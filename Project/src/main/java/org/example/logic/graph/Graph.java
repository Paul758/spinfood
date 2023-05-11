package org.example.logic.graph;

import org.example.data.structures.Solo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Graph {
    HashMap<Solo, List<Edge>> map = new HashMap<>();

    public void addVertex(Solo solo) {
        if (!map.containsKey(solo)) {
            map.put(solo, new ArrayList<>());
        }
    }

    public void addEdge(Solo soloA, Solo soloB, float weight) {
        addVertex(soloA);
        addVertex(soloB);

        Edge edgeA = new Edge(soloB, weight);
        Edge edgeB = new Edge(soloA, weight);

        map.get(soloA).add(edgeA);
        map.get(soloB).add(edgeB);
    }
    
    public void removeVertex(Solo solo) {
        map.remove(solo);

        for (Solo mapSolo : map.keySet()) {
            List<Edge> edges = new ArrayList<>();
            for (Edge edge : map.get(mapSolo)) {
                if (!edge.solo.equals(solo)) {
                    edges.add(edge);
                }
            }
            map.put(mapSolo, edges);
        }
    }

    public Solo getVertexWithLeastEdges() {
        int minCount = Integer.MAX_VALUE;
        Solo minSolo = null;

        for (Solo solo : map.keySet()) {
            int count = map.get(solo).size();
            if (count < minCount) {
                minCount = count;
                minSolo = solo;
            }
        }
        
        return minSolo;
    }

    public Solo getVertexWithMostEdges() {
        int maxValue = Integer.MIN_VALUE;
        Solo maxSolo = null;

        for (Solo solo : map.keySet()) {
            int count = map.get(solo).size();
            if (count > maxValue) {
                maxValue = count;
                maxSolo = solo;
            }
        }

        return maxSolo;
    }
    
    public Edge getEdgeWithLeastWeight(Solo solo) {
        float minWeight = Float.MAX_VALUE;
        Edge minEdge = null;

        for (Edge edge : map.get(solo)) {
            if (edge.weight < minWeight) {
                minWeight = edge.weight;
                minEdge = edge;
            }
        }
        return minEdge;
    }
}
