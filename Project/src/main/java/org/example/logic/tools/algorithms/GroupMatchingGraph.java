package org.example.logic.tools.algorithms;

import org.example.data.Coordinate;
import org.example.data.DataManagement;
import org.example.data.enums.FoodPreference;
import org.example.logic.graph.Edge;
import org.example.logic.graph.Graph;
import org.example.logic.structures.GroupMatched;
import org.example.logic.structures.PairMatched;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GroupMatchingGraph {


    public static List<GroupMatched> match(List<PairMatched> matchedPairs) {
        List<PairMatched> meatNonePairs = getMeatNonePairs(matchedPairs);
        List<PairMatched> veggieVeganPairs = getVeggieVeganPairs(matchedPairs);


        Graph<PairMatched> graph = createGraph(matchedPairs);

        Graph<PairMatched> meatGraph = createGraph(meatNonePairs);
        Graph<PairMatched> veganGraph = createGraph(veggieVeganPairs);

        List<List<PairMatched>> superGroups = new ArrayList<>();

        superGroups.addAll(findSuperGroups(meatNonePairs, meatGraph));
        superGroups.addAll(findSuperGroups(veggieVeganPairs, veganGraph));


        System.out.println("The superGroups are");
        System.out.println(superGroups.toString());
        System.out.println("the superGroups size is: " + superGroups.size());

        return new ArrayList<>();
    }

    private static List<List<PairMatched>> findSuperGroups(List<PairMatched> matchedPairs, Graph<PairMatched> graph) {
        int superGroupSize = 9;
        List<List<PairMatched>> superGroups = new ArrayList<>();

        for (int i = 0; i < matchedPairs.size(); i++) {
            try {

                List<PairMatched> superGroup = new ArrayList<>();

                PairMatched pairA = graph.getVertexWithLeastEdges(8);

                //System.out.println("pair a is " + pairA);

                for(int j = 0; j < superGroupSize; j++) {
                    PairMatched possibleMatch = graph.getEdgeWithLeastWeight(pairA).participant;
                    superGroup.add(possibleMatch);
                    graph.removeVertex(possibleMatch);
                }
                superGroups.add(superGroup);
                graph.removeVertex(pairA);

            } catch (Exception e) {
                //System.out.println(e.toString());
                break;
            }
        }

        for (List<PairMatched> pairMatchedList: superGroups) {
            isSuperGroupFeasible(pairMatchedList);
        }


        return superGroups;
    }

    private static List<PairMatched> getVeggieVeganPairs(List<PairMatched> matchedPairs) {
        List<PairMatched> veggieVeganPairs = new ArrayList<>();
        for (PairMatched pair : matchedPairs) {
            if (pair.foodPreference.equals(FoodPreference.VEGGIE) || pair.foodPreference.equals(FoodPreference.VEGAN)) {
                veggieVeganPairs.add(pair);
            }
        }
        return veggieVeganPairs;

    }

    private static List<PairMatched> getMeatNonePairs(List<PairMatched> matchedPairs) {
        List<PairMatched> meatNonePairs = new ArrayList<>();
        for (PairMatched pair : matchedPairs) {
            if (pair.foodPreference.equals(FoodPreference.MEAT) || pair.foodPreference.equals(FoodPreference.NONE)) {
                meatNonePairs.add(pair);
            }
        }
        return meatNonePairs;
    }


    public static Graph<PairMatched> createGraph(List<PairMatched> matchedPairs) {
        Graph<PairMatched> graph = new Graph<>();

        PairMatched furthestPair = GetFurthestPair(matchedPairs);
        //System.out.println("furthest Distance" + furthestPair.distanceToPartyLocation);
        double maxDistanceToPartyLocation = furthestPair.distanceToPartyLocation;

        for (int i = 0; i < matchedPairs.size(); i++) {

            for (int j = i + 1; j < matchedPairs.size(); j++) {
                PairMatched pairA = matchedPairs.get(i);
                PairMatched pairB = matchedPairs.get(j);

                //if(fullfillsHardCriteria(pairA,pairB)){
                   double costs = calcMatchingCosts(pairA, pairB, maxDistanceToPartyLocation);
                  // System.out.println("cost: " + costs);
                   graph.addEdge(pairA, pairB, (float) costs);
                //}
            }
        }

        return graph;
    }

    private static double calcMatchingCosts(PairMatched pairA, PairMatched pairB, double maxDistanceToPartyLocation) {
        double costs = 0;
        costs += calcPathCosts(pairA, pairB, maxDistanceToPartyLocation);

        return costs;
    }

    private static boolean fullfillsHardCriteria(PairMatched pairA, PairMatched pairB) {
        if (pairA.foodPreference.equals(FoodPreference.MEAT) || pairA.foodPreference.equals(FoodPreference.NONE)
                && pairB.foodPreference.equals(FoodPreference.VEGAN) || pairB.foodPreference.equals(FoodPreference.VEGGIE)) {
            return false;
        } else if (pairB.foodPreference.equals(FoodPreference.MEAT) || pairB.foodPreference.equals(FoodPreference.NONE)
                && pairA.foodPreference.equals(FoodPreference.VEGAN) || pairA.foodPreference.equals(FoodPreference.VEGGIE)) {
            return false;
        } else {
            return true;
        }

    }

    public static double calcPathCosts(PairMatched pairA, PairMatched pairB, double maxDistanceToPartyLocation) {
        double pathCosts =  Coordinate.getDistance(pairA.getKitchen().coordinate, pairB.getKitchen().coordinate) / (maxDistanceToPartyLocation * 2);
       // System.out.println("maxDistance " + maxDistanceToPartyLocation);
       // System.out.println("pathCosts " + pathCosts);
        return pathCosts;
    }

    public static PairMatched GetFurthestPair(List<PairMatched> pairs) {
        pairs.sort((pairA, pairB) -> {
            return Double.compare(pairB.distanceToPartyLocation, pairA.distanceToPartyLocation);
        });
        return pairs.get(0);
    }


    public static boolean isSuperGroupFeasible(List<PairMatched> matchedList){
        System.out.println("now printing foodPreferences of superGroup");
        for (PairMatched pair :
                matchedList) {
            System.out.println(pair.foodPreference);
        }
        return true;
    }

}
