package org.example.logic.matchingalgorithms;

import org.example.data.DataManagement;
import org.example.data.enums.FoodPreference;
import org.example.data.enums.KitchenType;
import org.example.data.enums.Sex;
import org.example.data.factory.Kitchen;
import org.example.data.factory.Person;
import org.example.data.structures.Solo;
import org.example.logic.structures.MatchingRepository;
import org.example.logic.structures.PairMatched;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Test class to check if correct pair matchings are produced
 */
class GraphPairMatchingTest {
    private static MatchingRepository matchingRepository;


    @BeforeAll
    static void setup() {
        String filePathParticipants = "src/main/java/org/example/artifacts/teilnehmerListe.csv";
        String filePathLocation = "src/main/java/org/example/artifacts/partylocation.csv";
        DataManagement dataManagement = new DataManagement(filePathParticipants, filePathLocation);
        matchingRepository = new MatchingRepository(dataManagement);

    }

    @Test
    void match() {
        List<PairMatched> matchedPairs = (List<PairMatched>) matchingRepository.getMatchedPairsCollection();

        boolean pairsAreFeasible = true;

        for (PairMatched pair : matchedPairs) {
            if (!IsFeasible(pair)) {
                pairsAreFeasible = false;
            }
        }

        Assertions.assertTrue(pairsAreFeasible);
    }

    private boolean IsFeasible(PairMatched pair) {
        boolean foodPreferenceIsFeasible;
        boolean kitchenIsFeasible;
        foodPreferenceIsFeasible = checkFoodPreference(pair);

        kitchenIsFeasible = checkKitchen(pair);

        return foodPreferenceIsFeasible && kitchenIsFeasible;
    }

    private boolean checkKitchen(PairMatched pair) {
        return pair.getKitchen() != null;
    }

    private boolean checkFoodPreference(PairMatched pair) {
        FoodPreference personAFoodPreference = pair.getSoloA().getFoodPreference();
        FoodPreference personBFoodPreference = pair.getSoloB().getFoodPreference();

        boolean personAIsMeat = personAFoodPreference.equals(FoodPreference.MEAT);
        boolean personBIsVeggie = personBFoodPreference.equals(FoodPreference.VEGGIE);
        boolean personBisVegan = personBFoodPreference.equals(FoodPreference.VEGAN);

        return !(personAIsMeat && (personBIsVeggie || personBisVegan));
    }

    @Test
    void checkMatchingWithOnlyMeat() {
        List<PairMatched> matchedPairs = (List<PairMatched>) matchingRepository.getMatchedPairsCollection();

        for (PairMatched pair : matchedPairs) {
            if (pair.getFoodPreference()==FoodPreference.MEAT){
                matchedPairs.remove(pair);
            }
        }
        boolean pairsAreFeasible = true;
        for (PairMatched pair : matchedPairs) {
            if (!IsFeasible(pair)) {
                pairsAreFeasible = false;
            }
        }
        Assertions.assertTrue(pairsAreFeasible);
    }

    @Test
    void checkMatchingWithOnlyOneSolo() {
        List<Solo> Solos = (List<Solo>) matchingRepository.getSoloDataCollection();
        while (Solos.size()>1){
            Solos.remove(0);
        }
        List<PairMatched> matchedPairs = GraphPairMatching.match(Solos);
        Assertions.assertTrue(matchedPairs.isEmpty());
    }

    @Test
    void checkMatchingWithEmptyList() {
        List<Solo> empty = new ArrayList<>();
        List<PairMatched> pairMatchedList = GraphPairMatching.match(empty);
        Assertions.assertTrue(pairMatchedList.isEmpty());
    }

    @Test
    void checkMatchingWithNoKitchenAvailable() {
        Solo soloA = new Solo(new Person("id", "name", 20, Sex.MALE),
                FoodPreference.MEAT,
                new Kitchen(KitchenType.NO, 0,0,0));

        Solo soloB = new Solo(new Person("id", "name", 20, Sex.MALE),
                FoodPreference.MEAT,
                new Kitchen(KitchenType.NO, 0,0,0));

        List<Solo> solos = List.of(soloA, soloB);
        List<PairMatched> pairMatchedList = GraphPairMatching.match(solos);
        Assertions.assertTrue(pairMatchedList.isEmpty());
    }

    @Test
    void checkMatchingWithUnsuitableFoodPreferences() {
        Solo soloA = new Solo(new Person("id", "name", 20, Sex.MALE),
                FoodPreference.MEAT,
                new Kitchen(KitchenType.NO, 0,0,0));

        Solo soloB = new Solo(new Person("id", "name", 20, Sex.MALE),
                FoodPreference.VEGAN,
                new Kitchen(KitchenType.NO, 0,0,0));

        List<Solo> solos = List.of(soloA, soloB);
        List<PairMatched> pairMatchedList = GraphPairMatching.match(solos);
        Assertions.assertTrue(pairMatchedList.isEmpty());
    }

    @Test
    void checkMatchingWhereSolosShouldMatch() {
        Solo soloA = new Solo(new Person("id", "name", 20, Sex.MALE),
                FoodPreference.MEAT,
                new Kitchen(KitchenType.MAYBE, 0,0,0));

        Solo soloB = new Solo(new Person("id", "name", 20, Sex.MALE),
                FoodPreference.NONE,
                new Kitchen(KitchenType.NO, 0,0,0));

        List<Solo> solos = List.of(soloA, soloB);
        List<PairMatched> pairMatchedList = GraphPairMatching.match(solos);
        Assertions.assertEquals(1, pairMatchedList.size());
    }
}



