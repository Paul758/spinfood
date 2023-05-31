package org.example.logic.tools.algorithms;

import org.example.data.Coordinate;
import org.example.logic.structures.PairMatched;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;


public class KMeans {

    public static List<List<Point>> getCluster(List<Point> points, int numberOfCluster) {
        List<Centroid> randomCentroids = getRandomCentroids(numberOfCluster, points);
        int changes = addPointsToCentroid(points, randomCentroids);

        while (changes != 0) {
            randomCentroids.forEach(Centroid::changePositionToCenter);
            changes = addPointsToCentroid(points, randomCentroids);
            System.out.println("changes: " + changes);
        }

        return randomCentroids.stream().map(Centroid::getPoints).toList();
    }

    private static List<Centroid> getDatasetCentroids(int numberOfCluster, List<Point> pointList) {
        List<Point> points = new ArrayList<>(pointList);
        List<Centroid> centroids = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numberOfCluster; i++) {
            Point point = points.get(random.nextInt(0, points.size()));
            points.remove(point);
            centroids.add(new Centroid(point.x, point.y));
        }

        return centroids;
    }

    private static List<Centroid> getRandomCentroids(int numberOfCluster, List<Point> points) {
        List<Centroid> centroids = new ArrayList<>();
        Random random = new Random();

        List<Double> xValues = points.stream()
                .map(p -> p.x)
                .sorted()
                .toList();

        List<Double> yValues = points.stream()
                .map(p -> p.y)
                .sorted()
                .toList();

        double minX = xValues.get(0);
        double maxX = xValues.get(xValues.size() - 1);
        double minY = yValues.get(0);
        double maxY = yValues.get(yValues.size() - 1);

        System.out.println(minX + " " + maxX + " " + minY + " " + maxY);

        for (int i = 0; i < numberOfCluster; i++) {
            double x = random.nextDouble(minX,maxX);
            double y = random.nextDouble(minY,maxY);
            centroids.add(new Centroid(x, y));
        }

        return centroids;
    }

    private static int addPointsToCentroid(List<Point> points, List<Centroid> centroids) {
        int changes = 0;
        for (Point point : points) {

            List<Centroid> centroidsByDistance = centroids.stream()
                    .sorted(Comparator.comparingDouble(c -> Vector2.getDistance(c, point)))
                    .toList();

            Centroid minCentroid = centroidsByDistance.get(0);

            if (!minCentroid.contains(point)) {
                centroids.forEach(c -> c.removePoint(point));
                minCentroid.addPoint(point);
                changes++;
            }
        }

        return changes;
    }
}
