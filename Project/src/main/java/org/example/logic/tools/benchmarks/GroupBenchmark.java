package org.example.logic.tools.benchmarks;

import org.example.data.Coordinate;
import org.example.logic.structures.GroupMatched;

import java.util.Collection;

public class GroupBenchmark {


    public static void matchedGroupsBenchmark(Collection<GroupMatched> matchedGroups) {
        for (GroupMatched group : matchedGroups) {
            //System.out.printf("Benchmark for group " + group);
            System.out.println("Age difference: " + calculateAgeDifference(group) + " optimal 0");
            System.out.println("Sex difference " + calculateSexDifference(group) + " optimal: 0.5");
            System.out.println("food deviation " + calculateFoodPreferenceDeviation(group) + " optimal 0");
            System.out.println("Path length ");

        }
    }

    public static double calculateAgeDifference(GroupMatched group) {
        return group.getAgeRangeDeviation();
    }

    public static double calculateSexDifference(GroupMatched group) {
        return group.getGenderDeviation();
    }

    public static double calculateFoodPreferenceDeviation(GroupMatched group) {
        return group.getFoodPreferenceDeviation();
    }

}
