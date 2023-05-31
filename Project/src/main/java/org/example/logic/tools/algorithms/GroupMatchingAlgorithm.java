package org.example.logic.tools.algorithms;

import org.example.data.Coordinate;
import org.example.logic.enums.MealType;
import org.example.logic.structures.GroupMatched;
import org.example.logic.structures.PairMatched;

import java.util.*;

public class GroupMatchingAlgorithm {

    Coordinate partyLocation;


    public GroupMatchingAlgorithm(Coordinate partyLocation){
        this.partyLocation = partyLocation;
    }

    public static List<PairMatched> sortPairListByDistance(List<PairMatched> pairs){
        pairs.sort((pairA, pairB) -> Double.compare(pairB.getDistanceToPartyLocation(), pairA.getDistanceToPartyLocation()));
        return pairs;
    }


   public static List<List<PairMatched>> sliceDistanceList(List<PairMatched> sortedPairMatchedList){
       List<List<PairMatched>> cookingList = new ArrayList<>();
       int listLength = sortedPairMatchedList.size();
       int third = listLength / 3;

       cookingList.add(sortedPairMatchedList.subList(0, third));
       cookingList.add(sortedPairMatchedList.subList(third + 1, 2 * third));
       cookingList.add(sortedPairMatchedList.subList(2 * third + 1, sortedPairMatchedList.size() - 1));
       return cookingList;
   }


   public static Collection<GroupMatched> match(List<PairMatched> matchedPairsList){

       List<PairMatched> sortedPairsList = sortPairListByDistance(matchedPairsList);

       List<GroupMatched> startersGroups = new ArrayList<>();
       List<GroupMatched> mainCourseGroups = new ArrayList<>();
       List<GroupMatched> dessertGroups = new ArrayList<>();

       List<List<PairMatched>> cookingList = sliceDistanceList(sortedPairsList);
       ArrayList<PairMatched> starterPairs = new ArrayList<>(cookingList.get(0));
       ArrayList<PairMatched> mainPairs = new ArrayList<>(cookingList.get(1));
       ArrayList<PairMatched> dessertPairs = new ArrayList<>(cookingList.get(2));

       while(true){
           List<PairMatched> groupOfNine = new ArrayList<>();

           if(starterPairs.size() == 0){
               break;
           }

           PairMatched starterPair = starterPairs.get(0);
           groupOfNine.add(starterPair);
           starterPairs.remove(0);

           //Get 2 more starter pairs
           ArrayList<PairMatched> addPairsListStarter = addPairs(starterPairs.listIterator(), starterPair, 2);
           starterPairs.removeAll(addPairsListStarter);
           groupOfNine.addAll(addPairsListStarter);

            //Get 3 main pairs
           ArrayList<PairMatched> addPairsListMain = addPairs(mainPairs.listIterator(), starterPair, 3);
           mainPairs.removeAll(addPairsListMain);
           groupOfNine.addAll(addPairsListMain);

           //Get 3 dessert pairs
           ArrayList<PairMatched> addPairsListDessert = addPairs(dessertPairs.listIterator(), starterPair, 3);
           dessertPairs.removeAll(addPairsListDessert);
           groupOfNine.addAll(addPairsListDessert);


           if(groupOfNine.size() < 9){
               break;
           }

           //starters pairs
           PairMatched pairA = groupOfNine.get(0);
           PairMatched pairB = groupOfNine.get(1);
           PairMatched pairC = groupOfNine.get(2);
           //main course pairs
           PairMatched pairD = groupOfNine.get(3);
           PairMatched pairE = groupOfNine.get(4);
           PairMatched pairF = groupOfNine.get(5);
           //dessert pairs
           PairMatched pairG = groupOfNine.get(6);
           PairMatched pairH = groupOfNine.get(7);
           PairMatched pairI = groupOfNine.get(8);

           //convert to single groups


         /*  //starters groups
           GroupMatched starterGroupA = new GroupMatched(pairA, pairD, pairG, pairA, MealType.STARTER);
           GroupMatched starterGroupB = new GroupMatched(pairB, pairE, pairH, pairB, MealType.STARTER);
           GroupMatched starterGroupC = new GroupMatched(pairC, pairF, pairI, pairC, MealType.STARTER);
           startersGroups.addAll(List.of(starterGroupA, starterGroupB, starterGroupC));
           //main course groups
           GroupMatched mainCourseGroupA = new GroupMatched(pairA, pairF, pairH, pairF, MealType.MAIN);
           GroupMatched mainCourseGroupB = new GroupMatched(pairB, pairD, pairI, pairD, MealType.MAIN);
           GroupMatched mainCourseGroupC = new GroupMatched(pairC, pairE, pairG, pairE, MealType.MAIN);
           mainCourseGroups.addAll(List.of(mainCourseGroupA, mainCourseGroupB, mainCourseGroupC));
           //dessert groups
           GroupMatched dessertGroupA = new GroupMatched(pairA, pairE, pairI, pairI, MealType.DESSERT);
           GroupMatched dessertGroupB = new GroupMatched(pairB, pairF, pairG, pairG, MealType.DESSERT);
           GroupMatched dessertGroupC = new GroupMatched(pairC, pairD, pairH, pairH, MealType.DESSERT);
           dessertGroups.addAll(List.of(dessertGroupA, dessertGroupB, dessertGroupC));*/

       }

       Collection<GroupMatched> groups = new ArrayList<>();
       groups.addAll(startersGroups);
       groups.addAll(mainCourseGroups);
       groups.addAll(dessertGroups);

       return groups;
   }

    public static ArrayList<PairMatched> addPairs(Iterator<PairMatched> foodListIterator, PairMatched starterPair, int counterLimit){

       int counter = 0;
       ArrayList<PairMatched> addList = new ArrayList<>();

       double distanceToPartyLocation = starterPair.getDistanceToPartyLocation();

       while(foodListIterator.hasNext()){
           PairMatched possiblePair = foodListIterator.next();
           double distanceToPair = Coordinate.getDistance(starterPair.getKitchen().coordinate, possiblePair.getKitchen().coordinate);

           if(distanceToPair <= distanceToPartyLocation){
               counter++;
               addList.add(possiblePair);
               if(counter >= counterLimit){
                   break;
               }
           }
       }
       return addList;
   }

}
