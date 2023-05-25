package org.example.logic.graph;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Graph <T> {
    HashMap<T, List<Edge<T>>> adjacencyList = new HashMap<>();

    public void addVertex(T participant) {
        if (!adjacencyList.containsKey(participant)) {
            adjacencyList.put(participant, new ArrayList<>());
        }
    }

    public void addEdge(T participantA, T participantB, float weight) {
        addVertex(participantA);
        addVertex(participantB);

        Edge<T> edgeA = new Edge<>(participantB, weight);
        Edge<T> edgeB = new Edge<>(participantA, weight);

        edgeA.linkedEdge = edgeB;
        edgeB.linkedEdge = edgeA;

        adjacencyList.get(participantA).add(edgeA);
        adjacencyList.get(participantB).add(edgeB);
    }

    public void removeVertex(T participant) {
        List<Edge<T>> edges = adjacencyList.get(participant);
        for (Edge<T> edge : edges) {
            Edge<T> linkedEdge = edge.linkedEdge;
            adjacencyList.get(edge.participant).remove(linkedEdge);
        }
        adjacencyList.remove(participant);
    }

    public T getVertexWithLeastEdges() {
        int minCount = Integer.MAX_VALUE;
        T minParticipant = null;

        for (T participant : adjacencyList.keySet()) {
            int count = adjacencyList.get(participant).size();
            if (count < minCount) {
                minCount = count;
                minParticipant = participant;
            }
        }

        return minParticipant;
    }

    public T getVertexWithLeastEdges(int limit) {
        int minCount = Integer.MAX_VALUE;
        T minParticipant = null;

        for (T participant : adjacencyList.keySet()) {
            int count = adjacencyList.get(participant).size();
            if (count < minCount && count >= limit) {
                minCount = count;
                minParticipant = participant;
            }
        }

        return minParticipant;
    }



    public Edge<T> getEdgeWithLeastWeight(T participant) {
        List<Edge<T>> edges = adjacencyList.get(participant);
        Edge<T> minEdge = null;
        float minWeight = Float.MAX_VALUE;

        for (Edge<T> edge : edges) {
            if (edge.weight < minWeight) {
                minEdge = edge;
                minWeight = edge.weight;
            }
        }
        return minEdge;
    }
}
