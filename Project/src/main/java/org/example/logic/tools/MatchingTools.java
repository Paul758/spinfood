package org.example.logic.tools;

import org.example.data.enums.FoodPreference;

public class MatchingTools {

    public static int getAgeRange(int age){
        if (age < 0) throw new IllegalStateException("Unknown age: " + age);
        if (age <= 17) return 0;
        if (age <= 23) return 1;
        if (age <= 27) return 2;
        if (age <= 30) return 3;
        if (age <= 35) return 4;
        if (age <= 41) return 5;
        if (age <= 46) return 6;
        if (age <= 56) return 7;
        else return 8;
    }


    public static int getFoodPreference(FoodPreference foodPreference){
        return switch (foodPreference) {
            case NONE, MEAT -> 0;
            case VEGGIE -> 1;
            case VEGAN -> 2;
        };
    }
}
