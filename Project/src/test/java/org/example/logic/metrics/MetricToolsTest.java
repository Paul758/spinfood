package org.example.logic.metrics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MetricToolsTest {

    @Test
    void getPreferenceValue() {

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