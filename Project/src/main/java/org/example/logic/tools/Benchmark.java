package org.example.logic.tools;

import org.example.data.enums.FoodPreference;
import org.example.data.enums.Sex;
import org.example.data.structures.Solo;

import java.util.List;
import java.util.Map;

public class Benchmark {

    public static void matchedPairsBenchmark(List<PairMatched> pairMatchedList, List<Solo> solos) {
        int pairSize = pairMatchedList.size() * 2;
        int soloSize = solos.size();

        float countValue = calcCount(pairMatchedList.size(), solos.size());
        float ageDifference = calcAgeDifference(pairMatchedList);
        float genderDiversity = calcGenderDiversity(pairMatchedList);
        float foodPreference = calcFoodPreference(pairMatchedList);

        System.out.println("Pairs: " + pairSize + ", Remaining: " + (soloSize - pairSize));
        System.out.println("Count:              " + percentAndRound(countValue) + "%");
        System.out.println("Age:                " + percentAndRound(ageDifference) + "%");
        System.out.println("Gender Diversity:   " + percentAndRound(genderDiversity) + "%");
        System.out.println("FoodPreference:     " + percentAndRound(foodPreference) + "%");
    }

    private static float calcCount(int pairCount, int soloCount) {
        pairCount = 2 * pairCount;
        return (float) pairCount / (float) soloCount;
    }

    private static float calcAgeDifference(List<PairMatched> pairMatchedList) {
        float maxValue = pairMatchedList.size() * 2 * 8;
        float sumAgeDifference = 0;
        for (PairMatched pairMatched : pairMatchedList) {
            sumAgeDifference += pairMatched.calculateAgeRangeDeviation();
        }

        return 1f - sumAgeDifference / maxValue;
    }

    private static float calcGenderDiversity(List<PairMatched> pairMatchedList) {
        float maxValue = pairMatchedList.size();
        float sum = 0;

        for (PairMatched pairMatched : pairMatchedList) {
            Sex sexA = pairMatched.soloA.person.sex();
            Sex sexB = pairMatched.soloB.person.sex();
            if (sexA.equals(sexB)) {
                sum++;
            }
        }

        return 1f - sum / maxValue;
    }

    private static float calcFoodPreference(List<PairMatched> pairMatchedList) {
        float maxValue = pairMatchedList.size() * 2;
        float sum = 0;

        for (PairMatched pairMatched : pairMatchedList) {
            FoodPreference fA = pairMatched.soloA.foodPreference;
            FoodPreference fB = pairMatched.soloB.foodPreference;

            if(!(fA.equals(FoodPreference.NONE) || fB.equals(FoodPreference.NONE))) {
                int valueA = MatchingTools.getFoodPreference(fA);
                int valueB = MatchingTools.getFoodPreference(fB);
                sum += Math.abs(valueA - valueB);
            }
        }

        return 1f - sum / maxValue;
    }

    private static float percentAndRound(float value) {
        return ((float) Math.round(value * 10000)) / 100;
    }
}
