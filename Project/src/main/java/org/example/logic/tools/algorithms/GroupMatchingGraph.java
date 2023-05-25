package org.example.logic.tools.algorithms;

import org.example.data.Coordinate;
import org.example.data.DataManagement;
import org.example.logic.graph.Graph;
import org.example.logic.structures.PairMatched;

import java.util.Collection;
import java.util.List;

public class GroupMatchingGraph {

    public Graph<PairMatched> createGraph(List<PairMatched> matchedPairs) {
        PairMatched furthestPair = GetFurthestPair(matchedPairs);
        double maxDistanceToPartyLocation = furthestPair.distanceToPartyLocation;

        for (int x = 0; x < matchedPairs.size(); x++) {

            for (int y = x + 1; y < matchedPairs.size(); y++) {

            }
        }

        throw new RuntimeException();
    }

    public double calcPathCosts(PairMatched pairA, PairMatched pairB, double maxDistanceToPartyLocation) {
        return Coordinate.getDistance(pairA.getKitchen().coordinate, pairB.getKitchen().coordinate) / (maxDistanceToPartyLocation * 2);

    }

    public static PairMatched GetFurthestPair(List<PairMatched> pairs) {
        pairs.sort((pairA, pairB) -> {
            return Double.compare(pairB.distanceToPartyLocation, pairA.distanceToPartyLocation);
        });
        return pairs.get(0);
    }



}
