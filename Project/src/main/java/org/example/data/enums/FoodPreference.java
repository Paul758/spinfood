package org.example.data.enums;


import org.example.data.tools.Keywords;

public enum FoodPreference {
    MEAT, NONE, VEGGIE, VEGAN;

    /**
     * Converts the String value of the food preference to the corresponding enum value
     * @author Paul Groß
     * @param data data from the csv file
     * @return the corresponding enum value
     * @throws IllegalStateException if the data is not a valid food preference
     */
    public static FoodPreference parseFoodPreference(String data) {
        return switch (data) {
            case Keywords.meat -> MEAT;
            case Keywords.none -> NONE;
            case Keywords.veggie -> VEGGIE;
            case Keywords.vegan -> VEGAN;
            default -> throw new IllegalStateException("Unexpected value: " + data);
        };
    }

    public static FoodPreference parseFoodPreference(int data) {
        return switch (data) {
            case 0 -> NONE;
            case 1 -> MEAT;
            case 2 -> VEGGIE;
            case 3 -> VEGAN;
            default -> throw new IllegalStateException("Unexpected value: " + data);
        };
    }
}
