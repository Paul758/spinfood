package org.example.logic.graph;

import org.example.data.structures.Solo;

import java.util.Comparator;

public class Edge <T>{
    public T participant;
    public float weight;
    Edge<T> linkedEdge;

    public Edge(T participant, float weight) {
        this.participant = participant;
        this.weight = weight;
    }
}
