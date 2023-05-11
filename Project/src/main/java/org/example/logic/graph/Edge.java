package org.example.logic.graph;

import org.example.data.structures.Solo;

public class Edge {
    public Solo solo;
    public float weight;

    public Edge(Solo solo, float weight) {
        this.solo = solo;
        this.weight = weight;
    }
}
