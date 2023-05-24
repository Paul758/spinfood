package org.example.logic.structures;

import org.example.data.enums.FoodPreference;
import org.example.data.enums.Sex;
import org.example.logic.tools.MatchingTools;
import org.example.logic.enums.MealType;

import java.util.ArrayList;
import java.util.List;

public class GroupMatched extends Match {

    public PairMatched pairA;
    public PairMatched pairB;
    public PairMatched pairC;
    public PairMatched cook;

    public FoodPreference foodPreference;

    public MealType mealType;

    public GroupMatched(PairMatched pairA, PairMatched pairB, PairMatched pairC, PairMatched cook, MealType mealType) {
        this.pairA = pairA;
        this.pairB = pairB;
        this.pairC = pairC;
        this.cook = cook;
        this.mealType = mealType;
    }

    @Override
    public FoodPreference calculateFoodPreference() {
        //Calculate here
        int foodPreferencePairA = MatchingTools.getFoodPreference(pairA.foodPreference);
        int foodPreferencePairB = MatchingTools.getFoodPreference(pairB.foodPreference);
        int foodPreferencePairC = MatchingTools.getFoodPreference(pairC.foodPreference);
        return FoodPreference.parseFoodPreference(Math.max(foodPreferencePairA, Math.max(foodPreferencePairB, foodPreferencePairC)));

    }

    @Override
    public int calculateAgeRangeDeviation() {
        return pairA.ageRangeDeviation + pairB.ageRangeDeviation + pairC.ageRangeDeviation;
    }

    @Override
    public float calculateSexDeviation(){
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
    public int calculateFoodPreferenceDeviation() {
        return Math.abs(MatchingTools.getFoodPreference(this.foodPreference) - MatchingTools.getFoodPreference(pairA.foodPreference))
               + Math.abs(MatchingTools.getFoodPreference(this.foodPreference) - MatchingTools.getFoodPreference(pairB.foodPreference))
               + Math.abs(MatchingTools.getFoodPreference(this.foodPreference) - MatchingTools.getFoodPreference(pairC.foodPreference));
    }

    public ArrayList<PairMatched> getPairList(){
        return new ArrayList<>(List.of(pairA, pairB, pairC));
    }

    public boolean contains(PairMatched pair){
        return pair.equals(pairA) || pair.equals(pairB) || pair.equals(pairC);
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
