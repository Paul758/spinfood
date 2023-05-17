package org.example.logic.tools;

import org.example.data.enums.FoodPreference;

public class GroupMatched extends Match {

    PairMatched pairA;
    PairMatched pairB;
    PairMatched pairC;

    public GroupMatched(PairMatched pairA, PairMatched pairB, PairMatched pairC) {
        this.pairA = pairA;
        this.pairB = pairB;
        this.pairC = pairC;
    }


    @Override
    protected FoodPreference calculateFoodPreference() {
        //Calculate here
        int foodPreferencePairA = MatchingTools.getFoodPreference(pairA.foodPreference);
        int foodPreferencePairB = MatchingTools.getFoodPreference(pairB.foodPreference);
        int foodPreferencePairC = MatchingTools.getFoodPreference(pairC.foodPreference);
        return FoodPreference.parseFoodPreference(Math.max(foodPreferencePairA, Math.max(foodPreferencePairB, foodPreferencePairC)));

        //throw new IllegalStateException("not implemented yet");
    }

    @Override
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
