package org.example.logic.tools;

import org.example.data.Coordinate;
import org.example.data.enums.FoodPreference;

import java.util.*;

public class GroupMatchingAlgorithm {

    static Coordinate partyLocation;
    List<PairMatched> startersPairs;
    List<PairMatched> mainCoursePairs;
    List<PairMatched> dessertPairs;

    public static List<PairMatched> successors = new ArrayList<>();

    public static void setPartyLocation(Coordinate partyLocation){
        GroupMatchingAlgorithm.partyLocation = partyLocation;
    }

    public static List<PairMatched> sortPairListByDistance(List<PairMatched> pairs){
        System.out.println("matched pair list: " + pairs);
        pairs.sort((pairA, pairB) -> {
            double distanceA = Coordinate.getDistance(partyLocation, pairA.getKitchen().coordinate);
            double distanceB = Coordinate.getDistance(partyLocation, pairB.getKitchen().coordinate);
            return Double.compare(distanceB, distanceA);
        });

        pairs.forEach(System.out::println);
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


   public static List<List<GroupMatched>> match(List<PairMatched> matchedPairsList){
       System.out.println("matched pair list: " + matchedPairsList);

       List<PairMatched> sortedPairsList = sortPairListByDistance(matchedPairsList);
       List<List<PairMatched>> cookingList = sliceDistanceList(sortedPairsList);
       List<GroupMatched> startersGroups = new ArrayList<>();
       List<GroupMatched> mainCourseGroups = new ArrayList<>();
       List<GroupMatched> dessertGroups = new ArrayList<>();

       ArrayList<PairMatched> starterPairs = new ArrayList<>(cookingList.get(0));
       ArrayList<PairMatched> mainPairs = new ArrayList<>(cookingList.get(1));
       ArrayList<PairMatched> dessertPairs = new ArrayList<>(cookingList.get(2));

       while(true){
           List<PairMatched> groupOfNine = new ArrayList<>();

           if(starterPairs.size() == 0){
               System.out.println("There is no starter pair left");
               break;
           }

           PairMatched starterPair = starterPairs.get(0);
           groupOfNine.add(starterPair);
           starterPairs.remove(0);

           //Get 2 more starter pairs
           ArrayList<PairMatched> addPairsListStarter = addPairs(starterPairs.listIterator(), starterPair, 2);
           starterPairs.removeAll(addPairsListStarter);
           System.out.println("The size  of starters now is " + starterPairs.size());
           groupOfNine.addAll(addPairsListStarter);


           ArrayList<PairMatched> addPairsListMain = addPairs(mainPairs.listIterator(), starterPair, 3);
           mainPairs.removeAll(addPairsListMain);
           System.out.println("The size of main now is " + mainPairs.size());
           groupOfNine.addAll(addPairsListMain);


           ArrayList<PairMatched> addPairsListDessert = addPairs(dessertPairs.listIterator(), starterPair, 3);
           dessertPairs.removeAll(addPairsListDessert);
           System.out.println("The size of dessert now is " + dessertPairs.size());
           groupOfNine.addAll(addPairsListDessert);


           if(groupOfNine.size() < 9){
               System.out.println("There are not enough people to form a group of nine");
               System.out.println("The size of groupOfNine is " + groupOfNine.size());
               successors.addAll(groupOfNine);
               successors.forEach(System.out::println);
               System.out.println("break");
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
           //starters groups
           GroupMatched starterGroupA = new GroupMatched(pairA, pairD, pairG, pairA);
           GroupMatched starterGroupB = new GroupMatched(pairB, pairE, pairH, pairB);
           GroupMatched starterGroupC = new GroupMatched(pairC, pairF, pairI, pairC);
           startersGroups.addAll(List.of(starterGroupA, starterGroupB, starterGroupC));
           //main course groups
           GroupMatched mainCourseGroupA = new GroupMatched(pairA, pairF, pairH, pairF);
           GroupMatched mainCourseGroupB = new GroupMatched(pairB, pairD, pairI, pairD);
           GroupMatched mainCourseGroupC = new GroupMatched(pairC, pairE, pairG, pairE);
           mainCourseGroups.addAll(List.of(mainCourseGroupA, mainCourseGroupB, mainCourseGroupC));
           //dessert groups
           GroupMatched dessertGroupA = new GroupMatched(pairA, pairE, pairI, pairI);
           GroupMatched dessertGroupB = new GroupMatched(pairB, pairF, pairG, pairG);
           GroupMatched dessertGroupC = new GroupMatched(pairC, pairD, pairH, pairH);
           dessertGroups.addAll(List.of(dessertGroupA, dessertGroupB, dessertGroupC));

       }

       ArrayList<List<GroupMatched>> groups = new ArrayList<>();
       groups.add(startersGroups);
       groups.add(mainCourseGroups);
       groups.add(dessertGroups);

       successors = getSuccessors(starterPairs, mainPairs, dessertPairs);

       return groups;
   }

    private static List<PairMatched> getSuccessors(List<PairMatched> startersPairs, List<PairMatched> mainCoursePairs, List<PairMatched> dessertPairs) {
        List<PairMatched> successors = new ArrayList<>();

        successors.addAll(startersPairs);
        successors.addAll(mainCoursePairs);
        successors.addAll(dessertPairs);

        return successors;
    }

    public static ArrayList<PairMatched> addPairs(Iterator<PairMatched> foodListIterator, PairMatched starterPair, int counterLimit){

       int counter = 0;
       int meatNoneCounter = 0;
       ArrayList<PairMatched> addList = new ArrayList<>();

       double distanceToPartyLocation = Coordinate.getDistance(starterPair.getKitchen().coordinate, partyLocation);

       while(foodListIterator.hasNext()){
           PairMatched possiblePair = foodListIterator.next();
           double distanceToPair = Coordinate.getDistance(starterPair.getKitchen().coordinate, possiblePair.getKitchen().coordinate);

           if(distanceToPair <= distanceToPartyLocation){
               System.out.println(possiblePair);
               if(possiblePair.foodPreference.equals(FoodPreference.MEAT) || possiblePair.foodPreference.equals(FoodPreference.NONE)){

                   if(meatNoneCounter >= 1){
                       continue;
                   }
               }
               counter++;
               meatNoneCounter++;
               addList.add(possiblePair);
               if(counter >= counterLimit){
                   break;
               }
           }
       }
       return addList;
   }


   public static List<PairMatched> getSuccessorList(){
        return successors;
   }

    private double calculateMaxDistance(List<PairMatched> pairs) {
        double maxDistance = Double.MIN_VALUE;

        for (PairMatched pair : pairs) {

            double distance = Coordinate.getDistance(partyLocation, pair.getKitchen().coordinate);
            if(distance > maxDistance) {
                maxDistance = distance;
            }
        }
        return maxDistance;
    }
}
