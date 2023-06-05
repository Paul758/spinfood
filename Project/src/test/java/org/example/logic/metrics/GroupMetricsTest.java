package org.example.logic.metrics;

import org.example.data.enums.FoodPreference;
import org.example.data.enums.Sex;
import org.example.data.factory.Person;
import org.example.data.structures.Solo;
import org.example.logic.enums.MealType;
import org.example.logic.structures.GroupMatched;
import org.example.logic.structures.PairMatched;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

class GroupMetricsTest {

    static MockedStatic<PairMetrics> mockedPairMetrics;
    static GroupMatched mockGroup;
    static List<PairMatched> mockPairs;
    static GroupMatched mockGroupEmpty;
    static List<PairMatched> mockPairsEmpty;

    @BeforeAll
    static void setup() {
        mockedPairMetrics = Mockito.mockStatic(PairMetrics.class);

        mockGroup = Mockito.mock(GroupMatched.class);
        mockPairs = new ArrayList<>();

        mockPairs.add(Mockito.mock(PairMatched.class));
        mockPairs.add(Mockito.mock(PairMatched.class));
        mockPairs.add(Mockito.mock(PairMatched.class));
        Mockito.when(mockGroup.getPairList()).thenReturn(mockPairs);

        for (int i = 0; i < 3; i++) {
            Solo mockSoloA = Mockito.mock(Solo.class);
            Solo mockSoloB = Mockito.mock(Solo.class);
            Person mockPersonA = Mockito.mock(Person.class);
            Person mockPersonB = Mockito.mock(Person.class);

            Mockito.when(mockSoloA.getPerson()).thenReturn(mockPersonA);
            Mockito.when(mockSoloB.getPerson()).thenReturn(mockPersonB);
            Mockito.when(mockPairs.get(i).getSoloA()).thenReturn(mockSoloA);
            Mockito.when(mockPairs.get(i).getSoloB()).thenReturn(mockSoloB);
        }

        mockGroupEmpty = Mockito.mock(GroupMatched.class);
        mockPairsEmpty = new ArrayList<>();

        Mockito.when(mockGroupEmpty.getPairList()).thenReturn(mockPairsEmpty);
    }

    @AfterAll
    static void close() {
        mockedPairMetrics.close();
    }

    @Test
    void calcAgeDifference() {
        Mockito.when(PairMetrics.calcAgeDifference(mockPairs.get(0))).thenReturn(0.0);
        Mockito.when(PairMetrics.calcAgeDifference(mockPairs.get(1))).thenReturn(2.0);
        Mockito.when(PairMetrics.calcAgeDifference(mockPairs.get(2))).thenReturn(5.0);

        Assertions.assertEquals(2.33, GroupMetrics.calcAgeDifference(mockGroup), 0.01);
    }

