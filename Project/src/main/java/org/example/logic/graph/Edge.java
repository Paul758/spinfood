package org.example.logic.graph;

import org.example.data.structures.Solo;

import java.util.Comparator;

public class Edge {
    public Solo solo;
    public float weight;
    Edge linkedEdge;

    public Edge(Solo solo, float weight) {
        this.solo = solo;
        this.weight = weight;
    }
}
