package org.example.logic.metrics;

import org.example.logic.structures.PairMatched;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;


class PairListMetricsTest {

    static MockedStatic<PairMetrics> mockedPairMetrics;
    static List<PairMatched> emptyList;
    static List<PairMatched> oneElementList;
    static List<PairMatched> threeElementsList;
    @BeforeAll
    static void setup() {
        mockedPairMetrics = Mockito.mockStatic(PairMetrics.class);

        emptyList = new ArrayList<>();

        oneElementList = new ArrayList<>();
        oneElementList.add(Mockito.mock(PairMatched.class));

        threeElementsList = new ArrayList<>();
        threeElementsList.add(Mockito.mock(PairMatched.class));
        threeElementsList.add(Mockito.mock(PairMatched.class));
        threeElementsList.add(Mockito.mock(PairMatched.class));
    }

    @AfterAll
    static void close() {
        mockedPairMetrics.close();
    }

    @Test
    void calcAgeDifference_oneElementInList() {
        Mockito.when(PairMetrics.calcAgeDifference(oneElementList.get(0))).thenReturn(1.0);

        Assertions.assertEquals(1.0, PairListMetrics.calcAgeDifference(oneElementList));
    }

    @Test
    void calcAgeDifference_threeElementsInList() {
        Mockito.when(PairMetrics.calcAgeDifference(threeElementsList.get(0))).thenReturn(5.0);
        Mockito.when(PairMetrics.calcAgeDifference(threeElementsList.get(1))).thenReturn(0.0);
        Mockito.when(PairMetrics.calcAgeDifference(threeElementsList.get(2))).thenReturn(4.0);

        Assertions.assertEquals(3.0, PairListMetrics.calcAgeDifference(threeElementsList));
    }

    @Test
    void calcAgeDifference_throwsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> PairListMetrics.calcAgeDifference(emptyList));
    }

    @Test
    void calcGenderDiversity_oneElementInList() {
        Mockito.when(PairMetrics.calcGenderDiversity(oneElementList.get(0))).thenReturn(0.5);

        Assertions.assertEquals(0.5, PairListMetrics.calcGenderDiversity(oneElementList));
    }

    @Test
    void calcGenderDiversity_threeElementsInList() {
        Mockito.when(PairMetrics.calcGenderDiversity(threeElementsList.get(0))).thenReturn(0.5);
        Mockito.when(PairMetrics.calcGenderDiversity(threeElementsList.get(1))).thenReturn(0.0);
        Mockito.when(PairMetrics.calcGenderDiversity(threeElementsList.get(2))).thenReturn(0.5);

        Assertions.assertEquals(0.33, PairListMetrics.calcGenderDiversity(threeElementsList), 0.01);
    }

    @Test
    void calcGenderDiversity_throwsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> PairListMetrics.calcAgeDifference(emptyList));
    }

    @Test
    void calcPreferenceDeviation_oneElementInList() {
        Mockito.when(PairMetrics.calcPreferenceDeviation(oneElementList.get(0))).thenReturn(1.0);

        Assertions.assertEquals(1.0, PairListMetrics.calcPreferenceDeviation(oneElementList));
    }

    @Test
    void calcPreferenceDeviation_threeElementsInList() {
        Mockito.when(PairMetrics.calcPreferenceDeviation(threeElementsList.get(0))).thenReturn(0.0);
        Mockito.when(PairMetrics.calcPreferenceDeviation(threeElementsList.get(1))).thenReturn(2.0);
        Mockito.when(PairMetrics.calcPreferenceDeviation(threeElementsList.get(2))).thenReturn(2.0);

        Assertions.assertEquals(1.33, PairListMetrics.calcPreferenceDeviation(threeElementsList), 0.01);
    }

    @Test
    void calcPreferenceDeviation_throwsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> PairListMetrics.calcPreferenceDeviation(emptyList));
    }

    @Test
    void isValid_true() {
        Mockito.when(PairMetrics.isValid(threeElementsList.get(0))).thenReturn(true);
        Mockito.when(PairMetrics.isValid(threeElementsList.get(1))).thenReturn(true);
        Mockito.when(PairMetrics.isValid(threeElementsList.get(2))).thenReturn(true);

        Assertions.assertTrue(PairListMetrics.isValid(threeElementsList));
    }

    @Test
    void isValid_false() {
        Mockito.when(PairMetrics.isValid(threeElementsList.get(0))).thenReturn(true);
        Mockito.when(PairMetrics.isValid(threeElementsList.get(1))).thenReturn(true);
        Mockito.when(PairMetrics.isValid(threeElementsList.get(2))).thenReturn(false);

        Assertions.assertFalse(PairListMetrics.isValid(threeElementsList));
    }

    @Test
    void isValid_emptyListIsTrue() {
        Assertions.assertTrue(PairListMetrics.isValid(emptyList));
    }
}