package org.example.logic.tools.algorithms;

public class LimitedCentroid extends Centroid {
    int limit;

    public LimitedCentroid(double x, double y, int limit) {
        super(x, y);
        this.limit = limit;
    }

    @Override
    public void addPoint(Point point) {
        if (points.size() == limit) throw new IllegalStateException();
        points.add(point);
    }

    public boolean isFull() {
        return points.size() == limit;
    }
}
