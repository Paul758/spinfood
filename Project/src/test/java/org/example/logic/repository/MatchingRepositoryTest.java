package org.example.logic.repository;

import org.example.data.Coordinate;
import org.example.data.DataManagement;
import org.example.data.enums.FoodPreference;
import org.example.data.enums.KitchenType;
import org.example.data.enums.Sex;
import org.example.data.factory.Kitchen;
import org.example.data.factory.Person;
import org.example.data.structures.Pair;
import org.example.logic.structures.MatchingRepository;
import org.example.logic.structures.PairMatched;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

class MatchingRepositoryTest {

    private static MatchingRepository matchingRepository;

    @BeforeAll
    static void setup() {
        String filePathParticipants = "src/main/java/org/example/artifacts/teilnehmerListe.csv";
        String filePathLocation = "src/main/java/org/example/artifacts/partylocation.csv";
        DataManagement dataManagement = new DataManagement(filePathParticipants, filePathLocation);
        matchingRepository = new MatchingRepository(dataManagement);

    }

    @Test
    void createAndAddPrematchedPairs() {
        Pair expectedPair = new Pair(
                new Person("01be5c1f-4aa1-458d-a530-b1c109ffbb55", "Person3",22, Sex.MALE),
                new Person("117ee996-14d3-44e8-8bcb-eb2d29fddda5", "Personx1", 25, Sex.MALE),
                FoodPreference.VEGAN,
                new Kitchen(KitchenType.YES,0,8.681372017093311,50.5820794170933)
        );

        PairMatched expectedPairMatched = new PairMatched(expectedPair);

        matchingRepository.getMatchedPairsCollection().clear();
        matchingRepository.createAndAddPrematchedPairs();

        Assertions.assertTrue(matchingRepository.getMatchedPairsCollection().contains(expectedPairMatched));
    }

    @Test
    void setDistanceToPartyLocationForPairs() {
        Coordinate partyLocation = matchingRepository.dataManagement.partyLocation;
        Pair expectedPair = new Pair(
                new Person("01be5c1f-4aa1-458d-a530-b1c109ffbb55", "Person3",22, Sex.MALE),
                new Person("117ee996-14d3-44e8-8bcb-eb2d29fddda5", "Personx1", 25, Sex.MALE),
                FoodPreference.VEGAN,
                new Kitchen(KitchenType.YES,0,8.681372017093311,50.5820794170933)
        );

        double expectedDistance = Coordinate.getDistance(partyLocation, expectedPair.kitchen.coordinate);



        matchingRepository.setDistanceToPartyLocationForPairs();
        List<PairMatched> pairMatchedList = (List<PairMatched>) matchingRepository.getMatchedPairsCollection();
        PairMatched testPair = pairMatchedList.get(0);

        Assertions.assertEquals(expectedDistance, testPair.getDistanceToPartyLocation());
    }



}