package org.example.logic.tools.algorithms;

import org.example.data.Coordinate;
import org.example.data.DataManagement;
import org.example.logic.graph.Graph;
import org.example.logic.structures.PairMatched;

import java.util.Collection;
import java.util.List;

public class GroupMatchingGraph {

    public Graph<PairMatched> createGraph(List<PairMatched> matchedPairs){
        PairMatched furthestPair = GetFurthestPair(matchedPairs);
        double maxDistanceToPartyLocation = furthestPair.distanceToPartyLocation;

        throw new RuntimeException();
    }

    public double calcPathCosts(PairMatched pairA, PairMatched pairB) {
        return Coordinate.getDistance(pairA.getKitchen().coordinate, pairB.getKitchen().coordinate);
    }


    public static PairMatched GetFurthestPair(List<PairMatched> pairs){
        //System.out.println("matched pair list: " + pairs);
        pairs.sort((pairA, pairB) -> {
            return Double.compare(pairB.distanceToPartyLocation, pairA.distanceToPartyLocation);
        });

        //pairs.forEach(System.out::println);
        return pairs.get(0);
    }



}
