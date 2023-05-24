package org.example.logic.tools.benchmarks;

import org.example.data.enums.FoodPreference;
import org.example.data.enums.Sex;
import org.example.data.structures.Solo;
import org.example.logic.structures.PairMatched;
import org.example.logic.tools.MatchingTools;

import java.util.List;

public class PairBenchmark
{

    public static void matchedPairsBenchmark(List<PairMatched> pairs, List<Solo> solos) {
        int pairSize = pairs.size() * 2;
        int soloSize = solos.size();
        int notMatchedSize = calcIsMatched(pairs, solos);

        boolean isCorrect = isCorrect(pairs, solos);

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
            FoodPreference fA = pairMatched.soloA.foodPreference;
            FoodPreference fB = pairMatched.soloB.foodPreference;

            int fAvalue = MatchingTools.getFoodPreference(fA);
            int fBvalue = MatchingTools.getFoodPreference(fB);

            sum += Math.abs(fAvalue - fBvalue);
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
               if (pair.soloA.equals(solo) || pair.soloB.equals(solo)) {
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

    private static boolean isCorrect(List<PairMatched> pairMatchedList, List<Solo> solos) {
        boolean isCorrect = true;
        for (Solo solo : solos) {
            int counter = 0;
            for (PairMatched pair : pairMatchedList) {
                if (pair.soloA.equals(solo)) {
                    counter++;
                }
                if (pair.soloB.equals(solo)) {
                    counter++;
                }
            }
            if (counter > 1) {
                isCorrect = false;
                break;
            }
        }
        return isCorrect;
    }
}
