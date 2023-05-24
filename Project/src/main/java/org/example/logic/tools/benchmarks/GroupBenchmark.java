package org.example.logic.tools.benchmarks;

import org.example.data.Coordinate;
import org.example.logic.structures.GroupMatched;
import org.example.logic.structures.PairMatched;

import java.util.Collection;

public class GroupBenchmark {


    public void matchedGroupsBenchmark(Collection<GroupMatched> matchedGroups, Collection<PairMatched> matchedPairs) {




    }

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
