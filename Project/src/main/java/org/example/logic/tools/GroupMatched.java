package org.example.logic.tools;

import org.example.data.enums.FoodPreference;

public class GroupMatched {

    PairMatched pairA;
    PairMatched pairB;
    PairMatched pairC;

    public GroupMatched(PairMatched pairA, PairMatched pairB, PairMatched pairC) {
        this.pairA = pairA;
        this.pairB = pairB;
        this.pairC = pairC;
    }

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

    @Override
    public String toString() {
        return "GroupMatched{" +
                "pairA=" + pairA +
                ", pairB=" + pairB +
                ", pairC=" + pairC +
                '}';
    }
}
