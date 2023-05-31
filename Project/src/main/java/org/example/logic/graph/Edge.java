package org.example.logic.graph;

/**
 * Edge class to be used for solving the matching problem for pairs and groups
 * Implemented generic to be used for both Pair and Group matching
 * Contains a participant and a weight
 * The weight is later used to determine the best match between two participants
 * @author David Riemer
 * @version 1.1
 * @see org.example.logic.matchingalgorithms.GraphGroupMatching
 * @see org.example.logic.matchingalgorithms.GraphPairMatching
 */
public class Edge <T>{
    public T participant;
    public float weight;
    Edge<T> linkedEdge;

    public Edge(T participant, float weight) {
        this.participant = participant;
        this.weight = weight;
    }
}
