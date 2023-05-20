package org.example.logic.tools;

import org.example.data.Coordinate;

import java.util.*;

public class GroupMatchingAlgorithm {

    List<PairMatched> pairs;
    Coordinate partyLocation;
    List<PairMatched> startersPairs;
    List<PairMatched> mainCoursePairs;
    List<PairMatched> dessertPairs;

    public GroupMatchingAlgorithm(List<PairMatched> pairs, Coordinate partyLocation){
        this.pairs = pairs;
        this.partyLocation = partyLocation;
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

    public void sortPairListByDistance(){
        pairs.sort((pairA, pairB) -> {
            double distanceA = Coordinate.getDistance(partyLocation, pairA.getKitchen().coordinate);
            double distanceB = Coordinate.getDistance(partyLocation, pairB.getKitchen().coordinate);
            return Double.compare(distanceB, distanceA);
        });

        pairs.forEach(System.out::println);
    }


   public void sliceDistanceList(List<PairMatched> sortedPairMatchedList){
        int listLength = sortedPairMatchedList.size();
        int third = listLength / 3;

        startersPairs = sortedPairMatchedList.subList(0, third);
        mainCoursePairs = sortedPairMatchedList.subList(third + 1, 2 * third);
        dessertPairs = sortedPairMatchedList.subList(2 * third + 1, sortedPairMatchedList.size() - 1);
   }


   public List<List<GroupMatched>> match(){
       sortPairListByDistance();
       sliceDistanceList(pairs);
       List<GroupMatched> startersGroups = new ArrayList<>();
       List<GroupMatched> mainCourseGroups = new ArrayList<>();
       List<GroupMatched> dessertGroups = new ArrayList<>();

       ArrayList<PairMatched> starterPairs = new ArrayList<>(startersPairs);
       ArrayList<PairMatched> mainPairs = new ArrayList<>(mainCoursePairs);
       ArrayList<PairMatched> dessPairs = new ArrayList<>(dessertPairs);

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


           ArrayList<PairMatched> addPairsListDessert = addPairs(dessPairs.listIterator(), starterPair, 3);
           dessPairs.removeAll(addPairsListDessert);
           System.out.println("The size of dessert now is " + dessPairs.size());
           groupOfNine.addAll(addPairsListDessert);


           if(groupOfNine.size() < 9){
               System.out.println("There are not enough people to form a group of nine");
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
       return groups;
   }

   public ArrayList<PairMatched> addPairs(Iterator<PairMatched> foodListIterator, PairMatched starterPair, int counterLimit){

       int counter = 0;
       ArrayList<PairMatched> addList = new ArrayList<>();
       //System.out.println("location of starter pair is " + starterPair.getKitchen().coordinate);
       //System.out.println("party location is" + partyLocation);
        double distanceToPartyLocation = Coordinate.getDistance(starterPair.getKitchen().coordinate, partyLocation);
       //System.out.println("The foodList size is " + foodList.size());

       while(foodListIterator.hasNext()){
           //System.out.println("Called once");
           PairMatched possiblePair = foodListIterator.next();
           double distanceToPair = Coordinate.getDistance(starterPair.getKitchen().coordinate, possiblePair.getKitchen().coordinate);
           //System.out.println("The distance is " + distanceToPair);
           //System.out.println("The distance to location is " + distanceToPartyLocation);
           if(distanceToPair <= distanceToPartyLocation){
               //System.out.println("Adding to list");
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