    @Test
    void calcAgeDifference_throwsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> GroupMetrics.calcAgeDifference(mockGroupEmpty));
    }

    @Test
    void calcGenderDiversity_threeFemaleThreeMale() {
        Mockito.when(mockPairs.get(0).getSoloA().getPerson().sex()).thenReturn(Sex.FEMALE);
        Mockito.when(mockPairs.get(0).getSoloB().getPerson().sex()).thenReturn(Sex.FEMALE);
        Mockito.when(mockPairs.get(1).getSoloA().getPerson().sex()).thenReturn(Sex.FEMALE);
        Mockito.when(mockPairs.get(1).getSoloB().getPerson().sex()).thenReturn(Sex.MALE);
        Mockito.when(mockPairs.get(2).getSoloA().getPerson().sex()).thenReturn(Sex.MALE);
        Mockito.when(mockPairs.get(2).getSoloB().getPerson().sex()).thenReturn(Sex.MALE);

        Assertions.assertEquals(0, GroupMetrics.calcGenderDiversity(mockGroup), 0.01);
    }

    @Test
    void calcGenderDiversity_twoFemaleTwoMaleTwoOther() {
        Mockito.when(mockPairs.get(0).getSoloA().getPerson().sex()).thenReturn(Sex.FEMALE);
        Mockito.when(mockPairs.get(0).getSoloB().getPerson().sex()).thenReturn(Sex.MALE);
        Mockito.when(mockPairs.get(1).getSoloA().getPerson().sex()).thenReturn(Sex.MALE);
        Mockito.when(mockPairs.get(1).getSoloB().getPerson().sex()).thenReturn(Sex.OTHER);
        Mockito.when(mockPairs.get(2).getSoloA().getPerson().sex()).thenReturn(Sex.OTHER);
        Mockito.when(mockPairs.get(2).getSoloB().getPerson().sex()).thenReturn(Sex.FEMALE);

        Assertions.assertEquals(0.16, GroupMetrics.calcGenderDiversity(mockGroup), 0.01);
    }

    @Test
    void calcGenderDiversity_sixFemale() {
        Mockito.when(mockPairs.get(0).getSoloA().getPerson().sex()).thenReturn(Sex.FEMALE);
        Mockito.when(mockPairs.get(0).getSoloB().getPerson().sex()).thenReturn(Sex.FEMALE);
        Mockito.when(mockPairs.get(1).getSoloA().getPerson().sex()).thenReturn(Sex.FEMALE);
        Mockito.when(mockPairs.get(1).getSoloB().getPerson().sex()).thenReturn(Sex.FEMALE);
        Mockito.when(mockPairs.get(2).getSoloA().getPerson().sex()).thenReturn(Sex.FEMALE);
        Mockito.when(mockPairs.get(2).getSoloB().getPerson().sex()).thenReturn(Sex.FEMALE);

        Assertions.assertEquals(0.5, GroupMetrics.calcGenderDiversity(mockGroup), 0.01);
    }

    @Test
    void calcPreferenceDeviation() {
        Mockito.when(PairMetrics.calcPreferenceDeviation(mockPairs.get(0))).thenReturn(0.0);
        Mockito.when(PairMetrics.calcPreferenceDeviation(mockPairs.get(1))).thenReturn(1.0);
        Mockito.when(PairMetrics.calcPreferenceDeviation(mockPairs.get(2))).thenReturn(2.0);

        Assertions.assertEquals(1.0, GroupMetrics.calcPreferenceDeviation(mockGroup));
    }

    @Test
    void calcPreferenceDeviation_throwsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> GroupMetrics.calcPreferenceDeviation(mockGroupEmpty));
    }

    @Test
    void isValid_false_zeroPairsInGroup() {
        Assertions.assertFalse(GroupMetrics.isValid(mockGroupEmpty));
    }

    @Test
    void isValid_false_pairIsInvalid() {
        Mockito.when(PairMetrics.isValid(mockPairs.get(0))).thenReturn(true);
        Mockito.when(PairMetrics.isValid(mockPairs.get(1))).thenReturn(true);
        Mockito.when(PairMetrics.isValid(mockPairs.get(2))).thenReturn(false);

        Assertions.assertFalse(GroupMetrics.isValid(mockGroup));
    }

    @Test
    void isValid_false_invalidFoodComposition() {
        Mockito.when(PairMetrics.isValid(mockPairs.get(0))).thenReturn(true);
        Mockito.when(PairMetrics.isValid(mockPairs.get(1))).thenReturn(true);
        Mockito.when(PairMetrics.isValid(mockPairs.get(2))).thenReturn(true);

        Mockito.when(mockPairs.get(0).getFoodPreference()).thenReturn(FoodPreference.MEAT);
        Mockito.when(mockPairs.get(1).getFoodPreference()).thenReturn(FoodPreference.MEAT);
        Mockito.when(mockPairs.get(2).getFoodPreference()).thenReturn(FoodPreference.VEGAN);

        Assertions.assertFalse(GroupMetrics.isValid(mockGroup));
    }

    @Test
    void isValid_false_noCook() {
        Mockito.when(PairMetrics.isValid(mockPairs.get(0))).thenReturn(true);
        Mockito.when(PairMetrics.isValid(mockPairs.get(1))).thenReturn(true);
        Mockito.when(PairMetrics.isValid(mockPairs.get(2))).thenReturn(true);

        Mockito.when(mockPairs.get(0).getFoodPreference()).thenReturn(FoodPreference.MEAT);
        Mockito.when(mockPairs.get(1).getFoodPreference()).thenReturn(FoodPreference.MEAT);
        Mockito.when(mockPairs.get(2).getFoodPreference()).thenReturn(FoodPreference.MEAT);

        Mockito.when(mockGroup.getCook()).thenReturn(null);

        Assertions.assertFalse(GroupMetrics.isValid(mockGroup));
    }

    @Test
    void isValid_false_noMealType() {
        Mockito.when(PairMetrics.isValid(mockPairs.get(0))).thenReturn(true);
        Mockito.when(PairMetrics.isValid(mockPairs.get(1))).thenReturn(true);
        Mockito.when(PairMetrics.isValid(mockPairs.get(2))).thenReturn(true);

        Mockito.when(mockPairs.get(0).getFoodPreference()).thenReturn(FoodPreference.MEAT);
        Mockito.when(mockPairs.get(1).getFoodPreference()).thenReturn(FoodPreference.MEAT);
        Mockito.when(mockPairs.get(2).getFoodPreference()).thenReturn(FoodPreference.MEAT);

        Mockito.when(mockGroup.getCook()).thenReturn(Mockito.mock(PairMatched.class));

        Mockito.when(mockGroup.getMealType()).thenReturn(MealType.NONE);

        Assertions.assertFalse(GroupMetrics.isValid(mockGroup));
    }

    @Test
    void isValid_true() {
        Mockito.when(PairMetrics.isValid(mockPairs.get(0))).thenReturn(true);
        Mockito.when(PairMetrics.isValid(mockPairs.get(1))).thenReturn(true);
        Mockito.when(PairMetrics.isValid(mockPairs.get(2))).thenReturn(true);

        Mockito.when(mockPairs.get(0).getFoodPreference()).thenReturn(FoodPreference.MEAT);
        Mockito.when(mockPairs.get(1).getFoodPreference()).thenReturn(FoodPreference.MEAT);
        Mockito.when(mockPairs.get(2).getFoodPreference()).thenReturn(FoodPreference.MEAT);

        Mockito.when(mockGroup.getCook()).thenReturn(Mockito.mock(PairMatched.class));

        Mockito.when(mockGroup.getMealType()).thenReturn(MealType.MAIN);

        Assertions.assertTrue(GroupMetrics.isValid(mockGroup));
    }

    @Test
    void hasValidPreferenceComposition_true_threeNone() {
        Mockito.when(mockPairs.get(0).getFoodPreference()).thenReturn(FoodPreference.NONE);
        Mockito.when(mockPairs.get(1).getFoodPreference()).thenReturn(FoodPreference.NONE);
        Mockito.when(mockPairs.get(2).getFoodPreference()).thenReturn(FoodPreference.NONE);

        Assertions.assertTrue(GroupMetrics.hasValidPreferenceComposition(mockPairs));
    }

    @Test
    void hasValidPreferenceComposition_true_threeVeggie() {
        Mockito.when(mockPairs.get(0).getFoodPreference()).thenReturn(FoodPreference.VEGGIE);
        Mockito.when(mockPairs.get(1).getFoodPreference()).thenReturn(FoodPreference.VEGGIE);
        Mockito.when(mockPairs.get(2).getFoodPreference()).thenReturn(FoodPreference.VEGGIE);

        Assertions.assertTrue(GroupMetrics.hasValidPreferenceComposition(mockPairs));
    }

    @Test
    void hasValidPreferenceComposition_true_oneNoneTwoMeat() {
        Mockito.when(mockPairs.get(0).getFoodPreference()).thenReturn(FoodPreference.NONE);
        Mockito.when(mockPairs.get(1).getFoodPreference()).thenReturn(FoodPreference.MEAT);
        Mockito.when(mockPairs.get(2).getFoodPreference()).thenReturn(FoodPreference.MEAT);

        Assertions.assertTrue(GroupMetrics.hasValidPreferenceComposition(mockPairs));
    }

    @Test
    void hasValidPreferenceComposition_true_oneNoneTwoVeggie() {
        Mockito.when(mockPairs.get(0).getFoodPreference()).thenReturn(FoodPreference.NONE);
        Mockito.when(mockPairs.get(1).getFoodPreference()).thenReturn(FoodPreference.VEGGIE);
        Mockito.when(mockPairs.get(2).getFoodPreference()).thenReturn(FoodPreference.VEGGIE);

        Assertions.assertTrue(GroupMetrics.hasValidPreferenceComposition(mockPairs));
    }

    @Test
    void hasValidPreferenceComposition_true_oneMeatOneVeggieOneVegan() {
        Mockito.when(mockPairs.get(0).getFoodPreference()).thenReturn(FoodPreference.MEAT);
        Mockito.when(mockPairs.get(1).getFoodPreference()).thenReturn(FoodPreference.VEGGIE);
        Mockito.when(mockPairs.get(2).getFoodPreference()).thenReturn(FoodPreference.VEGAN);

        Assertions.assertTrue(GroupMetrics.hasValidPreferenceComposition(mockPairs));
    }

    @Test
    void hasValidPreferenceComposition_false_TwoMeatOneVegan() {
        Mockito.when(mockPairs.get(0).getFoodPreference()).thenReturn(FoodPreference.MEAT);
        Mockito.when(mockPairs.get(1).getFoodPreference()).thenReturn(FoodPreference.MEAT);
        Mockito.when(mockPairs.get(2).getFoodPreference()).thenReturn(FoodPreference.VEGAN);

        Assertions.assertFalse(GroupMetrics.hasValidPreferenceComposition(mockPairs));
    }

    @Test
    void hasValidPreferenceComposition_false_OneVeggieTwoNone() {
        Mockito.when(mockPairs.get(0).getFoodPreference()).thenReturn(FoodPreference.VEGGIE);
        Mockito.when(mockPairs.get(1).getFoodPreference()).thenReturn(FoodPreference.NONE);
        Mockito.when(mockPairs.get(2).getFoodPreference()).thenReturn(FoodPreference.NONE);

        Assertions.assertFalse(GroupMetrics.hasValidPreferenceComposition(mockPairs));
    }

    @Test
    void hasValidPreferenceComposition_false_OneVeggieOneMeatOneNone() {
        Mockito.when(mockPairs.get(0).getFoodPreference()).thenReturn(FoodPreference.VEGGIE);
        Mockito.when(mockPairs.get(1).getFoodPreference()).thenReturn(FoodPreference.MEAT);
        Mockito.when(mockPairs.get(2).getFoodPreference()).thenReturn(FoodPreference.NONE);

        Assertions.assertFalse(GroupMetrics.hasValidPreferenceComposition(mockPairs));
    }
}