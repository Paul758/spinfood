package org.example.logic.metrics;

import org.example.Main;
import org.example.data.enums.FoodPreference;

/**
 * @version 1.0
 * Class containing static methods to be used for calculating metrics
 * Contais a mehthod to "map" a certain food preference to a value
 * Contains a method to round a double to a certain number of decimal places
 */
public class MetricTools {
    public static final double idealGenderRatio = 0.5;

    /**
     * @param foodPreference a FoodPreference enum
     * @return the int value of the food preference, to better calculate the preference deviation and perform more accurate matching
     */
    public static int getPreferenceValue(FoodPreference foodPreference) {
        return switch (foodPreference) {
            case NONE, MEAT -> 0;
            case VEGGIE -> 1;
            case VEGAN -> 2;
        };
    }

    /**
     * Basic Math.round() method, but with a specified number of decimal places
     * @param value the double value to be rounded
     * @param decimalPlaces the number of decimal places to round to
     * @return the rounded double value
     */
    public static double round(double value, int decimalPlaces) {
        double scale = Math.pow(10, decimalPlaces);
        return Math.round(value * scale) / scale;
    }
}
