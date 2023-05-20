package org.example.logic.tools;

import org.example.data.enums.Sex;
import org.example.data.structures.Solo;

import java.util.List;

public class Benchmark {

    public static void matchedPairsBenchmark(List<PairMatched> pairs, List<Solo> solos) {
        int pairSize = pairs.size() * 2;
        int soloSize = solos.size();
        int notMatchedSize = calcIsMatched(pairs, solos);

        boolean isCorrect = isCorrect(pairs);

        float countValue = calcCount(pairs.size(), solos.size());
        float ageDifference = calcAgeDifference(pairs);
        float genderDiversity = calcGenderDiversity(pairs);
        float foodPreference = calcFoodPreference(pairs);
        float sum = (ageDifference + genderDiversity + foodPreference) / 3;

        System.out.println("Is correct: " + isCorrect);
        System.out.println("Total: " + soloSize + ", in pairs: " + pairSize + ", Not Matched: " + notMatchedSize + ", Remaining: " + (soloSize - pairSize));
        System.out.println("Count:              " + percentAndRound(countValue) + "%");
        System.out.println("Age:                " + percentAndRound(ageDifference) + "%");
        System.out.println("Gender Diversity:   " + percentAndRound(genderDiversity) + "%");
        System.out.println("FoodPreference:     " + percentAndRound(foodPreference) + "%");
        System.out.println("Sum:                " + percentAndRound(sum) + "%");

    }

    private static float calcCount(int pairCount, int soloCount) {
        pairCount = 2 * pairCount;
        return (float) pairCount / (float) soloCount;
    }

    private static float calcAgeDifference(List<PairMatched> pairMatchedList) {
        float maxValue = pairMatchedList.size() * 2 * 8;
        float sumAgeDifference = 0;
        for (PairMatched pairMatched : pairMatchedList) {
            sumAgeDifference += pairMatched.ageRangeDeviation;
        }

        return 1f - sumAgeDifference / maxValue;
    }

    private static float calcGenderDiversity(List<PairMatched> pairMatchedList) {
        float maxValue = pairMatchedList.size();
        float sum = 0;

        for (PairMatched pairMatched : pairMatchedList) {
            Sex sexA = pairMatched.getPersonA().sex();
            Sex sexB = pairMatched.getPersonB().sex();
            if (!sexA.equals(Sex.OTHER) && sexA.equals(sexB)) {
                sum++;
            }
        }

        return 1f - sum / maxValue;
    }

    private static float calcFoodPreference(List<PairMatched> pairMatchedList) {
        float maxValue = pairMatchedList.size() * 2;
        float sum = 0;

        for (PairMatched pairMatched : pairMatchedList) {
            sum += pairMatched.foodPreferenceDeviation;
        }

        return 1f - sum / maxValue;
    }

    private static float percentAndRound(float value) {
        return ((float) Math.round(value * 10000)) / 100;
    }

    private static int calcIsMatched(List<PairMatched> pairs, List<Solo> solos) {
        int counter = 0;
        for (Solo solo : solos) {
            boolean isMatched = false;
            for (PairMatched pair : pairs) {
               if (pair.containsPerson(solo.person)) {
                   isMatched = true;
                   break;
               }
            }
            if (!isMatched) {
                counter++;
            }
        }
        return counter;
    }

    private static boolean isCorrect(List<PairMatched> pairMatchedList) {
        boolean isCorrect = true;
            for (PairMatched pair : pairMatchedList) {
                isCorrect &= pair.isValid();
            }
        return isCorrect;
    }
}
