package org.example.logic.metrics;

import org.example.data.Coordinate;
import org.example.data.enums.FoodPreference;
import org.example.data.enums.KitchenType;
import org.example.data.enums.Sex;
import org.example.data.factory.Kitchen;
import org.example.data.factory.Person;
import org.example.data.structures.Solo;
import org.example.logic.structures.PairMatched;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class PairMetricsTest {

    static PairMatched mockPair;

    @BeforeAll
    static void setup() {
        mockPair = Mockito.mock(PairMatched.class);
        Solo mockSoloA = Mockito.mock(Solo.class);
        Solo mockSoloB = Mockito.mock(Solo.class);
        Person mockPersonA = Mockito.mock(Person.class);
        Person mockPersonB = Mockito.mock(Person.class);
        Kitchen mockKitchenA = Mockito.mock(Kitchen.class);
        Kitchen mockKitchenB = Mockito.mock(Kitchen.class);

        Mockito.when(mockPair.getSoloA()).thenReturn(mockSoloA);
        Mockito.when(mockPair.getSoloB()).thenReturn(mockSoloB);
        Mockito.when(mockSoloA.getPerson()).thenReturn(mockPersonA);
        Mockito.when(mockSoloB.getPerson()).thenReturn(mockPersonB);
        Mockito.when(mockSoloA.getKitchen()).thenReturn(mockKitchenA);
        Mockito.when(mockSoloB.getKitchen()).thenReturn(mockKitchenB);
    }

    @Test
    void calcAgeDifference_sameAgeRange() {
        Mockito.when(mockPair.getSoloA().getPerson().age()).thenReturn(20);
        Mockito.when(mockPair.getSoloB().getPerson().age()).thenReturn(18);

        Assertions.assertEquals(0, PairMetrics.calcAgeDifference(mockPair));
    }

    @Test
    void calcAgeDifference_FirstInHigherAgeRange() {
        Mockito.when(mockPair.getSoloA().getPerson().age()).thenReturn(32);
        Mockito.when(mockPair.getSoloB().getPerson().age()).thenReturn(26);

        Assertions.assertEquals(2, PairMetrics.calcAgeDifference(mockPair));
    }

    @Test
    void calcAgeRange_SecondInHigherAgeRange() {
        Mockito.when(mockPair.getSoloA().getPerson().age()).thenReturn(15);
        Mockito.when(mockPair.getSoloB().getPerson().age()).thenReturn(70);

        Assertions.assertEquals(8, PairMetrics.calcAgeDifference(mockPair));
    }

    @Test
    void calcGenderDifference_OneFemaleOneMale() {
        Mockito.when(mockPair.getSoloA().getPerson().sex()).thenReturn(Sex.FEMALE);
        Mockito.when(mockPair.getSoloB().getPerson().sex()).thenReturn(Sex.MALE);

        Assertions.assertEquals(0, PairMetrics.calcGenderDiversity(mockPair));
    }

    @Test
    void calcGenderDifference_TwoFemale() {
        Mockito.when(mockPair.getSoloA().getPerson().sex()).thenReturn(Sex.FEMALE);
        Mockito.when(mockPair.getSoloB().getPerson().sex()).thenReturn(Sex.FEMALE);

        Assertions.assertEquals(0.5, PairMetrics.calcGenderDiversity(mockPair));
    }

    @Test
    void calcGenderDifference_TwoMale() {
        Mockito.when(mockPair.getSoloA().getPerson().sex()).thenReturn(Sex.MALE);
        Mockito.when(mockPair.getSoloB().getPerson().sex()).thenReturn(Sex.MALE);

        Assertions.assertEquals(0.5, PairMetrics.calcGenderDiversity(mockPair));
    }

    @Test
    void calcGenderDifference_OneOtherOneFemale() {
        Mockito.when(mockPair.getSoloA().getPerson().sex()).thenReturn(Sex.OTHER);
        Mockito.when(mockPair.getSoloB().getPerson().sex()).thenReturn(Sex.FEMALE);

        Assertions.assertEquals(0, PairMetrics.calcGenderDiversity(mockPair));
    }

    @Test
    void calcGenderDifference_OneMaleOneOther() {
        Mockito.when(mockPair.getSoloA().getPerson().sex()).thenReturn(Sex.MALE);
        Mockito.when(mockPair.getSoloB().getPerson().sex()).thenReturn(Sex.OTHER);

        Assertions.assertEquals(0.5, PairMetrics.calcGenderDiversity(mockPair));
    }

    @Test
    void calcPreferenceDeviation_SamePreference() {
        Mockito.when(mockPair.getSoloA().getFoodPreference()).thenReturn(FoodPreference.VEGGIE);
        Mockito.when(mockPair.getSoloB().getFoodPreference()).thenReturn(FoodPreference.VEGGIE);

        Assertions.assertEquals(0, PairMetrics.calcPreferenceDeviation(mockPair));
    }

    @Test
    void calcPreferenceDeviation_MeatAndNone() {
        Mockito.when(mockPair.getSoloA().getFoodPreference()).thenReturn(FoodPreference.MEAT);
        Mockito.when(mockPair.getSoloB().getFoodPreference()).thenReturn(FoodPreference.NONE);

        Assertions.assertEquals(0, PairMetrics.calcPreferenceDeviation(mockPair));
    }

    @Test
    void calcPreferenceDeviation_VeganAndVeggie() {
        Mockito.when(mockPair.getSoloA().getFoodPreference()).thenReturn(FoodPreference.VEGAN);
        Mockito.when(mockPair.getSoloB().getFoodPreference()).thenReturn(FoodPreference.VEGGIE);

        Assertions.assertEquals(1, PairMetrics.calcPreferenceDeviation(mockPair));
    }

    @Test
    void calcPreferenceDeviation_MeatAndVegan() {
        Mockito.when(mockPair.getSoloA().getFoodPreference()).thenReturn(FoodPreference.MEAT);
        Mockito.when(mockPair.getSoloB().getFoodPreference()).thenReturn(FoodPreference.VEGAN);

        Assertions.assertEquals(2, PairMetrics.calcPreferenceDeviation(mockPair));
    }

    @Test
    void calcPathLength_HighDistance() {
        Mockito.when(mockPair.getStarterLocation()).thenReturn(new Coordinate(-68,-54));
        Mockito.when(mockPair.getMainLocation()).thenReturn(new Coordinate(-25, 31));
        Mockito.when(mockPair.getDessertLocation()).thenReturn(new Coordinate(96, 7));
        Coordinate partyLocation = new Coordinate(43, -5);

        double expected = 272.9562632066512;
        Assertions.assertEquals(expected, PairMetrics.calcPathLength(mockPair, partyLocation));
    }

    @Test
    void calcPathLength_LocationsAreTheSame() {
        Mockito.when(mockPair.getStarterLocation()).thenReturn(new Coordinate(0,0));
        Mockito.when(mockPair.getMainLocation()).thenReturn(new Coordinate(0, 0));
        Mockito.when(mockPair.getDessertLocation()).thenReturn(new Coordinate(0, 0));
        Coordinate partyLocation = new Coordinate(0, 0);

        double expected = 0;
        Assertions.assertEquals(expected, PairMetrics.calcPathLength(mockPair, partyLocation));
    }

    @Test
    void calcPathLength_SmallDistances() {
        Mockito.when(mockPair.getStarterLocation()).thenReturn(new Coordinate(0.02,0.04));
        Mockito.when(mockPair.getMainLocation()).thenReturn(new Coordinate(0.03, 0.01));
        Mockito.when(mockPair.getDessertLocation()).thenReturn(new Coordinate(-0.01, 0.02));
        Coordinate partyLocation = new Coordinate(0.01, -0.03);

        double expected = 0.12670548092920544;
        Assertions.assertEquals(expected, PairMetrics.calcPathLength(mockPair, partyLocation));
    }

    @Test
    void calcPathLength_ExceptionThrownInvalidGroupLocation() {
        Mockito.when(mockPair.getStarterLocation()).thenReturn(new Coordinate(0,0));
        Mockito.when(mockPair.getMainLocation()).thenReturn(null);
        Mockito.when(mockPair.getDessertLocation()).thenReturn(new Coordinate(0, 0));
        Coordinate partyLocation = new Coordinate(0, 0);

        assertThrows(IllegalArgumentException.class, () -> PairMetrics.calcPathLength(mockPair, partyLocation));
    }

    @Test
    void calcPathLength_ExceptionThrownInvalidPartyLocation() {
        Mockito.when(mockPair.getStarterLocation()).thenReturn(new Coordinate(0,0));
        Mockito.when(mockPair.getMainLocation()).thenReturn(new Coordinate(0,0));
        Mockito.when(mockPair.getDessertLocation()).thenReturn(new Coordinate(0, 0));

        assertThrows(IllegalArgumentException.class, () -> PairMetrics.calcPathLength(mockPair, null));
    }

    @Test
    void isValid_true() {
        Mockito.when(mockPair.getSoloA().getFoodPreference()).thenReturn(FoodPreference.MEAT);
        Mockito.when(mockPair.getSoloB().getFoodPreference()).thenReturn(FoodPreference.NONE);
        Mockito.when(mockPair.getSoloA().getKitchen().getKitchenType()).thenReturn(KitchenType.YES);
        Mockito.when(mockPair.getSoloB().getKitchen().getKitchenType()).thenReturn(KitchenType.NO);

        Assertions.assertTrue(PairMetrics.isValid(mockPair));
    }

    @Test
    void isValid_false() {
        Mockito.when(mockPair.getSoloA().getFoodPreference()).thenReturn(FoodPreference.MEAT);
        Mockito.when(mockPair.getSoloB().getFoodPreference()).thenReturn(FoodPreference.NONE);
        Mockito.when(mockPair.getSoloA().getKitchen().getKitchenType()).thenReturn(KitchenType.NO);
        Mockito.when(mockPair.getSoloB().getKitchen().getKitchenType()).thenReturn(KitchenType.NO);

        Assertions.assertFalse(PairMetrics.isValid(mockPair));
    }

    @Test
    void isPreferenceCombinationValid_MeatAndVeganIsFalse() {
        Mockito.when(mockPair.getSoloA().getFoodPreference()).thenReturn(FoodPreference.MEAT);
        Mockito.when(mockPair.getSoloB().getFoodPreference()).thenReturn(FoodPreference.VEGAN);

        Assertions.assertFalse(PairMetrics.isPreferenceCombinationValid(mockPair));
    }

    @Test
    void isPreferenceCombinationValid_VeggieAndMeatIsFalse() {
        Mockito.when(mockPair.getSoloA().getFoodPreference()).thenReturn(FoodPreference.VEGGIE);
        Mockito.when(mockPair.getSoloB().getFoodPreference()).thenReturn(FoodPreference.MEAT);

        Assertions.assertFalse(PairMetrics.isPreferenceCombinationValid(mockPair));
    }

    @Test
    void isPreferenceCombinationValid_VeganAndNoneIsTrue() {
        Mockito.when(mockPair.getSoloA().getFoodPreference()).thenReturn(FoodPreference.VEGAN);
        Mockito.when(mockPair.getSoloB().getFoodPreference()).thenReturn(FoodPreference.NONE);

        Assertions.assertTrue(PairMetrics.isPreferenceCombinationValid(mockPair));
    }

    @Test
    void isPreferenceCombinationValid_VeganAndVeggieIsTrue() {
        Mockito.when(mockPair.getSoloA().getFoodPreference()).thenReturn(FoodPreference.VEGAN);
        Mockito.when(mockPair.getSoloB().getFoodPreference()).thenReturn(FoodPreference.VEGGIE);

        Assertions.assertTrue(PairMetrics.isPreferenceCombinationValid(mockPair));
    }

    @Test
    void isKitchenAvailable_YesAndNoIsTrue() {
        Mockito.when(mockPair.getSoloA().getKitchen().getKitchenType()).thenReturn(KitchenType.YES);
        Mockito.when(mockPair.getSoloB().getKitchen().getKitchenType()).thenReturn(KitchenType.NO);

        Assertions.assertTrue(PairMetrics.isKitchenAvailable(mockPair));
    }

    @Test
    void isKitchenAvailable_NoAndMaybeIsTrue() {
        Mockito.when(mockPair.getSoloA().getKitchen().getKitchenType()).thenReturn(KitchenType.NO);
        Mockito.when(mockPair.getSoloB().getKitchen().getKitchenType()).thenReturn(KitchenType.MAYBE);

        Assertions.assertTrue(PairMetrics.isKitchenAvailable(mockPair));
    }

    @Test
    void isKitchenAvailable_NoAndNoIsFalse() {
        Mockito.when(mockPair.getSoloA().getKitchen().getKitchenType()).thenReturn(KitchenType.NO);
        Mockito.when(mockPair.getSoloB().getKitchen().getKitchenType()).thenReturn(KitchenType.NO);

        Assertions.assertFalse(PairMetrics.isKitchenAvailable(mockPair));
    }
}