package org.example.logic.metrics;

import org.example.data.enums.FoodPreference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MetricToolsTest {

    @Test
    void getPreferenceValue_MeatPreference() {
        Assertions.assertEquals(0, MetricTools.getPreferenceValue(FoodPreference.MEAT));
    }

    @Test
    void getPreferenceValue_NonePreference() {
        Assertions.assertEquals(0, MetricTools.getPreferenceValue(FoodPreference.NONE));
    }

    @Test
    void getPreferenceValue_VeggiePreference() {
        Assertions.assertEquals(1, MetricTools.getPreferenceValue(FoodPreference.VEGGIE));
    }

    @Test
    void getPreferenceValue_VeganPreference() {
        Assertions.assertEquals(2, MetricTools.getPreferenceValue(FoodPreference.VEGAN));
    }

    @Test
    void round1() {
        double value = 3.14159;
        int decimalPlaces = 2;
        double expected = 3.14;

        Assertions.assertEquals(expected, MetricTools.round(value, decimalPlaces));
    }

    @Test
    void round2() {
        double value = 3.14159;
        int decimalPlaces = 0;
        double expected = 3.0;

        Assertions.assertEquals(expected, MetricTools.round(value, decimalPlaces));
    }

    @Test
    void round3() {
        double value = -2.71828;
        int decimalPlaces = 3;
        double expected = -2.718;

        Assertions.assertEquals(expected, MetricTools.round(value, decimalPlaces));
    }

    @Test
    void round4() {
        double value = -2.71828;
        int decimalPlaces = 0;
        double expected = -3.0;

        Assertions.assertEquals(expected, MetricTools.round(value, decimalPlaces));
    }
}