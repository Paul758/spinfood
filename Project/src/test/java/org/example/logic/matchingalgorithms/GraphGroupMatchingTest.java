package org.example.logic.matchingalgorithms;

import org.example.data.DataManagement;
import org.example.data.enums.FoodPreference;
import org.example.data.enums.KitchenType;
import org.example.data.enums.Sex;
import org.example.data.factory.Kitchen;
import org.example.data.factory.Person;
import org.example.data.structures.Pair;
import org.example.data.structures.Solo;
import org.example.logic.enums.MealType;
import org.example.logic.structures.MatchingRepository;
import org.example.logic.structures.GroupMatched;
import org.example.logic.structures.PairMatched;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class to check if correct group matchings are produced
 */
class GraphGroupMatchingTest {

    static MatchingRepository matchingRepository;

    @BeforeEach
    void setup() {
        String filePathParticipants = "src/main/java/org/example/artifacts/teilnehmerliste.csv";
        String filePathLocation = "src/main/java/org/example/artifacts/partylocation.csv";
        DataManagement dataManagement = new DataManagement(filePathParticipants, filePathLocation);
        matchingRepository = new MatchingRepository(dataManagement);
        matchingRepository.matchPairs();
        matchingRepository.matchGroups();
    }


    @Test
    void match() {
        List<GroupMatched> groupMatchedList = new ArrayList<>(matchingRepository.getMatchedGroupsCollection());
        HashMap<Kitchen, List<MealType>> kitchenUsageHashmap = new HashMap<>();
        boolean isFeasible = true;
        System.out.println("Groupmatched list size " + groupMatchedList.size());
        for (GroupMatched group : groupMatchedList) {

            isFeasible = checkFoodPreference(group);
            assertTrue(isFeasible);
            isFeasible = checkGroupHasCook(group);
            assertTrue(isFeasible);
            isFeasible = ckeckEveryKitchenIsOnlyUsedOnce(group, kitchenUsageHashmap);
            assertTrue(isFeasible);
        }

        isFeasible = checkEveryPairIsCookingOnlyOnce();
        assertTrue(isFeasible);
    }

