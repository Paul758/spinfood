package org.example.logic.matchingalgorithms;

import org.example.data.enums.FoodPreference;
import org.example.data.enums.KitchenType;
import org.example.data.enums.Sex;
import org.example.data.structures.Solo;
import org.example.logic.graph.Graph;
import org.example.logic.structures.PairMatched;
import org.example.logic.tools.MatchingTools;



import java.util.ArrayList;
import java.util.List;

/**
 * Matches solos to pairs using an undirected weighted graph
 * 1. Creates a graph where solos gets represented by vertices and the quality of a match by the weight of the edges
 * 2. Get the solo with the least edges
 * 3. Get the edge with the smallest weight from this solo, which represents his optimal match
 * 4. Create a pair from these solos and remove them from the graph
 * 5. Repeat step 2 to 4 until no more solos or edges exist
 * 6. The solos got matched to pairs
 */
public class GraphPairMatching {

    private static final float genderCost = 1f;
    private static final float kitchenCost = 1f;
    private static final float foodPreferenceCost = 1f;
    private static final float ageCost = 1f;
    private static final float defaultLimitMultiplier = 1f;

    public static List<PairMatched> match(List<Solo> solos) {
        return match(solos, MatchCosts.getDefault(), defaultLimitMultiplier);
    }

    public static List<PairMatched> match(List<Solo> solos, MatchCosts matchingCosts) {
        return match(solos, matchingCosts, defaultLimitMultiplier);
    }

    public static List<PairMatched> match(List<Solo> solos, float limitMultiplier) {
        return match(solos, MatchCosts.getDefault(), limitMultiplier);
    }

    /**
    * Matches solos based on their compatibility and returns a list of matched pairs.
    * @param solos the list of solo participants to be matched
    * @param matchCosts used to scale the costs for the criteria
    * @param limitMultiplier value between 0 and 1, is used to control the quality of the matches
    * @return a list of PairMatched objects representing the matched pairs
    */
    public static List<PairMatched> match(List<Solo> solos, MatchCosts matchCosts, float limitMultiplier) {
        List<PairMatched> pairMatched = new ArrayList<>();

        Graph<Solo> graph = createGraph(solos, matchCosts, limitMultiplier);

        for (int i = 0 ; i < solos.size() / 2; i++) {
            try {
                Solo participantA = graph.getVertexWithLeastEdges();
                Solo participantB = graph.getEdgeWithLeastWeight(participantA).participant;

                pairMatched.add(new PairMatched( participantA, participantB));

                graph.removeVertex(participantA);
                graph.removeVertex(participantB);

            } catch (NullPointerException e) {
                break;
            }
        }

        return pairMatched;
    }

    /**
     * Creates a graph from a list of solos where each solo is a vertex in the graph.
     * If two solos can be a valid pair then an edge between them will be created.
     * The costs of the two solos gets represented by the weight of the edge between them.
     * High costs indicate an unfitting match, low costs indicate a fitting match.
     * @param solos a list of Solo objects
     * @param matchCosts used to scale the costs for the criteria
     * @param limitMultiplier value between 0 and 1, is used to control the quality of the matches
     * @return a graph of Solo objects
     */
    private static Graph<Solo> createGraph(List<Solo> solos, MatchCosts matchCosts, float limitMultiplier) {
        Graph<Solo> graph = new Graph<>();

        float maxCosts = calcMaxCost(matchCosts);

        for (int i = 0; i < solos.size(); i++) {
            for (int j = i + 1; j < solos.size(); j++) {
                Solo soloA = solos.get(i);
                Solo soloB = solos.get(j);

                if (fulfillsHardCriteria(soloA, soloB)) {
                    float weight = calcEdgeWeight(soloA, soloB, matchCosts);
                    if (weight <= maxCosts * limitMultiplier) {
                        graph.addEdge(soloA, soloB, weight);
                    }
                }
            }
        }
        return graph;
    }

    /**
     * Checks if two Solo objects fulfill the hard criteria for the matching process.
     * The criteria are fulfilled if one of the solos has an available kitchen and a compatible food preference.
     * @param solo1 the first Solo object
     * @param solo2 the second Solo object
     * @return true if the Solo objects fulfill the hard criteria, false otherwise
     */
    private static boolean fulfillsHardCriteria(Solo solo1, Solo solo2) {
        boolean s1HasNoKitchen = solo1.kitchen.kitchenType.equals(KitchenType.NO);
        boolean s2HasNoKitchen = solo2.kitchen.kitchenType.equals(KitchenType.NO);
        boolean noKitchenAvailable = s1HasNoKitchen && s2HasNoKitchen;

        boolean s1Tos2 = isFoodPreferenceIncompatible(solo1, solo2);
        boolean s2Tos1 = isFoodPreferenceIncompatible(solo2, solo1);
        boolean unFittingFoodPreference = s1Tos2 || s2Tos1;

        return !(noKitchenAvailable || unFittingFoodPreference);
    }

