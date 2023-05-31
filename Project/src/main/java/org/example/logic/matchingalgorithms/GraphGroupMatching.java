package org.example.logic.matchingalgorithms;

import org.example.data.Coordinate;
import org.example.data.enums.FoodPreference;
import org.example.data.enums.Sex;
import org.example.logic.enums.MealType;
import org.example.logic.graph.Graph;
import org.example.logic.structures.GroupMatched;
import org.example.logic.structures.PairMatched;

import java.util.ArrayList;
import java.util.List;

public class GraphGroupMatching {


    public static List<GroupMatched> match(List<PairMatched> matchedPairs, MatchCosts matchCosts) {

        //Split pairs based on foodPreference
        List<PairMatched> meatNonePairs = getMeatNonePairs(matchedPairs);
        List<PairMatched> veggieVeganPairs = getVeggieVeganPairs(matchedPairs);

        //Create graphs
        Graph<PairMatched> meatGraph = createGraph(meatNonePairs, matchCosts);
        Graph<PairMatched> veganGraph = createGraph(veggieVeganPairs, matchCosts);

        //find superGroups (9 pairs)
        List<List<PairMatched>> superGroups = new ArrayList<>();
        superGroups.addAll(findSuperGroups(meatNonePairs, meatGraph));
        superGroups.addAll(findSuperGroups(veggieVeganPairs, veganGraph));

        //Split superGroups in corresponding dinner groups and assign cook (starters, mainCourse, dessert)
        List<GroupMatched> groupMatchedList;
        groupMatchedList = createDinnerGroupsFromSuperGroups(superGroups);

        return groupMatchedList;
    }