    private boolean ckeckEveryKitchenIsOnlyUsedOnce(GroupMatched group, HashMap<Kitchen, List<MealType>> kitchenUsageHashmap) {
        PairMatched cook = group.getCook();
        MealType mealType = group.getMealType();
        Kitchen pairKitchen = cook.getKitchen();

        if(kitchenUsageHashmap.containsKey(pairKitchen)) {
            List<MealType> mealsCookedInKitchen = kitchenUsageHashmap.get(pairKitchen);
            if(mealsCookedInKitchen.contains(mealType)) {
                return false;
            } else {
                mealsCookedInKitchen.add(mealType);
                kitchenUsageHashmap.put(pairKitchen, mealsCookedInKitchen);
            }
        } else {
            kitchenUsageHashmap.put(pairKitchen, new ArrayList<>(List.of(mealType)));
        }

        return true;
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

        assertTrue(testGroup.containsPair(pairMatchedD));
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


        assertTrue(testMatchingRepository.pairSuccessors.contains(pairMatchedD));
        Assertions.assertFalse(testMatchingRepository.pairSuccessors.contains(pairMatchedB));
        Assertions.assertFalse(testMatchingRepository.pairSuccessors.contains(pairMatchedC));
        Assertions.assertFalse(testMatchingRepository.pairSuccessors.contains(pairMatchedA));

        testMatchingRepository.removePair(pairMatchedA);

        assertTrue(matchingRepository.getMatchedGroupsCollection().isEmpty() &&
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
        Solo soloRemove = new Solo(pairMatchedA.getSoloA().person, pairMatchedA.getSoloA().foodPreference, pairMatchedA.getSoloA().getKitchen());
        List<Solo> soloSuccessors  = (List<Solo>) matchingRepository.soloSuccessors;
        Solo replacementSolo = soloSuccessors.get(0);

        PairMatched pairMatchedB = testGroup.getPairList().get(1);
        PairMatched pairMatchedC = testGroup.getPairList().get(2);

        List<PairMatched> pairSuccessorList = (List<PairMatched>) testMatchingRepository.pairSuccessors;
        PairMatched pairMatchedD = pairSuccessorList.get(0);

        assertTrue(testMatchingRepository.soloSuccessors.contains(replacementSolo));
        assertTrue(testMatchingRepository.pairSuccessors.contains(pairMatchedD));
        Assertions.assertFalse(testMatchingRepository.pairSuccessors.contains(pairMatchedA));
        Assertions.assertFalse(testMatchingRepository.pairSuccessors.contains(pairMatchedB));
        Assertions.assertFalse(testMatchingRepository.pairSuccessors.contains(pairMatchedC));

        testMatchingRepository.removeSolo(soloRemove);

        List<GroupMatched> groupMatchedListAfter = (List<GroupMatched>) testMatchingRepository.getMatchedGroupsCollection();
        GroupMatched testGroupAfter = groupMatchedList.get(0);

        PairMatched pairMatchedAafter = testGroupAfter.getPairList().get(0);

        Assertions.assertEquals(pairMatchedAafter.getSoloA(), replacementSolo);
        assertTrue(matchingRepository.getMatchedGroupsCollection().size() > 0);
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
        Solo soloRemove = new Solo(pairMatchedA.getSoloA().getPerson(), pairMatchedA.getSoloA().getFoodPreference(), pairMatchedA.getKitchen());
        Solo soloRemaining = new Solo(pairMatchedA.getSoloB().getPerson(), pairMatchedA.getFoodPreference(), pairMatchedA.getKitchen());

        PairMatched pairMatchedB = testGroup.getPairList().get(1);
        PairMatched pairMatchedC = testGroup.getPairList().get(2);

        List<PairMatched> pairSuccessorList = (List<PairMatched>) testMatchingRepository.pairSuccessors;
        PairMatched pairMatchedD = pairSuccessorList.get(0);

        assertTrue(testMatchingRepository.pairSuccessors.contains(pairMatchedD));
        Assertions.assertFalse(testMatchingRepository.pairSuccessors.contains(pairMatchedA));
        Assertions.assertFalse(testMatchingRepository.pairSuccessors.contains(pairMatchedB));
        Assertions.assertFalse(testMatchingRepository.pairSuccessors.contains(pairMatchedC));
        Assertions.assertFalse(testMatchingRepository.soloSuccessors.contains(soloRemaining));

        testMatchingRepository.removeSolo(soloRemove);

        assertTrue(matchingRepository.getMatchedGroupsCollection().isEmpty());
        Assertions.assertFalse(testMatchingRepository.pairSuccessors.contains(pairMatchedA));
        assertTrue(testMatchingRepository.pairSuccessors.contains(pairMatchedB));
        assertTrue(testMatchingRepository.pairSuccessors.contains(pairMatchedC));
        assertTrue(testMatchingRepository.soloSuccessors.contains(soloRemaining));
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

    @Test
    public void testGroupMatchingWith8Veggie1Meat(){
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
        Pair testPairD = new Pair(
                new Person("7", "Person4",27, Sex.MALE),
                new Person("8", "Personx4", 28, Sex.FEMALE),
                FoodPreference.VEGAN,
                new Kitchen(KitchenType.YES,0,8.681372017093311,50.5820794170933)
        );
        PairMatched pairMatchedD = new PairMatched(testPairD);
        Pair testPairE = new Pair(
                new Person("9", "Person5",27, Sex.MALE),
                new Person("10", "Personx5", 28, Sex.FEMALE),
                FoodPreference.VEGAN,
                new Kitchen(KitchenType.YES,0,8.681372017093311,50.5820794170933)
        );
        PairMatched pairMatchedE = new PairMatched(testPairE);
        Pair testPairF = new Pair(
                new Person("11", "Person6",27, Sex.MALE),
                new Person("12", "Personx6", 28, Sex.FEMALE),
                FoodPreference.VEGAN,
                new Kitchen(KitchenType.YES,0,8.681372017093311,50.5820794170933)
        );
        PairMatched pairMatchedF = new PairMatched(testPairF);
        Pair testPairG = new Pair(
                new Person("13", "Person7",27, Sex.MALE),
                new Person("14", "Personx7", 28, Sex.FEMALE),
                FoodPreference.VEGAN,
                new Kitchen(KitchenType.YES,0,8.681372017093311,50.5820794170933)
        );
        PairMatched pairMatchedG = new PairMatched(testPairG);
        Pair testPairH = new Pair(
                new Person("15", "Person8",27, Sex.MALE),
                new Person("16", "Personx8", 28, Sex.FEMALE),
                FoodPreference.VEGAN,
                new Kitchen(KitchenType.YES,0,8.681372017093311,50.5820794170933)
        );
        PairMatched pairMatchedH = new PairMatched(testPairH);
        Pair testPairI = new Pair(
                new Person("17", "Person4",27, Sex.MALE),
                new Person("18", "Personx4", 28, Sex.FEMALE),
                FoodPreference.MEAT,
                new Kitchen(KitchenType.YES,0,8.681372017093311,50.5820794170933)
        );
        PairMatched pairMatchedI = new PairMatched(testPairI);
        matchingRepository.addMatchedPairsCollection(List.of(pairMatchedA, pairMatchedB, pairMatchedC, pairMatchedD));
        matchingRepository.addMatchedPairsCollection(List.of(pairMatchedE, pairMatchedF, pairMatchedG, pairMatchedH));
        matchingRepository.addMatchedPairsCollection(List.of(pairMatchedI));
        List<GroupMatched> groupMatchedList = (List<GroupMatched>) matchingRepository.getMatchedGroupsCollection();
        System.out.printf("GroupMatchedList size: %d\n", groupMatchedList.size());
        if (groupMatchedList.isEmpty()) {
            System.out.printf("No SuperGroup could be made");
        }
        assertTrue(groupMatchedList.size() == 0);

    }

    @Test
    public void testGroupMatchingOnEmptyList() {
        List<PairMatched> empty = new ArrayList<>();
        List<GroupMatched> groups = GraphGroupMatching.match(empty, new MatchCosts());
        Assertions.assertEquals(0, groups.size());
    }

    @Test
    public void testGroupMatchingOnListWithOnPair() {
        Pair pair = new Pair(
                new Person("1", "Person1",22, Sex.MALE),
                new Person("2", "Personx1", 23, Sex.FEMALE),
                FoodPreference.VEGAN,
                new Kitchen(KitchenType.YES,0,8.681372017093311,50.5820794170933)
        );
        PairMatched pairMatched = new PairMatched(pair);

        List<PairMatched> pairMatchedList = List.of(pairMatched);
        List<GroupMatched> groups = GraphGroupMatching.match(pairMatchedList, new MatchCosts());
        Assertions.assertEquals(0, groups.size());
    }

    @Test
    public void testGroupMatchingWithNinePairs(){
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
        Pair testPairD = new Pair(
                new Person("7", "Person4",27, Sex.MALE),
                new Person("8", "Personx4", 28, Sex.FEMALE),
                FoodPreference.VEGAN,
                new Kitchen(KitchenType.YES,0,8.681372017093311,50.5820794170933)
        );
        PairMatched pairMatchedD = new PairMatched(testPairD);
        Pair testPairE = new Pair(
                new Person("9", "Person5",27, Sex.MALE),
                new Person("10", "Personx5", 28, Sex.FEMALE),
                FoodPreference.VEGAN,
                new Kitchen(KitchenType.YES,0,8.681372017093311,50.5820794170933)
        );
        PairMatched pairMatchedE = new PairMatched(testPairE);
        Pair testPairF = new Pair(
                new Person("11", "Person6",27, Sex.MALE),
                new Person("12", "Personx6", 28, Sex.FEMALE),
                FoodPreference.VEGAN,
                new Kitchen(KitchenType.YES,0,8.681372017093311,50.5820794170933)
        );
        PairMatched pairMatchedF = new PairMatched(testPairF);
        Pair testPairG = new Pair(
                new Person("13", "Person7",27, Sex.MALE),
                new Person("14", "Personx7", 28, Sex.FEMALE),
                FoodPreference.VEGAN,
                new Kitchen(KitchenType.YES,0,8.681372017093311,50.5820794170933)
        );
        PairMatched pairMatchedG = new PairMatched(testPairG);
        Pair testPairH = new Pair(
                new Person("15", "Person8",27, Sex.MALE),
                new Person("16", "Personx8", 28, Sex.FEMALE),
                FoodPreference.VEGAN,
                new Kitchen(KitchenType.YES,0,8.681372017093311,50.5820794170933)
        );
        PairMatched pairMatchedH = new PairMatched(testPairH);
        Pair testPairI = new Pair(
                new Person("17", "Person4",27, Sex.MALE),
                new Person("18", "Personx4", 28, Sex.FEMALE),
                FoodPreference.VEGAN,
                new Kitchen(KitchenType.YES,0,8.681372017093311,50.5820794170933)
        );
        PairMatched pairMatchedI = new PairMatched(testPairI);
        matchingRepository.addMatchedPairsCollection(List.of(pairMatchedA, pairMatchedB, pairMatchedC, pairMatchedD));
        matchingRepository.addMatchedPairsCollection(List.of(pairMatchedE, pairMatchedF, pairMatchedG, pairMatchedH));
        matchingRepository.addMatchedPairsCollection(List.of(pairMatchedI));
        matchingRepository.matchGroups();
        List<GroupMatched> groupMatchedList = (List<GroupMatched>) matchingRepository.getMatchedGroupsCollection();
        assertEquals(0, groupMatchedList.size());
    }
}