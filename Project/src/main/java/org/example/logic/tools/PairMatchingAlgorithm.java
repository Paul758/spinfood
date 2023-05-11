package org.example.logic.tools;

import org.example.data.enums.FoodPreference;
import org.example.data.enums.KitchenType;
import org.example.data.structures.Solo;
import org.example.logic.graph.Edge;
import org.example.logic.graph.Graph;

import java.util.ArrayList;
import java.util.List;

public class PairMatchingAlgorithm {

    List<Solo> solos;

    float genderCost = 1f;
    float twoKitchenCost = 1f;

    float ageWeight;
    float genderWeight;
    float foodPreferenceWeight;

    boolean prioritiseHighCount;

    public PairMatchingAlgorithm(List<Solo> solos){
        this.solos = solos;
    }

    public PairMatchingAlgorithm(List<Solo> solos, float ageWeight, float genderWeight, float foodPreferenceWeight, boolean prioritiseHighCount) {
        this.solos = solos;
        this.ageWeight = ageWeight;
        this.genderWeight = genderWeight;
        this.foodPreferenceWeight = foodPreferenceWeight;
        this.prioritiseHighCount = prioritiseHighCount;
    }

    public List<PairMatched> match() {
        List<PairMatched> pairMatched = new ArrayList<>();
        Graph graph = new Graph();

        for (int i = 0; i < solos.size(); i++) {
            for (int j = i + 1; j < solos.size(); j++) {
                Solo soloA = solos.get(i);
                Solo soloB = solos.get(j);

                float weight = calcValue(soloA, soloB);

                if (weight != -1) {
                    graph.addEdge(soloA, soloB, weight);
                }
            }
        }

        for (int i = 0 ; i < solos.size() / 2; i++) {
            Solo soloA;
            if (prioritiseHighCount) {
                soloA = graph.getVertexWithLeastEdges();
            } else {
                soloA = graph.getVertexWithMostEdges();
            }

            if (soloA == null) {
                break;
            }

            Edge edge = graph.getEdgeWithLeastWeight(soloA);
            if (edge == null) {
                break;
            }
            Solo soloB = edge.solo;

            pairMatched.add(new PairMatched(soloA, soloB));

            graph.removeVertex(soloA);
            graph.removeVertex(soloB);
        }

        return pairMatched;
    }

    private float calcValue(Solo soloA, Solo soloB) {
        if (soloA.kitchen.kitchenType.equals(KitchenType.NO) && soloB.kitchen.kitchenType.equals(KitchenType.NO)) {
            return -1;
        }

        if (isFoodPreferenceIncompatible(soloA, soloB) || isFoodPreferenceIncompatible(soloB, soloA)) {
            return -1;
        }

        float value = 0;

        if (soloA.kitchen.kitchenType.equals(KitchenType.YES) && soloB.kitchen.kitchenType.equals(KitchenType.YES)) {
            value += twoKitchenCost;
        }

        if (soloA.person.sex().equals(soloB.person.sex())) {
            value += genderCost;
        }

        int ageRangeA = MatchingTools.getAgeRange(soloA.person.age());
        int ageRangeB = MatchingTools.getAgeRange(soloB.person.age());
        value += (((float) Math.abs(ageRangeA - ageRangeB)) / 8f) * ageWeight;


        return value;
    }

    private boolean isFoodPreferenceIncompatible(Solo s1, Solo s2) {
        boolean s1IsMeat = s1.foodPreference.equals(FoodPreference.MEAT);
        boolean s2IsVeggie = s2.foodPreference.equals(FoodPreference.VEGGIE);
        boolean s2IsVegan = s2.foodPreference.equals(FoodPreference.VEGAN);

        return s1IsMeat && (s2IsVeggie || s2IsVegan);
    }
}