    /**
     * Calculates the highest possible cost the solos can have for a given MatchCosts object
     * @param matchCosts any MatchCost object
     * @return the highest possible cost
     */
    private static float calcMaxCost(MatchCosts matchCosts) {
        return (float) (kitchenCost * matchCosts.getKitchenCosts()
                + genderCost * matchCosts.getGenderCosts()
                + foodPreferenceCost * matchCosts.getFoodPreferenceCosts()
                + ageCost * matchCosts.getAgeCosts());
    }

    /**
     * Calculates the total cost of two Solo objects
     * @param soloA the first Solo object
     * @param soloB the second Solo object
     * @param matchCosts a MatchCost object
     * @return the total cost of soloA and soloB
     */
    private static float calcEdgeWeight(Solo soloA, Solo soloB, MatchCosts matchCosts) {
        return calcKitchenCost(soloA, soloB, matchCosts)
                + calcGenderCost(soloA, soloB, matchCosts)
                + calcAgeCost(soloA, soloB, matchCosts)
                + calcFoodPreferenceCost(soloA, soloB, matchCosts);
    }

    /**
     * Calculates the kitchen cost of two Solo objects.
     * If both solos have a kitchen, the cost gets calculated. Otherwise, the cost is 0.
     * @param soloA the first Solo object
     * @param soloB the second Solo object
     * @param matchCost a MatchCost object
     * @return the kitchen cost
     */
    private static float calcKitchenCost(Solo soloA, Solo soloB, MatchCosts matchCost) {
        boolean soloAHasKitchen = soloA.kitchen.kitchenType.equals(KitchenType.YES);
        boolean soloBHasKitchen = soloB.kitchen.kitchenType.equals(KitchenType.YES);

        if (soloAHasKitchen && soloBHasKitchen) {
            return 1 * kitchenCost * (float) matchCost.getKitchenCosts();
        } else {
            return 0;
        }
    }

    /**
     * Calculates the gender cost of two Solo objects.
     * If both solos have the same gender, the cost is calculated. Otherwise, the cost is 0.
     * @param soloA the first Solo object
     * @param soloB the second Solo object
     * @param matchCosts a MatchCost object
     * @return the kitchen cost
     */
    private static float calcGenderCost(Solo soloA, Solo soloB, MatchCosts matchCosts) {
        Sex soloASex = soloA.person.sex();
        Sex soloBSex = soloB.person.sex();

        if (!soloASex.equals(Sex.OTHER) && soloASex.equals(soloBSex)) {
            return 1 * genderCost * (float) matchCosts.getGenderCosts();
        } else {
            return 0;
        }
    }

    /**
     * Calculates the age cost of two Solo objects.
     * Divides the difference of the age range value by 8 to get a value between 0 and 1.
     * This value gets multiplied by the fixed age cost to get the total age cost
     * @param soloA the first Solo object
     * @param soloB the second Solo object
     * @param matchCosts a MatchCost object
     * @return the age cost
     */
    private static float calcAgeCost(Solo soloA, Solo soloB, MatchCosts matchCosts) {
        float ageRangeA = MatchingTools.getAgeRange(soloA.person.age());
        float ageRangeB = MatchingTools.getAgeRange(soloB.person.age());
        float difference = Math.abs(ageRangeA - ageRangeB);
        return (difference / 8) * ageCost * (float) matchCosts.getAgeCosts();
    }

    /**
     * Calculates the food preference cost of two Solo objects.
     * Divides the difference of the food preference value by 2 to get a value between 0 and 1.
     * This value gets multiplied by the fixed food preference cost to get the total food preference cost
     * @param soloA the first Solo object
     * @param soloB the second Solo object
     * @param matchCosts a MatchCost object
     * @return the food preference cost
     */
    private static float calcFoodPreferenceCost(Solo soloA, Solo soloB, MatchCosts matchCosts) {
        int soloAValue = MatchingTools.getIntValueFoodPreference(soloA.foodPreference);
        int soloBValue = MatchingTools.getIntValueFoodPreference(soloB.foodPreference);
        float difference = Math.abs(soloAValue - soloBValue);
        return (difference / 2) * foodPreferenceCost * (float) matchCosts.getFoodPreferenceCosts();
    }


    /**
     * Checks if the food preference of the Solos is incompatible
     * @param soloA the first Solo object
     * @param soloB the second Solo object
     * @return true if soloA has meat and soloB veggie or vegan as food preference, false otherwise
     */
    private static boolean isFoodPreferenceIncompatible(Solo soloA, Solo soloB) {
        boolean soloAIsMeat = soloA.foodPreference.equals(FoodPreference.MEAT);
        boolean soloBIsVeggie = soloB.foodPreference.equals(FoodPreference.VEGGIE);
        boolean soloBIsVegan = soloB.foodPreference.equals(FoodPreference.VEGAN);

        return soloAIsMeat && (soloBIsVeggie || soloBIsVegan);
    }
}