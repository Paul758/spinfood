package org.example.logic.tools.algorithms;

import org.example.data.Coordinate;
import org.example.data.enums.FoodPreference;
import org.example.logic.enums.MealType;
import org.example.logic.graph.Graph;
import org.example.logic.structures.GroupMatched;
import org.example.logic.structures.PairMatched;

import java.util.ArrayList;
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

        List<GroupMatched> groupMatchedList = createDinnerGroupsFromSuperGroups(superGroups);

        System.out.println("The groupMatchedList size is: " + groupMatchedList.size());

        System.out.println("Printing groupMatchedList: ");
        groupMatchedList.forEach(System.out::println);

        return groupMatchedList;
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
            if(isSuperGroupFeasible(pairMatchedList)){
                System.out.println("is feasible group");
            } else {
                System.out.println("is not a feasible group");
            }
        }




        return superGroups;
    }

    private static List<GroupMatched> createDinnerGroupsFromSuperGroups(List<List<PairMatched>> superGroups) {
        List<GroupMatched> dinnerGroups = new ArrayList<>();
        for (List<PairMatched> superGroup : superGroups){
            PairMatched pairA = superGroup.get(0);
            PairMatched pairB = superGroup.get(1);
            PairMatched pairC = superGroup.get(2);
            PairMatched pairD = superGroup.get(3);
            PairMatched pairE = superGroup.get(4);
            PairMatched pairF = superGroup.get(5);
            PairMatched pairG = superGroup.get(6);
            PairMatched pairH = superGroup.get(7);
            PairMatched pairI = superGroup.get(8);

            //starters groups
            GroupMatched starterGroupA = new GroupMatched(pairA, pairD, pairG, pairA, MealType.STARTER);
            GroupMatched starterGroupB = new GroupMatched(pairB, pairE, pairH, pairB, MealType.STARTER);
            GroupMatched starterGroupC = new GroupMatched(pairC, pairF, pairI, pairC, MealType.STARTER);

            //main course groups
            GroupMatched mainCourseGroupA = new GroupMatched(pairA, pairF, pairH, pairF, MealType.MAIN);
            GroupMatched mainCourseGroupB = new GroupMatched(pairB, pairD, pairI, pairD, MealType.MAIN);
            GroupMatched mainCourseGroupC = new GroupMatched(pairC, pairE, pairG, pairE, MealType.MAIN);

            //dessert groups
            GroupMatched dessertGroupA = new GroupMatched(pairA, pairE, pairI, pairI, MealType.DESSERT);
            GroupMatched dessertGroupB = new GroupMatched(pairB, pairF, pairG, pairG, MealType.DESSERT);
            GroupMatched dessertGroupC = new GroupMatched(pairC, pairD, pairH, pairH, MealType.DESSERT);

            dinnerGroups.addAll(List.of(
                    starterGroupA, starterGroupB, starterGroupC,
                    mainCourseGroupA, mainCourseGroupB, mainCourseGroupC,
                    dessertGroupA, dessertGroupB, dessertGroupC));
        }
        return dinnerGroups;
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

                if(fullfillsHardCriteria(pairA,pairB)){
                   double costs = calcMatchingCosts(pairA, pairB, maxDistanceToPartyLocation);
                  // System.out.println("cost: " + costs);
                   graph.addEdge(pairA, pairB, (float) costs);
                }
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
        System.out.println("Pair A foodPreference is " + pairA.foodPreference);
        System.out.println("Pair B foodPreference is " + pairB.foodPreference);

        //foodPreference TODO review this code
        /*if (pairA.foodPreference.equals(FoodPreference.MEAT) || pairA.foodPreference.equals(FoodPreference.NONE)
                && (pairB.foodPreference.equals(FoodPreference.VEGAN) || pairB.foodPreference.equals(FoodPreference.VEGGIE))) {
            System.out.println("cant match");
            return false;
        } else if (pairB.foodPreference.equals(FoodPreference.MEAT) || pairB.foodPreference.equals(FoodPreference.NONE)
                && (pairA.foodPreference.equals(FoodPreference.VEGAN) || pairA.foodPreference.equals(FoodPreference.VEGGIE))) {
            System.out.println("cant match");
            return false;
        } else {
            System.out.println("is matchable");
            return true;
        }*/

        //don't match pairs in the same WG
        return !pairA.getKitchen().coordinate.equals(pairB.getKitchen().coordinate);


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
