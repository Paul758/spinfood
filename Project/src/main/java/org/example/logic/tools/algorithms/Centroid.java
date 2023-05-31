package org.example.logic.tools.algorithms;

import java.util.ArrayList;
import java.util.List;

public class Centroid extends Vector2 {

    List<Point> points;

    public Centroid(double x, double y) {
        this.x = x;
        this.y = y;
        this.points = new ArrayList<>();
        System.out.println(this);
    }

    public void addPoint(Point point) {
        System.out.println("add point " + point + " to cluster " + this);
        points.add(point);
    }

    public void removePoint(Point point) {
        points.remove(point);
    }

    public boolean contains(Point point) {
        return points.contains(point);
    }

    public List<Point> getPoints() {
        return points;
    }

    public void changePositionToCenter() {
        System.out.println(points.size());
        points.forEach(System.out::println);

        double meanX = points.stream()
                .mapToDouble(p -> x)
                .average()
                .orElseThrow();

        double meanY = points.stream()
                .mapToDouble(p -> y)
                .average()
                .orElseThrow();

        this.x = meanX;
        this.y = meanY;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
