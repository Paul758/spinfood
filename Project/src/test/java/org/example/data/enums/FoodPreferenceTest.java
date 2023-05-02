package org.example.data.enums;

import org.example.data.tools.Keywords;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.Key;

import static org.junit.jupiter.api.Assertions.*;

class FoodPreferenceTest {


    @Test
    void parseFoodPreferenceMeat() {
        String meat = Keywords.meat;
        Assertions.assertEquals(FoodPreference.MEAT, FoodPreference.parseFoodPreference(meat));
    }

    @Test
    void parseFoodPreferenceVeggie() {
        String veggie = Keywords.veggie;
        Assertions.assertEquals(FoodPreference.VEGGIE, FoodPreference.parseFoodPreference(veggie));
    }

    @Test
    void parseFoodPreferenceVegan() {
        String vegan = Keywords.vegan;
        Assertions.assertEquals(FoodPreference.VEGAN, FoodPreference.parseFoodPreference(vegan));
    }

    @Test
    void parseFoodPreferenceNone() {
        String none = Keywords.none;
        Assertions.assertEquals(FoodPreference.NONE, FoodPreference.parseFoodPreference(none));
    }

    @Test
    void parseFoodPreferenceInvalidTest() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            FoodPreference.parseFoodPreference("Unexpected Value");
        });
    }
}