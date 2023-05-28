package org.example.logic.tools.algorithms;

import org.example.data.DataManagement;
import org.example.data.enums.FoodPreference;
import org.example.data.enums.KitchenType;
import org.example.data.enums.Sex;
import org.example.data.factory.Kitchen;
import org.example.data.factory.Person;
import org.example.data.structures.Pair;
import org.example.data.structures.Solo;
import org.example.logic.enums.MealType;
import org.example.logic.repository.MatchingRepository;
import org.example.logic.structures.GroupMatched;
import org.example.logic.structures.PairMatched;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class GroupMatchingGraphTest {

    static MatchingRepository matchingRepository;

    @BeforeAll
    static void setup() {
        String filePathParticipants = "src/main/java/org/example/artifacts/teilnehmerListe.csv";
        String filePathLocation = "src/main/java/org/example/artifacts/partylocation.csv";
        DataManagement dataManagement = new DataManagement(filePathParticipants, filePathLocation);
        matchingRepository = new MatchingRepository(dataManagement);

    }


    @Test
    void match() {
        List<GroupMatched> groupMatchedList = new ArrayList<>(matchingRepository.getMatchedGroupsCollection());

        boolean isFeasible = true;

        for (GroupMatched group : groupMatchedList) {

            isFeasible = checkFoodPreference(group);
            isFeasible = checkGroupHasCook(group);
        }

        isFeasible = checkEveryPairIsCookingOnlyOnce();

        Assertions.assertTrue(isFeasible);
    }

    private boolean checkEveryPairIsCookingOnlyOnce() {
        List<PairMatched> pairMatchedList = (List<PairMatched>) matchingRepository.getMatchedPairsCollection();
        pairMatchedList.removeAll(matchingRepository.pairSuccessors);
        Collection<GroupMatched> groupMatchedList = matchingRepository.getMatchedGroupsCollection();

        for (PairMatched pairMatched: pairMatchedList) {
            int pairAppearanceCounter = 0;

            for (GroupMatched group: groupMatchedList ) {
                if(!group.containsPair(pairMatched)){
                   continue;
                }
                pairAppearanceCounter++;
            }
            if(pairAppearanceCounter != 3){
                return false;
            }
        }
        return true;
    }

    private boolean checkGroupHasCook(GroupMatched group) {
        return group.getCook() != null;
    }

    private boolean checkFoodPreference(GroupMatched group) {
        int meatCounter = 0;
        int veggieCounter = 0;
        int veganCounter = 0;

        for (PairMatched pair : group.getPairList()) {
            switch (pair.getFoodPreference()) {
                case MEAT -> meatCounter++;
                case VEGGIE -> veggieCounter++;
                case VEGAN -> veganCounter++;
            }
        }

        return meatCounter <= 1 || (veggieCounter < 1 && veganCounter < 1);
    }

    @Test
    void testPairUnregistrationWithGroupFiller(){
        MatchingRepository testMatchingRepository = createTestRepository();
        List<GroupMatched> groupMatchedList = (List<GroupMatched>) testMatchingRepository.getMatchedGroupsCollection();
        GroupMatched testGroup = groupMatchedList.get(0);
        PairMatched pairMatchedA = testGroup.getCook();

        List<PairMatched> pairSuccessorList = (List<PairMatched>) testMatchingRepository.pairSuccessors;
        PairMatched pairMatchedD = pairSuccessorList.get(0);

        testMatchingRepository.removePair(pairMatchedA);

        Assertions.assertTrue(testGroup.containsPair(pairMatchedD));
    }

    private MatchingRepository createTestRepository() {
        matchingRepository.pairSuccessors.clear();
        matchingRepository.soloSuccessors.clear();
        matchingRepository.getMatchedPairsCollection().clear();
        matchingRepository.getMatchedGroupsCollection().clear();

        Pair testPairA = new Pair(
                new Person("1", "Person1",22, Sex.MALE),
                new Person("2", "Personx1", 23, Sex.FEMALE),
                FoodPreference.VEGAN,
                new Kitchen(KitchenType.YES,0,8.681372017093311,50.5820794170933)
        );
        PairMatched pairMatchedA = new PairMatched(testPairA);
        Pair testPairB = new Pair(
                new Person("3", "Person2",24, Sex.MALE),
                new Person("4", "Personx2", 25, Sex.FEMALE),
                FoodPreference.VEGAN,
                new Kitchen(KitchenType.YES,0,8.681372017093311,50.5820794170933)
        );
        PairMatched pairMatchedB = new PairMatched(testPairB);
        Pair testPairC = new Pair(
                new Person("5", "Person3",26, Sex.MALE),
                new Person("6", "Personx3", 27, Sex.FEMALE),
                FoodPreference.VEGAN,
                new Kitchen(KitchenType.YES,0,8.681372017093311,50.5820794170933)
        );
        PairMatched pairMatchedC = new PairMatched(testPairC);

        //test Successor
        Pair testPairD = new Pair(
                new Person("7", "Person4",27, Sex.MALE),
                new Person("8", "Personx4", 28, Sex.FEMALE),
                FoodPreference.VEGAN,
                new Kitchen(KitchenType.YES,0,8.681372017093311,50.5820794170933)
        );

        PairMatched pairMatchedD = new PairMatched(testPairD);



        GroupMatched testGroup = new GroupMatched(pairMatchedA, pairMatchedB, pairMatchedC, MealType.STARTER);

        matchingRepository.addMatchedPairsCollection(List.of(pairMatchedA, pairMatchedB, pairMatchedC, pairMatchedD));
        matchingRepository.addMatchedGroupsCollection(List.of(testGroup));
        matchingRepository.UpdatePairSuccessors();

        return matchingRepository;
    }

    @Test
    void testPairUnregistrationWithOutGroupFiller(){
        MatchingRepository testMatchingRepository = createTestRepositoryWithoutGroupFiller();
        List<GroupMatched> groupMatchedList = (List<GroupMatched>) testMatchingRepository.getMatchedGroupsCollection();
        GroupMatched testGroup = groupMatchedList.get(0);
        PairMatched pairMatchedA = testGroup.getCook();
        PairMatched pairMatchedB = testGroup.getPairList().get(1);
        PairMatched pairMatchedC = testGroup.getPairList().get(2);

        List<PairMatched> pairSuccessorList = (List<PairMatched>) testMatchingRepository.pairSuccessors;
        PairMatched pairMatchedD = pairSuccessorList.get(0);


        Assertions.assertTrue(testMatchingRepository.pairSuccessors.contains(pairMatchedD));
        Assertions.assertFalse(testMatchingRepository.pairSuccessors.contains(pairMatchedB));
        Assertions.assertFalse(testMatchingRepository.pairSuccessors.contains(pairMatchedC));
        Assertions.assertFalse(testMatchingRepository.pairSuccessors.contains(pairMatchedA));

        testMatchingRepository.removePair(pairMatchedA);

        Assertions.assertTrue(matchingRepository.getMatchedGroupsCollection().isEmpty() &&
                                testMatchingRepository.pairSuccessors.contains(pairMatchedB) &&
                                testMatchingRepository.pairSuccessors.contains(pairMatchedC));

        Assertions.assertFalse(testMatchingRepository.pairSuccessors.contains(pairMatchedA));
    }

    private MatchingRepository createTestRepositoryWithoutGroupFiller() {
        matchingRepository.pairSuccessors.clear();
        matchingRepository.soloSuccessors.clear();
        matchingRepository.getMatchedPairsCollection().clear();
        matchingRepository.getMatchedGroupsCollection().clear();

        Pair testPairA = new Pair(
                new Person("1", "Person1",22, Sex.MALE),
                new Person("2", "Personx1", 23, Sex.FEMALE),
                FoodPreference.VEGAN,
                new Kitchen(KitchenType.YES,0,8.681372017093311,50.5820794170933)
        );
        PairMatched pairMatchedA = new PairMatched(testPairA);
        Pair testPairB = new Pair(
                new Person("3", "Person2",24, Sex.MALE),
                new Person("4", "Personx2", 25, Sex.FEMALE),
                FoodPreference.VEGAN,
                new Kitchen(KitchenType.YES,0,8.681372017093311,50.5820794170933)
        );
        PairMatched pairMatchedB = new PairMatched(testPairB);
        Pair testPairC = new Pair(
                new Person("5", "Person3",26, Sex.MALE),
                new Person("6", "Personx3", 27, Sex.FEMALE),
                FoodPreference.VEGAN,
                new Kitchen(KitchenType.YES,0,8.681372017093311,50.5820794170933)
        );
        PairMatched pairMatchedC = new PairMatched(testPairC);

        //test Successor
        Pair testPairD = new Pair(
                new Person("7", "Person4",27, Sex.MALE),
                new Person("8", "Personx4", 28, Sex.FEMALE),
                FoodPreference.MEAT,
                new Kitchen(KitchenType.YES,0,8.681372017093311,50.5820794170933)
        );

        PairMatched pairMatchedD = new PairMatched(testPairD);

        GroupMatched testGroup = new GroupMatched(pairMatchedA, pairMatchedB, pairMatchedC, MealType.STARTER);

        matchingRepository.addMatchedPairsCollection(List.of(pairMatchedA, pairMatchedB, pairMatchedC, pairMatchedD));
        matchingRepository.addMatchedGroupsCollection(List.of(testGroup));
        matchingRepository.UpdatePairSuccessors();

        return matchingRepository;
    }

    @Test
    void testSoloUnregistrationWithFiller(){
        MatchingRepository testMatchingRepository = createTestRepositoryWithSoloFiller();

        List<GroupMatched> groupMatchedList = (List<GroupMatched>) testMatchingRepository.getMatchedGroupsCollection();
        GroupMatched testGroup = groupMatchedList.get(0);

        PairMatched pairMatchedA = testGroup.getCook();
        Solo soloRemove = new Solo(pairMatchedA.getPersonA(), pairMatchedA.getFoodPreference(), pairMatchedA.getKitchen());
        List<Solo> soloSuccessors  = (List<Solo>) matchingRepository.soloSuccessors;
        Solo replacementSolo = soloSuccessors.get(0);

        PairMatched pairMatchedB = testGroup.getPairList().get(1);
        PairMatched pairMatchedC = testGroup.getPairList().get(2);

        List<PairMatched> pairSuccessorList = (List<PairMatched>) testMatchingRepository.pairSuccessors;
        PairMatched pairMatchedD = pairSuccessorList.get(0);

        Assertions.assertTrue(testMatchingRepository.soloSuccessors.contains(replacementSolo));
        Assertions.assertTrue(testMatchingRepository.pairSuccessors.contains(pairMatchedD));
        Assertions.assertFalse(testMatchingRepository.pairSuccessors.contains(pairMatchedA));
        Assertions.assertFalse(testMatchingRepository.pairSuccessors.contains(pairMatchedB));
        Assertions.assertFalse(testMatchingRepository.pairSuccessors.contains(pairMatchedC));

        testMatchingRepository.removeSolo(soloRemove);

        List<GroupMatched> groupMatchedListAfter = (List<GroupMatched>) testMatchingRepository.getMatchedGroupsCollection();
        GroupMatched testGroupAfter = groupMatchedList.get(0);

        PairMatched pairMatchedAafter = testGroupAfter.getPairList().get(0);

        Assertions.assertEquals(pairMatchedAafter.getPersonA(), replacementSolo.person);
        Assertions.assertTrue(matchingRepository.getMatchedGroupsCollection().size() > 0);
        Assertions.assertFalse(testMatchingRepository.pairSuccessors.contains(pairMatchedA));
    }

    private MatchingRepository createTestRepositoryWithSoloFiller() {
        matchingRepository.pairSuccessors.clear();
        matchingRepository.soloSuccessors.clear();
        matchingRepository.getSoloDataCollection().clear();
        matchingRepository.getMatchedPairsCollection().clear();
        matchingRepository.getMatchedGroupsCollection().clear();

        Pair testPairA = new Pair(
                new Person("1", "Person1",22, Sex.MALE),
                new Person("2", "Personx1", 23, Sex.FEMALE),
                FoodPreference.VEGAN,
                new Kitchen(KitchenType.YES,0,8.681372017093311,50.5820794170933)
        );
        PairMatched pairMatchedA = new PairMatched(testPairA);
        Pair testPairB = new Pair(
                new Person("3", "Person2",24, Sex.MALE),
                new Person("4", "Personx2", 25, Sex.FEMALE),
                FoodPreference.VEGAN,
                new Kitchen(KitchenType.YES,0,8.681372017093311,50.5820794170933)
        );
        PairMatched pairMatchedB = new PairMatched(testPairB);
        Pair testPairC = new Pair(
                new Person("5", "Person3",26, Sex.MALE),
                new Person("6", "Personx3", 27, Sex.FEMALE),
                FoodPreference.VEGAN,
                new Kitchen(KitchenType.YES,0,8.681372017093311,50.5820794170933)
        );
        PairMatched pairMatchedC = new PairMatched(testPairC);

        //test Successor
        Pair testPairD = new Pair(
                new Person("7", "Person4",27, Sex.MALE),
                new Person("8", "Personx4", 28, Sex.FEMALE),
                FoodPreference.MEAT,
                new Kitchen(KitchenType.YES,0,8.681372017093311,50.5820794170933)
        );

        PairMatched pairMatchedD = new PairMatched(testPairD);

        Solo solo = new Solo(new Person("9", "Personx4", 28, Sex.FEMALE), FoodPreference.VEGAN, new Kitchen(KitchenType.YES, 1, 10d, 10d));
        GroupMatched testGroup = new GroupMatched(pairMatchedA, pairMatchedB, pairMatchedC, MealType.STARTER);

        matchingRepository.soloSuccessors.add(solo);
        matchingRepository.addMatchedPairsCollection(List.of(pairMatchedA, pairMatchedB, pairMatchedC, pairMatchedD));
        matchingRepository.addMatchedGroupsCollection(List.of(testGroup));
        matchingRepository.UpdatePairSuccessors();
        return matchingRepository;
    }

    @Test
    void testSoloUnregistrationWithOutFiller(){
        MatchingRepository testMatchingRepository = createTestRepositoryWithOutSoloFiller();

        List<GroupMatched> groupMatchedList = (List<GroupMatched>) testMatchingRepository.getMatchedGroupsCollection();
        GroupMatched testGroup = groupMatchedList.get(0);

        PairMatched pairMatchedA = testGroup.getCook();
        Solo soloRemove = new Solo(pairMatchedA.getPersonA(), pairMatchedA.getFoodPreference(), pairMatchedA.getKitchen());
        Solo soloRemaining = new Solo(pairMatchedA.getPersonB(), pairMatchedA.getFoodPreference(), pairMatchedA.getKitchen());

        PairMatched pairMatchedB = testGroup.getPairList().get(1);
        PairMatched pairMatchedC = testGroup.getPairList().get(2);

        List<PairMatched> pairSuccessorList = (List<PairMatched>) testMatchingRepository.pairSuccessors;
        PairMatched pairMatchedD = pairSuccessorList.get(0);

        Assertions.assertTrue(testMatchingRepository.pairSuccessors.contains(pairMatchedD));
        Assertions.assertFalse(testMatchingRepository.pairSuccessors.contains(pairMatchedA));
        Assertions.assertFalse(testMatchingRepository.pairSuccessors.contains(pairMatchedB));
        Assertions.assertFalse(testMatchingRepository.pairSuccessors.contains(pairMatchedC));
        Assertions.assertFalse(testMatchingRepository.soloSuccessors.contains(soloRemaining));

        testMatchingRepository.removeSolo(soloRemove);
        
        Assertions.assertTrue(matchingRepository.getMatchedGroupsCollection().isEmpty());
        Assertions.assertFalse(testMatchingRepository.pairSuccessors.contains(pairMatchedA));
        Assertions.assertTrue(testMatchingRepository.pairSuccessors.contains(pairMatchedB));
        Assertions.assertTrue(testMatchingRepository.pairSuccessors.contains(pairMatchedC));
        Assertions.assertTrue(testMatchingRepository.soloSuccessors.contains(soloRemaining));
    }

    private MatchingRepository createTestRepositoryWithOutSoloFiller() {
        matchingRepository.pairSuccessors.clear();
        matchingRepository.soloSuccessors.clear();
        matchingRepository.getSoloDataCollection().clear();
        matchingRepository.getMatchedPairsCollection().clear();
        matchingRepository.getMatchedGroupsCollection().clear();

        Pair testPairA = new Pair(
                new Person("1", "Person1",22, Sex.MALE),
                new Person("2", "Personx1", 23, Sex.FEMALE),
                FoodPreference.VEGAN,
                new Kitchen(KitchenType.YES,0,8.681372017093311,50.5820794170933)
        );
        PairMatched pairMatchedA = new PairMatched(testPairA);
        Pair testPairB = new Pair(
                new Person("3", "Person2",24, Sex.MALE),
                new Person("4", "Personx2", 25, Sex.FEMALE),
                FoodPreference.VEGAN,
                new Kitchen(KitchenType.YES,0,8.681372017093311,50.5820794170933)
        );
        PairMatched pairMatchedB = new PairMatched(testPairB);
        Pair testPairC = new Pair(
                new Person("5", "Person3",26, Sex.MALE),
                new Person("6", "Personx3", 27, Sex.FEMALE),
                FoodPreference.VEGAN,
                new Kitchen(KitchenType.YES,0,8.681372017093311,50.5820794170933)
        );
        PairMatched pairMatchedC = new PairMatched(testPairC);

        //test Successor
        Pair testPairD = new Pair(
                new Person("7", "Person4",27, Sex.MALE),
                new Person("8", "Personx4", 28, Sex.FEMALE),
                FoodPreference.MEAT,
                new Kitchen(KitchenType.YES,0,8.681372017093311,50.5820794170933)
        );

        PairMatched pairMatchedD = new PairMatched(testPairD);

        Solo solo = new Solo(new Person("9", "Personx4", 28, Sex.FEMALE), FoodPreference.MEAT, new Kitchen(KitchenType.YES, 1, 10d, 10d));
        GroupMatched testGroup = new GroupMatched(pairMatchedA, pairMatchedB, pairMatchedC, MealType.STARTER);

        matchingRepository.soloSuccessors.add(solo);
        matchingRepository.addMatchedPairsCollection(List.of(pairMatchedA, pairMatchedB, pairMatchedC, pairMatchedD));
        matchingRepository.addMatchedGroupsCollection(List.of(testGroup));
        matchingRepository.UpdatePairSuccessors();
        return matchingRepository;
    }

}