package org.example.logic.tools;

import org.example.data.enums.FoodPreference;
import org.example.data.enums.Sex;
import org.example.data.structures.Solo;

import java.util.ArrayList;
import java.util.List;

public class GroupMatched extends Match {

    public PairMatched pairA;
    public PairMatched pairB;
    public PairMatched pairC;
    public PairMatched cook;

    public FoodPreference foodPreference;

    public GroupMatched(PairMatched pairA, PairMatched pairB, PairMatched pairC, PairMatched cook) {
        this.pairA = pairA;
        this.pairB = pairB;
        this.pairC = pairC;
        this.cook = cook;
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
    protected float calculateSexDeviation(){
        int male = 0;
        int female = 0;
        int other = 0;

        for (PairMatched pair : getPairList()) {
            Sex personASex = pair.soloA.person.sex();
            Sex personBSex = pair.soloB.person.sex();

            switch (personASex){
                case MALE -> male++;
                case FEMALE -> female++;
                case OTHER -> other++;
            }

            switch (personBSex){
                case MALE -> male++;
                case FEMALE -> female++;
                case OTHER -> other++;
            }
        }

        if(male == female && male == other){
            return 0.5f;
        } else {
            return ((float) Math.max(male, Math.max(female, other))) / 6;
        }
    }

    @Override
    protected int calculateFoodPreferenceDeviation() {
        return Math.abs(MatchingTools.getFoodPreference(this.foodPreference) - MatchingTools.getFoodPreference(pairA.foodPreference))
               + Math.abs(MatchingTools.getFoodPreference(this.foodPreference) - MatchingTools.getFoodPreference(pairB.foodPreference))
               + Math.abs(MatchingTools.getFoodPreference(this.foodPreference) - MatchingTools.getFoodPreference(pairC.foodPreference));
    }

    public ArrayList<PairMatched> getPairList(){
        return new ArrayList<>(List.of(pairA, pairB, pairC));
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
