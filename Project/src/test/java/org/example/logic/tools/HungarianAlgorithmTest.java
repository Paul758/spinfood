package org.example.logic.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class HungarianAlgorithmTest {

    // Reference: https://brilliant.org/wiki/hungarian-matching/
    @Test
    void match3x3() {
        double[][] data = {
                {108, 125, 150},
                {150, 135, 175},
                {122, 148, 250}
        };

        int[][] expected = {
                {0,2},
                {1,1},
                {2,0}
        };

        int[][] actual = HungarianAlgorithm.match(data);
        Arrays.sort(actual, Comparator.comparing(a -> a[0]));

        Assertions.assertArrayEquals(expected, actual);
    }

    // Reference: https://www.hungarianalgorithm.com/examplehungarianalgorithm.php
    @Test
    void match4x4() {
        double[][] data = {
                {77, 37, 49, 92},
                {8, 9, 98, 23},
                {11, 69, 5, 86},
                {82, 83, 69, 92},
        };

        int[][] expected = {
                {0,1},
                {1,3},
                {2,0},
                {3,2}
        };

        int[][] actual = HungarianAlgorithm.match(data);
        Arrays.sort(actual, Comparator.comparing(a -> a[0]));

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void testException() {
        double[][] data = {
                {1, 2},
                {3}
        };

        Assertions.assertThrows(IllegalArgumentException.class, () -> HungarianAlgorithm.match(data));
    }
}