package org.example.logic.tools.benchmarks;

import org.example.data.Coordinate;
import org.example.logic.structures.GroupMatched;
import org.example.logic.structures.PairMatched;

import java.util.Collection;

public class GroupBenchmark {


    public static void matchedGroupsBenchmark(Collection<GroupMatched> matchedGroups) {
        for (GroupMatched group : matchedGroups) {
            //System.out.printf("Benchmark for group " + group);
            System.out.println("Age difference: " + calculateAgeDifference(group) + " optimal 0");
            System.out.println("Sex difference " + calculateSexDifference(group) + " optimal: 0.5");
            System.out.println("food deviation " + calculateFoodPreferenceDeviation(group) + " optimal 0");
            System.out.println("Path length " + calculatePathLength(group));

        }
    }

    public static int calculateAgeDifference(GroupMatched group) {
        return group.calculateAgeRangeDeviation();
    }

    public static float calculateSexDifference(GroupMatched group) {
        return group.calculateSexDeviation();
    }

    public static int calculateFoodPreferenceDeviation(GroupMatched group) {
        return group.calculateFoodPreferenceDeviation();
    }

    public static double calculatePathLength(GroupMatched group) {
        Coordinate positionPairA = group.pairA.getKitchen().coordinate;
        Coordinate positionPairB = group.pairB.getKitchen().coordinate;
        Coordinate positionPairC = group.pairC.getKitchen().coordinate;
        Coordinate positionPairCook = group.cook.getKitchen().coordinate;

        return Coordinate.getDistance(positionPairA, positionPairCook) +
                Coordinate.getDistance(positionPairB, positionPairCook) +
                Coordinate.getDistance(positionPairC, positionPairCook);
    }
}
