package org.example.logic.tools;

import org.example.data.enums.FoodPreference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatchingToolsTest {

    @Test
    void getAgeRange() {
        assertThrows(IllegalStateException.class, () -> MatchingTools.getAgeRange(-1));

        Assertions.assertEquals(0, MatchingTools.getAgeRange(0));
        Assertions.assertEquals(0, MatchingTools.getAgeRange(17));

        Assertions.assertEquals(1, MatchingTools.getAgeRange(18));
        Assertions.assertEquals(1, MatchingTools.getAgeRange(23));

        Assertions.assertEquals(2, MatchingTools.getAgeRange(24));
        Assertions.assertEquals(2, MatchingTools.getAgeRange(27));

        Assertions.assertEquals(3, MatchingTools.getAgeRange(28));
        Assertions.assertEquals(3, MatchingTools.getAgeRange(30));

        Assertions.assertEquals(4, MatchingTools.getAgeRange(31));
        Assertions.assertEquals(4, MatchingTools.getAgeRange(35));

        Assertions.assertEquals(5, MatchingTools.getAgeRange(36));
        Assertions.assertEquals(5, MatchingTools.getAgeRange(41));

        Assertions.assertEquals(6, MatchingTools.getAgeRange(42));
        Assertions.assertEquals(6, MatchingTools.getAgeRange(46));

        Assertions.assertEquals(7, MatchingTools.getAgeRange(47));
        Assertions.assertEquals(7, MatchingTools.getAgeRange(56));

        Assertions.assertEquals(8, MatchingTools.getAgeRange(57));
        Assertions.assertEquals(8, MatchingTools.getAgeRange(100));
    }


    @Test
    void getFoodPreference() {
        Assertions.assertEquals(0, MatchingTools.getIntValueFoodPreference(FoodPreference.NONE));
        Assertions.assertEquals(1, MatchingTools.getIntValueFoodPreference(FoodPreference.MEAT));
        Assertions.assertEquals(2, MatchingTools.getIntValueFoodPreference(FoodPreference.VEGGIE));
        Assertions.assertEquals(3, MatchingTools.getIntValueFoodPreference(FoodPreference.VEGAN));
    }
}