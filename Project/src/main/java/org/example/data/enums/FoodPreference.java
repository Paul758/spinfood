package org.example.data.enums;


public enum FoodPreference {
    MEAT, NONE, VEGGIE, VEGAN;

    public static FoodPreference parseFoodPreference(String data){
        switch (data) {
            case "meat":
                return MEAT;
            case "none":
                return NONE;
            case "veggie":
                return VEGGIE;
            case "vegan":
                return VEGAN;
        }
        throw new RuntimeException("Tried to parse an unexpected foodPreference: " + data + "Maybe there is an error in the .csv");
    }
}
