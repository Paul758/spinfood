package org.example.logic.tools.algorithms;

import java.util.*;

public class KMeansSameSize {
    public static List<List<Point>> getCluster(List<Point> points, int clusterSize) {
        if (points.size() % clusterSize != 0) throw new IllegalStateException("List size must be multiple of cluster size");

        List<List<Point>> resultCluster = new ArrayList<>();
        int countOfCluster = points.size() / clusterSize;
        System.out.println("---------------------------------");
        System.out.println("starts Kmeans");
        List<List<Point>> cluster = KMeans.getCluster(points, countOfCluster);
        System.out.println("finishes Kmeans");
        System.out.println("---------------------------------");
        System.out.println(cluster.size());

        List<LimitedCentroid> centroids = new ArrayList<>();
        for (List<Point> clusterPoints : cluster) {
            double xMean = clusterPoints.stream()
                    .mapToDouble(p -> p.x)
                    .average()
                    .orElseThrow();

            double yMean = clusterPoints.stream()
                    .mapToDouble(p -> p.y)
                    .average()
                    .orElseThrow();

            LimitedCentroid centroid = new LimitedCentroid(xMean, yMean, clusterSize);
            centroids.add(centroid);
        }

        while (centroids.size() != 0) {
            Point point = points.stream()
                    .sorted(Comparator.comparingDouble(p -> getWeightedDistance(p, centroids)))
                    .reduce((a,b) -> a)
                    .orElseThrow();

            LimitedCentroid preferredCentroid = centroids.stream()
                    .sorted(Comparator.comparingDouble(centroid -> Vector2.getDistance(centroid, point)))
                    .reduce((a,b) -> a)
                    .orElseThrow();

            preferredCentroid.addPoint(point);
            points.remove(point);

            if (preferredCentroid.isFull()) {
                resultCluster.add(preferredCentroid.getPoints());
                centroids.remove(preferredCentroid);
            }
        }

        return resultCluster;
    }

    private static double calcMeanDistance(Centroid centroid, List<Point> points) {
        return points.stream()
                .mapToDouble(p -> Vector2.getDistance(p, centroid))
                .average()
                .orElseThrow();
    }

    private static double calcMeantToAllCluster(Point point, List<Centroid> centroids) {
        return centroids.stream()
                .mapToDouble(c -> Vector2.getDistance(c, point))
                .average()
                .orElseThrow();
    }



    private static double getWeightedDistance(Point point, List<LimitedCentroid> centroids) {
        return calcDistanceToNearestCluster(point, centroids) - calcDistanceToFarthestCluster(point, centroids);
    }

    private static double calcDistanceToNearestCluster(Point point, List<LimitedCentroid> centroids) {
        return centroids.stream()
                .mapToDouble(c -> Vector2.getDistance(point, c))
                .sorted()
                .reduce((a,b) -> a)
                .orElseThrow();
    }

    private static double calcDistanceToFarthestCluster(Point point, List<LimitedCentroid> centroids) {
        return centroids.stream()
                .mapToDouble(c -> Vector2.getDistance(point, c))
                .sorted()
                .reduce((a,b) -> b)
                .orElseThrow();
    }

    private static List<List<Point>> calc(List<LimitedCentroid> centroids, List<Point> points, int clusterSize, List<List<Point>> resultCluster) {
        while (centroids.size() > 0) {
            Centroid centroidHighestDistance = centroids.stream()
                    .sorted(Comparator.comparingDouble(c -> calcMeanDistance(c, points)))
                    .reduce((a,b) -> b)
                    .orElseThrow();

            System.out.println("last centroid");
            System.out.println(calcMeanDistance(centroidHighestDistance, points));
            System.out.println(centroidHighestDistance);

            List<Point> nearestPoints =points.stream()
                    .sorted(Comparator.comparingDouble(p -> Vector2.getDistance(p, centroidHighestDistance) - calcDistanceToFarthestCluster(p, centroids)))
                    .limit(clusterSize)
                    .toList();

            System.out.println("nearest Points");
            nearestPoints.forEach(System.out::println);

            resultCluster.add(nearestPoints);
            nearestPoints.forEach(points::remove);
            centroids.remove(centroidHighestDistance);
        }

        return resultCluster;
    }
}
