package org.example.logic.tools;

import org.example.data.enums.FoodPreference;
import org.example.data.structures.Solo;
public class PairMatched extends Match {

    Solo soloA;
    Solo soloB;

    FoodPreference foodPreference;

    int foodPreferenceDeviation;
    int ageRangeDeviation;

    boolean prematched;

    public PairMatched(Solo soloA, Solo soloB, FoodPreference foodPreference, boolean prematched){
        this.soloA = soloA;
        this.soloB = soloB;
        this.foodPreference = foodPreference;

        int foodValueA = MatchingTools.getFoodPreference(soloA.foodPreference);
        int foodValueB = MatchingTools.getFoodPreference(soloB.foodPreference);
        foodPreferenceDeviation = Math.abs(foodValueA - foodValueB);

        this.prematched = prematched;
    }

    @Override
    protected FoodPreference calculateFoodPreference() {

        int foodValueA = MatchingTools.getFoodPreference(soloA.foodPreference);
        int foodValueB = MatchingTools.getFoodPreference(soloB.foodPreference);

        return FoodPreference.parseFoodPreference(Math.max(foodValueA, foodValueB));

       // FoodPreference foodPreferenceA = soloA.foodPreference;
       // FoodPreference foodPreferenceB = soloB.foodPreference;


       /* if(foodPreferenceA.equals(FoodPreference.NONE)){
            return foodPreferenceB;
        }
        if(foodPreferenceB.equals(FoodPreference.NONE)){
            return foodPreferenceA;
        }
        if(foodPreferenceA.equals(FoodPreference.MEAT)){
            return foodPreferenceA;
        }
        if(foodPreferenceB.equals(FoodPreference.MEAT)){
            return foodPreferenceB;
        }
        if(foodPreferenceA.equals(FoodPreference.VEGGIE) && foodPreferenceB.equals(FoodPreference.VEGGIE)){
            return FoodPreference.VEGGIE;
        }
        if(foodPreferenceA.equals(FoodPreference.VEGGIE) && foodPreferenceB.equals(FoodPreference.VEGAN)){
            return FoodPreference.VEGAN;
        }
        if(foodPreferenceB.equals(FoodPreference.VEGGIE) && foodPreferenceA.equals(FoodPreference.VEGAN)){
            return FoodPreference.VEGAN;
        }
        if(foodPreferenceA.equals(FoodPreference.VEGAN) && foodPreferenceB.equals(FoodPreference.VEGAN)){
            return FoodPreference.VEGAN;
        }*/

       // throw new IllegalStateException("no food preference found");
    }

    @Override
    protected int calculateAgeRangeDeviation() {
        int ageValueA = MatchingTools.getAgeRange(soloA.person.age());
        int ageValueB = MatchingTools.getAgeRange(soloB.person.age());
        return  Math.abs(ageValueA - ageValueB);
    }

}
