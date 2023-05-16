package org.example.logic.graph;

import org.example.data.structures.Solo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UndirectedGraph {
    HashMap<Solo, List<Edge>> adjacencyList = new HashMap<>();

    public void addVertex(Solo solo) {
        if (!adjacencyList.containsKey(solo)) {
            adjacencyList.put(solo, new ArrayList<>());
        }
    }

    public void addEdge(Solo soloA, Solo soloB, float weight) {
        addVertex(soloA);
        addVertex(soloB);

        Edge edgeA = new Edge(soloB, weight);
        Edge edgeB = new Edge(soloA, weight);

        edgeA.linkedEdge = edgeB;
        edgeB.linkedEdge = edgeA;

        adjacencyList.get(soloA).add(edgeA);
        adjacencyList.get(soloB).add(edgeB);
    }
    
    public void removeVertex(Solo solo) {
        List<Edge> edges = adjacencyList.get(solo);
        for (Edge edge : edges) {
            Edge linkedEdge = edge.linkedEdge;
            adjacencyList.get(edge.solo).remove(linkedEdge);
        }
        adjacencyList.remove(solo);
    }

    public Solo getVertexWithLeastEdges() {
        int minCount = Integer.MAX_VALUE;
        Solo minSolo = null;

        for (Solo solo : adjacencyList.keySet()) {
            int count = adjacencyList.get(solo).size();
            if (count < minCount) {
                minCount = count;
                minSolo = solo;
            }
        }
        
        return minSolo;
    }

    public Edge getEdgeWithLeastWeight(Solo solo) {
        List<Edge> edges = adjacencyList.get(solo);
        Edge minEdge = null;
        float minWeight = Float.MAX_VALUE;

        for (Edge edge : edges) {
            if (edge.weight < minWeight) {
                minEdge = edge;
                minWeight = edge.weight;
            }
        }

        return minEdge;
    }
}
