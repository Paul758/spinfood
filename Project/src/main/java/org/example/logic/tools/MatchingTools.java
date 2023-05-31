package org.example.logic.tools;

import org.example.data.enums.FoodPreference;
import org.example.data.factory.Person;

/**
 * Tools for calculating deviations from age and food preference of a matched pair
 */
public class MatchingTools {

    /**
     * Calculates the int-value age group of a participant
     * @param age age of a participant
     * @return int value of the age group
     */
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

    /**
     * Calculates the int-value food preference  of a participant
     * @param foodPreference food preference of a participant
     * @return int value of the food preference
     */
    public static int getIntValueFoodPreference(FoodPreference foodPreference){
        return switch (foodPreference) {
            case NONE -> 0;
            case MEAT -> 1;
            case VEGGIE -> 2;
            case VEGAN -> 3;
        };
    }

    /**
     * Calculates the deviation of two participants' food preferences
     * @param foodPreferenceA food Preference of a participant A
     * @param foodPreferenceB food Preference of a participant B
     * @return the int-value food preference distance between the food preferences of the two participants
     */
    public static int calculateFoodPreferenceDeviation(FoodPreference foodPreferenceA, FoodPreference foodPreferenceB) {
        int foodValueA = MatchingTools.getIntValueFoodPreference(foodPreferenceA);
        int foodValueB = MatchingTools.getIntValueFoodPreference(foodPreferenceB);
        return Math.abs(foodValueA - foodValueB);
    }

    /**
     * Calculates the deviation of two participants' ages
     * @param personA the person data of a participant A
     * @param personB the person data of a participant B
     * @return the int-value age distance between ages of the two participants
     */
    public static int calculateAgeRangeDeviation(Person personA, Person personB) {
        int ageValueA = MatchingTools.getAgeRange(personA.age());
        int ageValueB = MatchingTools.getAgeRange(personB.age());
        return  Math.abs(ageValueA - ageValueB);
    }
}
