package org.example.logic.tools.algorithms;

public class Vector2 {
    public double x;
    public double y;

    public static double getDistance(Vector2 v1, Vector2 v2) {
        return Math.sqrt(Math.pow(v2.x - v1.x, 2) + Math.pow(v2.y - v1.y, 2));
    }
}
