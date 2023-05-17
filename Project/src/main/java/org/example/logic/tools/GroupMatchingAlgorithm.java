package org.example.logic.tools;

import org.example.data.Coordinate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GroupMatchingAlgorithm {

    List<PairMatched> pairs;
    Coordinate partyLocation;
    List<PairMatched> startersPairs;
    List<PairMatched> mainCoursePairs;
    List<PairMatched> dessertPairs;

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
            return Double.compare(distanceA, distanceB);
        });
    }


   public void sliceDistanceList(List<PairMatched> sortedPairMatchedList){
        int listLength = sortedPairMatchedList.size();
        int third = listLength / 3;

        startersPairs = sortedPairMatchedList.subList(0, third);
        mainCoursePairs = sortedPairMatchedList.subList(third + 1, 2 * third);
        dessertPairs = sortedPairMatchedList.subList(2 * third + 1, 3 * third);
   }


   public void match(){
        sortPairListByDistance();
        sliceDistanceList(pairs);

        List<PairMatched> groupOfNine = new ArrayList<>();
        PairMatched starterPair = startersPairs.get(0);
        groupOfNine.add(starterPair);
        startersPairs.remove(0);
        double distanceToPartyLocation = Coordinate.getDistance(starterPair.getKitchen().coordinate, partyLocation);

        int counter = 0;

        //Get 2 more starter pairs
        groupOfNine.addAll(addPairs(startersPairs,starterPair, 2));
        groupOfNine.addAll(addPairs(mainCoursePairs,starterPair, 3));
        groupOfNine.addAll(addPairs(dessertPairs,starterPair, 3));

        

   }

   public List<PairMatched> addPairs(List<PairMatched> foodList, PairMatched starterPair, int counterLimit){

        int counter = 0;
        List<PairMatched> addList = new ArrayList<>();
        double distanceToPartyLocation = Coordinate.getDistance(starterPair.getKitchen().coordinate,partyLocation);
       for(int i = 0; i < foodList.size(); i++){
           PairMatched possiblePair = foodList.get(i);
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
