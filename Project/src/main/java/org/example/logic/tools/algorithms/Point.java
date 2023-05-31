package org.example.logic.tools.algorithms;

import org.example.logic.structures.PairMatched;

public class Point extends Vector2{
    public PairMatched pair;

    public Point(PairMatched pair, double x, double y) {
        this.pair = pair;
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }


}
