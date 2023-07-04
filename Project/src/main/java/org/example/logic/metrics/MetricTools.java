package org.example.logic.metrics;

import org.example.data.enums.FoodPreference;

public class MetricTools {
    public static final double idealGenderRatio = 0.5;

    public static int getPreferenceValue(FoodPreference foodPreference) {
        return switch (foodPreference) {
            case NONE, MEAT -> 0;
            case VEGGIE -> 1;
            case VEGAN -> 2;
        };
    }

    public static double round(double value, int decimalPlaces) {
        double scale = Math.pow(10, decimalPlaces);
        return Math.round(value * scale) / scale;
    }
}
