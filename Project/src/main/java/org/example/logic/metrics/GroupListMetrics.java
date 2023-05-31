package org.example.logic.metrics;

import org.example.logic.structures.GroupMatched;
import org.example.logic.structures.PairMatched;

import java.util.List;

public class GroupListMetrics {

    public static double calcAgeDifference(List<GroupMatched> groups) {
        List<PairMatched> pairs = getAllPairs(groups);
        return PairListMetrics.calcAgeDifference(pairs);
    }

    public static double calcGenderDiversity(List<GroupMatched> groups) {
        return groups.stream()
                .mapToDouble(GroupMetrics::calcGenderDiversity)
                .average()
                .orElse(-1);
    }

    public static double calcPreferenceDeviation(List<GroupMatched> groups) {
        return getAllPairs(groups).stream()
                .mapToDouble(PairMetrics::calcPreferenceDeviation)
                .average()
                .orElse(-1);
    }

    public static double calcPathLength(List<GroupMatched> groups) {
        return getAllPairs(groups).stream()
                .mapToDouble(PairMetrics::calcPathLength)
                .sum();
    }

    public static boolean isValid(List<GroupMatched> groups) {
        return groups.stream()
                .allMatch(GroupMetrics::isValid);
    }

    private static List<PairMatched> getAllPairs(List<GroupMatched> groups) {
        return groups.stream()
                .flatMap(g -> g.getPairs().stream())
                .distinct()
                .toList();
    }

    public static void printAllMetrics(List<GroupMatched> groups) {
        System.out.println("Is valid:               " + isValid(groups));
        System.out.println("Count groups:           " + groups.size());
        System.out.println("Count pairs:            " + getAllPairs(groups).size());
        System.out.println("Age Difference:         " + MetricTools.round(calcAgeDifference(groups), 2));
        System.out.println("Gender Diversity:       " + MetricTools.round(calcGenderDiversity(groups), 2));
        System.out.println("Preference Deviation:   " + MetricTools.round(calcPreferenceDeviation(groups), 2));
        System.out.println("Path Length:            " + MetricTools.round(calcPathLength(groups), 2));
    }
}
