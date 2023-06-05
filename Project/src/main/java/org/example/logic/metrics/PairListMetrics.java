package org.example.logic.metrics;

import org.example.logic.structures.PairMatched;

import java.util.List;

public class PairListMetrics {

    /**
     * The age difference of a pair-list is the average of the age differences of each pair in the list
     * @param pairs a list of PairMatched objects
     * @return the average age difference of the pair-list
     * @throws IllegalArgumentException if the pair-list is empty
     */
    public static double calcAgeDifference(List<PairMatched> pairs) {
        return pairs.stream()
                .mapToDouble(PairMetrics::calcAgeDifference)
                .average()
                .orElseThrow(IllegalArgumentException::new);
    }

    /**
     * The gender diversity of a pair-list is determined by calculating the average of the
     * gender diversities for each pair in the list.
     * @param pairs a list of PairMatched objects
     * @return the average gender diversity of the pair-list
     * @throws IllegalArgumentException if the pair-list is empty
     */
    public static double calcGenderDiversity(List<PairMatched> pairs) {
        return pairs.stream()
                .mapToDouble(PairMetrics::calcGenderDiversity)
                .average()
                .orElseThrow(IllegalArgumentException::new);
    }

    /**
     * The preference deviation of a pair-list is determined by calculating the average of the
     * preference deviations for each pair in the list.
     * @param pairs a list of PairMatched objects
     * @return the average preference deviation of the pair-list
     * @throws IllegalArgumentException if the pair-list is empty
     */
    public static double calcPreferenceDeviation(List<PairMatched> pairs) {
        return pairs.stream()
                .mapToDouble(PairMetrics::calcPreferenceDeviation)
                .average()
                .orElseThrow(IllegalArgumentException::new);
    }

    /**
     * A pair-list is valid, if each pair in the list is valid
     * @param pairs a list of PairMatched objects
     * @return true if all pairs are valid, otherwise false
     */
    public static boolean isValid(List<PairMatched> pairs) {
        return pairs.stream()
                .allMatch(PairMetrics::isValid);
    }

    public static void printAllMetrics(List<PairMatched> pairs) {
        System.out.println("Is valid:               " + isValid(pairs));
        System.out.println("Count pairs:            " + pairs.size());
        System.out.println("Age Difference:         " + MetricTools.round(calcAgeDifference(pairs), 2));
        System.out.println("Gender Diversity:       " + MetricTools.round(calcGenderDiversity(pairs), 2));
        System.out.println("Preference Deviation:   " + MetricTools.round(calcPreferenceDeviation(pairs), 2));
    }
}