    private static List<List<PairMatched>> findSuperGroups(List<PairMatched> matchedPairs, Graph<PairMatched> graph) {
        int superGroupSize = 9;
        List<List<PairMatched>> superGroups = new ArrayList<>();

        for (int i = 0; i < matchedPairs.size(); i++) {
            try {
                List<PairMatched> superGroup = new ArrayList<>();
                PairMatched currentPair = graph.getVertexWithLeastEdges(8);

                for(int j = 0; j < superGroupSize; j++) {
                    PairMatched possibleMatch = graph.getEdgeWithLeastWeight(currentPair).participant;
                    superGroup.add(possibleMatch);
                    graph.removeVertex(possibleMatch);
                }
                superGroups.add(superGroup);
                graph.removeVertex(currentPair);

            } catch (Exception e) {
                break;
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
            GroupMatched starterGroupA = new GroupMatched(pairA, pairD, pairG, MealType.STARTER);
            GroupMatched starterGroupB = new GroupMatched(pairB, pairE, pairH, MealType.STARTER);
            GroupMatched starterGroupC = new GroupMatched(pairC, pairF, pairI, MealType.STARTER);

            //main course groups
            GroupMatched mainCourseGroupA = new GroupMatched(pairF, pairA, pairH, MealType.MAIN);
            GroupMatched mainCourseGroupB = new GroupMatched(pairD, pairB, pairI, MealType.MAIN);
            GroupMatched mainCourseGroupC = new GroupMatched(pairE, pairC, pairG, MealType.MAIN);

            //dessert groups
            GroupMatched dessertGroupA = new GroupMatched(pairI, pairE, pairA, MealType.DESSERT);
            GroupMatched dessertGroupB = new GroupMatched(pairG, pairF, pairB, MealType.DESSERT);
            GroupMatched dessertGroupC = new GroupMatched(pairH, pairD, pairC, MealType.DESSERT);

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
            if (pair.getFoodPreference().equals(FoodPreference.VEGGIE) || pair.getFoodPreference().equals(FoodPreference.VEGAN)) {
                veggieVeganPairs.add(pair);
            }
        }
        return veggieVeganPairs;

    }

    private static List<PairMatched> getMeatNonePairs(List<PairMatched> matchedPairs) {
        List<PairMatched> meatNonePairs = new ArrayList<>();
        for (PairMatched pair : matchedPairs) {
            if (pair.getFoodPreference().equals(FoodPreference.MEAT) || pair.getFoodPreference().equals(FoodPreference.NONE)) {
                meatNonePairs.add(pair);
            }
        }
        return meatNonePairs;
    }


    public static Graph<PairMatched> createGraph(List<PairMatched> matchedPairs, MatchCosts matchCosts) {
        Graph<PairMatched> graph = new Graph<>();

        PairMatched furthestPair = GetFurthestPair(matchedPairs);
        double maxDistanceToPartyLocation = furthestPair.getDistanceToPartyLocation();

        for (int i = 0; i < matchedPairs.size(); i++) {

            for (int j = i + 1; j < matchedPairs.size(); j++) {
                PairMatched pairA = matchedPairs.get(i);
                PairMatched pairB = matchedPairs.get(j);

                if(fullfillsHardCriteria(pairA,pairB)){
                   double costs = calcMatchingCosts(pairA, pairB, maxDistanceToPartyLocation, matchCosts);
                   graph.addEdge(pairA, pairB, (float) costs);
                }
            }
        }
        return graph;
    }

    private static double calcMatchingCosts(PairMatched pairA, PairMatched pairB, double maxDistanceToPartyLocation, MatchCosts matchCosts) {
        double costs = 0;
        costs += calcPathCosts(pairA, pairB, maxDistanceToPartyLocation, matchCosts);
        costs += calcAgeCosts(pairA, pairB, matchCosts);
        costs += calcSexCosts(pairA, pairB, matchCosts);
        costs += calcFoodPreferenceCosts(pairA, pairB, matchCosts);
        return costs;
    }

    private static double calcFoodPreferenceCosts(PairMatched pairA, PairMatched pairB, MatchCosts matchCosts) {
        double costs = 0;
        if(pairA.getFoodPreference() != pairB.getFoodPreference()){
            costs += matchCosts.getFoodPreferenceCosts();
        }
        return costs;
    }

    private static boolean fullfillsHardCriteria(PairMatched pairA, PairMatched pairB) {
       // System.out.println("Pair A foodPreference is " + pairA.getFoodPreference());
       // System.out.println("Pair B foodPreference is " + pairB.getFoodPreference());

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

    public static double calcPathCosts(PairMatched pairA, PairMatched pairB, double maxDistanceToPartyLocation, MatchCosts matchCosts) {
        double pathCosts =  Coordinate.getDistance(pairA.getKitchen().coordinate, pairB.getKitchen().coordinate) / (maxDistanceToPartyLocation * 2);
        return pathCosts * matchCosts.getPathLengthCosts();
    }

    public static double calcAgeCosts(PairMatched pairA, PairMatched pairB, MatchCosts matchCosts){
        int ageRangeA = pairA.getPersonA().age();
        int ageRangeB = pairA.getPersonB().age();
        int meanAgePairA = (ageRangeA + ageRangeB) / 2;

        int ageRangeC = pairB.getPersonA().age();
        int ageRangeD = pairB.getPersonB().age();
        int meanAgePairB = (ageRangeC + ageRangeD) / 2;

        int ageRangeDeviation = Math.abs(meanAgePairA - meanAgePairB);

        return ageRangeDeviation * matchCosts.getAgeCosts();
    }

    public static double calcSexCosts(PairMatched pairA, PairMatched pairB, MatchCosts matchCosts) {

        double costs = 0;

        Sex sexA = pairA.getPersonA().sex();
        Sex sexB = pairA.getPersonB().sex();

        Sex sexC = pairB.getPersonA().sex();
        Sex sexD = pairB.getPersonB().sex();

        if(sexA.equals(sexC)){
            costs++;
        }
        if(sexA.equals(sexD)){
            costs++;
        }

        if(sexB.equals(sexC)){
            costs++;
        }
        if(sexB.equals(sexD)){
            costs++;
        }

        return costs * matchCosts.getGenderCosts();
    }

    public static PairMatched GetFurthestPair(List<PairMatched> pairs) {
        pairs.sort((pairA, pairB) -> {
            return Double.compare(pairB.getDistanceToPartyLocation(), pairA.getDistanceToPartyLocation());
        });
        return pairs.get(0);
    }
}
