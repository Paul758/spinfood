package org.example.logic.tools;

import org.example.data.Coordinate;

public class GroupBenchmark {


    public int calculateAgeDifference(GroupMatched group) {
        return group.calculateAgeRangeDeviation();
    }

    public float calculateSexDifference(GroupMatched group) {
        return group.calculateSexDeviation();
    }

    public int calculateFoodPreferenceDeviation(GroupMatched group) {
        return group.calculateFoodPreferenceDeviation();
    }

    public double calculatePathLength(GroupMatched group) {
        Coordinate positionPairA = group.pairA.getKitchen().coordinate;
        Coordinate positionPairB = group.pairB.getKitchen().coordinate;
        Coordinate positionPairC = group.pairC.getKitchen().coordinate;
        Coordinate positionPairCook = group.cook.getKitchen().coordinate;
        
        return Coordinate.getDistance(positionPairA, positionPairCook) +
                Coordinate.getDistance(positionPairB, positionPairCook) +
                Coordinate.getDistance(positionPairC, positionPairCook);
    }
}
