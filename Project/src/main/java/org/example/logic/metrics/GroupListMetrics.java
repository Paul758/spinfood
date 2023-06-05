package org.example.logic.metrics;

import org.example.data.Coordinate;
import org.example.logic.structures.GroupMatched;
import org.example.logic.structures.PairMatched;

import java.util.List;

public class GroupListMetrics {

    /**
     * The age difference of a group-list is determined by calculating the average of the
     * age difference for each pair in the list.
     * @param groups a list of GroupMatched objects
     * @return the average age difference of the group-list
     * @throws IllegalArgumentException if there are no pairs in the group-list
     */
    public static double calcAgeDifference(List<GroupMatched> groups) {
        return getPairsInGroups(groups).stream()
                .mapToDouble(PairMetrics::calcAgeDifference)
                .average()
                .orElseThrow(IllegalArgumentException::new);
    }

    /**
     * The gender diversity of a group-list is determined by calculating the average of the
     * gender diversities for each group in the list.
     * @param groups a list of GroupMatched objects
     * @return the average gender diversity of the pair-list
     * @throws IllegalArgumentException if the pair-list is empty
     */
    public static double calcGenderDiversity(List<GroupMatched> groups) {
        return groups.stream()
                .mapToDouble(GroupMetrics::calcGenderDiversity)
                .average()
                .orElseThrow(IllegalArgumentException::new);
    }

    /**
     * The preference deviation of a group-list is determined by calculating the average of the
     * preference deviations for each pair in the list.
     * @param groups a list of GroupMatched objects
     * @return the average preference deviation of the pair-list
     * @throws IllegalArgumentException if the pair-list is empty
     */
    public static double calcPreferenceDeviation(List<GroupMatched> groups) {
        return getPairsInGroups(groups).stream()
                .mapToDouble(PairMetrics::calcPreferenceDeviation)
                .average()
                .orElseThrow(IllegalArgumentException::new);
    }

    /**
     * The path length of a group-list is determined by calculating the sum of the
     * path lengths for each pair in the list.
     * @param groups a list of GroupMatched objects
     * @param partyLocation a Coordinate object representing the party location
     * @return the sum of the path length of each pair
     */
    public static double calcPathLength(List<GroupMatched> groups, Coordinate partyLocation) {
        return getPairsInGroups(groups).stream()
                .mapToDouble(p -> PairMetrics.calcPathLength(p, partyLocation))
                .sum();
    }

    /**
     * A group-list is valid when:
     * 1. all groups in the list are valid
     * 2. there are no groups that have two identical pairs
     * @param groups a list of GroupMatched objects
     * @return true if the group-list is valid, otherwise false
     */
    public static boolean isValid(List<GroupMatched> groups) {
        for (GroupMatched group : groups) {
            boolean valid = GroupMetrics.isValid(group) && !listHasGroupsWithIdenticalPairs(group, groups);
            if (!valid) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if there is another group in the group-list, that has two more identical pairs like the given group
     * @param groupA a GroupMatched object to check
     * @param groups a list of GroupMatched objects to check against
     * @return true if another group has the same pairs, otherwise false
     */
    public static boolean listHasGroupsWithIdenticalPairs(GroupMatched groupA, List<GroupMatched> groups) {
        for (GroupMatched groupB : groups) {
            if (groupA.equals(groupB)) {
                continue;
            }

            List<PairMatched> pairsA = groupA.getPairList();
            List<PairMatched> pairsB = groupB.getPairList();
            int counter = 0;
            for (PairMatched pair : pairsA) {
                if (pairsB.contains(pair)) counter++;
            }
            if (counter >= 2) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param groups a list of GroupMatched objects
     * @return a distinct list of all pairs that are in groups of the group-list
     */
    public static List<PairMatched> getPairsInGroups(List<GroupMatched> groups) {
        return groups.stream()
                .flatMap(g -> g.getPairList().stream())
                .distinct()
                .toList();
    }

    public static void printAllMetrics(List<GroupMatched> groups, Coordinate partyLocation) {
        System.out.println("Is valid:               " + isValid(groups));
        System.out.println("Count groups:           " + groups.size());
        System.out.println("Count pairs:            " + getPairsInGroups(groups).size());
        System.out.println("Age Difference:         " + MetricTools.round(calcAgeDifference(groups), 2));
        System.out.println("Gender Diversity:       " + MetricTools.round(calcGenderDiversity(groups), 2));
        System.out.println("Preference Deviation:   " + MetricTools.round(calcPreferenceDeviation(groups), 2));
        System.out.println("Path Length:            " + MetricTools.round(calcPathLength(groups, partyLocation), 2));
    }
}
