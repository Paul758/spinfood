package org.example.logic.metrics;

import org.example.logic.structures.PairMatched;

import java.util.List;

public class PairListMetrics {

    public static double calcAgeDifference(List<PairMatched> pairs) {
        return pairs.stream()
                .mapToDouble(PairMetrics::calcAgeDifference)
                .average()
                .orElse(-1);
    }

    public static double calcGenderDiversity(List<PairMatched> pairs) {
        return pairs.stream()
                .mapToDouble(PairMetrics::calcGenderDifference)
                .average()
                .orElse(-1);
    }

    public static double calcPreferenceDeviation(List<PairMatched> pairs) {
        return pairs.stream()
                .mapToDouble(PairMetrics::calcPreferenceDeviation)
                .average()
                .orElse(-1);
    }

    public static boolean isValid(List<PairMatched> pairs) {
        return pairs.stream()
                .allMatch(PairMetrics::isValidAfterPairMatch);
    }

    public static void printAllMetrics(List<PairMatched> pairs) {
        System.out.println("Is valid:               " + isValid(pairs));
        System.out.println("Count pairs:            " + pairs.size());
        System.out.println("Age Difference:         " + MetricTools.round(calcAgeDifference(pairs), 2));
        System.out.println("Gender Diversity:       " + MetricTools.round(calcGenderDiversity(pairs), 2));
        System.out.println("Preference Deviation:   " + MetricTools.round(calcPreferenceDeviation(pairs), 2));
    }
}
