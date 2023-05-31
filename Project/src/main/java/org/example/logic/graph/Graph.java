package org.example.logic.graph;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A graph data structure that can be used to find the best matching pairs and groups.
 * Vertices are participants and edges are the weight between two participants.
 * To represent the graph in Java an adjacency list is used, which is stored in a HashMap.
 * We use a
 * @author David Riemer
 * @version 1.1
 * @param <T> made Generic to be used for both Pair and Group matching
 */
public class Graph <T> {
    /**
     * HashMap that contains all vertices and their edges in a List
     */
    HashMap<T, List<Edge<T>>> adjacencyList = new HashMap<>();


    /**
     * Method to add a vertex to the graph
     * First checks if the vertex is already in the graph
     * if not, it is added to the adjacencyList with an empty ArrayList as value
     * @author David Riemer
     * @param participant to be added to the graph
     */
    public void addVertex(T participant) {
        if (!adjacencyList.containsKey(participant)) {
            adjacencyList.put(participant, new ArrayList<>());
        }
    }

    /**
     * Method to add an edge between two participants and a given weight
     * First addVertex is called to make sure both participants are in the graph
     * Then two edges are created and linked to each other
     * @author David Riemer
     * @param participantA participant to be added to the graph
     * @param participantB participant to be added to the graph
     * @param weight weight of the edge between the two participants
     */
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

    /**
     * Method to remove a vertex from the graph
     * @author David Riemer
     * @param participant to be removed from the graph
     */
    public void removeVertex(T participant) {
        List<Edge<T>> edges = adjacencyList.get(participant);
        for (Edge<T> edge : edges) {
            Edge<T> linkedEdge = edge.linkedEdge;
            adjacencyList.get(edge.participant).remove(linkedEdge);
        }
        adjacencyList.remove(participant);
    }

    /**
     * Method to find the Vertex with the least edges in the graph
     * First a minimum count is set to the maximum integer value
     * Then the adjacencyList is iterated over and the count of edges for each vertex is calculated
     * If the count is smaller than the minimum count, the minimum count is set to the count
     * @author Paul Groß
     * @return the Eventparticipant with the least edges in the graph
     *
     */
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

    /**
     * Same as the method above, however a minimum number of edges can be specified
     * @author David Riemer
     * @param minimum the minimum number of edges the vertex should have
     * @return the Eventparticipant with the least edges in the graph
     * @see org.example.logic.matchingalgorithms.GraphGroupMatching
     */
    public T getVertexWithLeastEdges(int minimum) {
        int minCount = Integer.MAX_VALUE;
        T minParticipant = null;

        for (T participant : adjacencyList.keySet()) {
            int count = adjacencyList.get(participant).size();
            if (count < minCount && count >= minimum) {
                minCount = count;
                minParticipant = participant;
            }
        }
        return minParticipant;
    }


    /**
     * @author David Riemer
     * @param participant the participant to get the edge with the least weight from
     * @return the edge with the least weight from the given participant
     */
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
