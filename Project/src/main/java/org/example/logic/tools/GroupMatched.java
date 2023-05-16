package org.example.logic.tools;

import org.example.data.enums.FoodPreference;

public class GroupMatched {

    PairMatched pairA;
    PairMatched pairB;
    PairMatched pairC;

    protected FoodPreference calculateFoodPreference() {
        //Calculate here
        int foodPreferencePairA = MatchingTools.getFoodPreference(pairA.getFoodPreference());
        int foodPreferencePairB = MatchingTools.getFoodPreference(pairB.getFoodPreference());
        int foodPreferencePairC = MatchingTools.getFoodPreference(pairC.getFoodPreference());
        return FoodPreference.parseFoodPreference(Math.max(foodPreferencePairA, Math.max(foodPreferencePairB, foodPreferencePairC)));

        //throw new IllegalStateException("not implemented yet");
    }

    protected int calculateAgeRangeDeviation() {
        return pairA.ageRangeDeviation + pairB.ageRangeDeviation + pairC.ageRangeDeviation;
    }
}
