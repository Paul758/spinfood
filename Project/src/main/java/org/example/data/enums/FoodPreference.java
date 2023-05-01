package org.example.data.enums;


import org.example.data.tools.Keywords;

public enum FoodPreference {
    MEAT, NONE, VEGGIE, VEGAN;

    public static FoodPreference parseFoodPreference(String data) {
        return switch (data) {
            case Keywords.meat -> MEAT;
            case Keywords.none -> NONE;
            case Keywords.veggie -> VEGGIE;
            case Keywords.vegan -> VEGAN;
            default -> throw new IllegalStateException("Unexpected value: " + data);
        };
    }
}
