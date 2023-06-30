package org.example.view.properties;

import org.example.data.Coordinate;
import org.example.logic.metrics.GroupListMetrics;
import org.example.logic.metrics.MetricTools;
import org.example.logic.structures.GroupMatched;
import org.example.logic.structures.MatchingRepository;
import org.example.view.controller.GroupListTabController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@SuppressWarnings("unused")
public class GroupListProperty {
    private final GroupListTabController controller;
    private final MatchingRepository matchingRepository;
    private final List<GroupMatched> groups;
    private static final int DECIMAL_PLACES = 4;

    public GroupListProperty(GroupListTabController controller) {
        this.controller = controller;
        this.matchingRepository = controller.getMatchingRepository();
        this.groups = new ArrayList<>(matchingRepository.getMatchedGroupsCollection());
    }

    public static LinkedHashMap<String, List<Entry>> getTabColumns() {
        List<Entry> metrics = List.of(
                new Entry("groupCount"),
                new Entry("successorCount"),
                new Entry("ageDifference"),
                new Entry("genderDiversity"),
                new Entry("foodPreferenceDeviation"),
                new Entry("totalScore"),
                new Entry("pathLength"),
                new Entry("isValid")
        );

        LinkedHashMap<String, List<Entry>> map = new LinkedHashMap<>();
        map.put("", metrics);
        return map;
    }

    public static LinkedHashMap<String, List<Entry>> getComparerColumns() {
        List<Entry> metrics = List.of(
                new Entry("name"),
                new Entry("groupCount"),
                new Entry("successorCount"),
                new Entry("ageDifference"),
                new Entry("genderDiversity"),
                new Entry("foodPreferenceDeviation"),
                new Entry("totalScore"),
                new Entry("pathLength"),
                new Entry("isValid")
        );

        LinkedHashMap<String, List<Entry>> map = new LinkedHashMap<>();
        map.put("", metrics);
        return map;
    }

    public String getName() {
        return controller.getName();
    }

    public String getGroupCount() {
        return String.valueOf(groups.size());
    }

    public String getSuccessorCount() {
        return String.valueOf(matchingRepository.pairSuccessors.size());
    }

    public String getAgeDifference() {
        double ageDifference = GroupListMetrics.calcAgeDifference(groups);
        return String.valueOf(MetricTools.round(ageDifference, DECIMAL_PLACES));
    }

    public String getGenderDiversity() {
        double genderDiversity = GroupListMetrics.calcGenderDiversity(groups);
        return String.valueOf(MetricTools.round(genderDiversity, DECIMAL_PLACES));
    }

    public String getFoodPreferenceDeviation() {
        double preferenceDeviation = GroupListMetrics.calcPreferenceDeviation(groups);
        return String.valueOf(MetricTools.round(preferenceDeviation, DECIMAL_PLACES));
    }

    public String getPathLength() {
        Coordinate partyLocation = matchingRepository.dataManagement.partyLocation;
        double pathLength = GroupListMetrics.calcPathLength(groups, partyLocation);
        return String.valueOf(MetricTools.round(pathLength, DECIMAL_PLACES));
    }

    public String getIsValid() {
        boolean isValid = GroupListMetrics.isValid(groups);
        return String.valueOf(isValid);
    }

    public String getTotalScore() {
        double totalScore = GroupListMetrics.calcAgeDifference(groups)
                + GroupListMetrics.calcGenderDiversity(groups)
                + GroupListMetrics.calcPreferenceDeviation(groups);
        return String.valueOf(MetricTools.round(totalScore, DECIMAL_PLACES));
    }
}
