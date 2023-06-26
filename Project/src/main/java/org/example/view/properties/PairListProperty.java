package org.example.view.properties;

import org.example.logic.metrics.MetricTools;
import org.example.logic.metrics.PairListMetrics;
import org.example.logic.structures.MatchingRepository;
import org.example.logic.structures.PairMatched;
import org.example.view.PairListTabController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class PairListProperty {
    PairListTabController pairListTabController;
    MatchingRepository matchingRepository;
    private final static int DECIMAL_PLACES = 4;

    public PairListProperty(PairListTabController pairListTabController) {
        this.pairListTabController = pairListTabController;
        this.matchingRepository = pairListTabController.getMatchingRepository();
    }

    public static LinkedHashMap<String, List<Entry>> getColumnNames() {
        List<Entry> metrics = List.of(
                new Entry("pairCount"),
                new Entry("successorCount"),
                new Entry("ageDifference"),
                new Entry("genderDiversity"),
                new Entry("foodPreferenceDeviation"),
                new Entry("isValid")
        );

        LinkedHashMap<String, List<Entry>> map = new LinkedHashMap<>();
        map.put("", metrics);
        return map;
    }

    public static LinkedHashMap<String, List<Entry>> getColumnNames2() {
        List<Entry> metrics = List.of(
                new Entry("name"),
                new Entry("pairCount"),
                new Entry("successorCount"),
                new Entry("ageDifference"),
                new Entry("genderDiversity"),
                new Entry("foodPreferenceDeviation"),
                new Entry("totalScore"),
                new Entry("isValid")

        );

        LinkedHashMap<String, List<Entry>> map = new LinkedHashMap<>();
        map.put("", metrics);
        return map;
    }

    public String getName() {
        return pairListTabController.getName();
    }

    public String getPairCount() {
        return String.valueOf(matchingRepository.getMatchedPairsCollection().size());
    }

    public String getSuccessorCount() {
        return String.valueOf(matchingRepository.soloSuccessors.size());
    }

    public String getAgeDifference() {
        List<PairMatched> pairs = new ArrayList<>(matchingRepository.getMatchedPairsCollection());
        double ageDifference = PairListMetrics.calcAgeDifference(pairs);
        return String.valueOf(MetricTools.round(ageDifference, DECIMAL_PLACES));
    }

    public String getGenderDiversity() {
        List<PairMatched> pairs = new ArrayList<>(matchingRepository.getMatchedPairsCollection());
        double genderDiversity = PairListMetrics.calcGenderDiversity(pairs);
        return String.valueOf(MetricTools.round(genderDiversity, DECIMAL_PLACES));
    }

    public String getFoodPreferenceDeviation() {
        List<PairMatched> pairs = new ArrayList<>(matchingRepository.getMatchedPairsCollection());
        double preferenceDeviation = PairListMetrics.calcPreferenceDeviation(pairs);
        return String.valueOf(MetricTools.round(preferenceDeviation, DECIMAL_PLACES));
    }

    public String getIsValid() {
        List<PairMatched> pairs = new ArrayList<>(matchingRepository.getMatchedPairsCollection());
        boolean isValid = PairListMetrics.isValid(pairs);
        return String.valueOf(isValid);
    }

    public String getTotalScore() {
        List<PairMatched> pairs = new ArrayList<>(matchingRepository.getMatchedPairsCollection());
        double totalScore = PairListMetrics.calcAgeDifference(pairs)
                + PairListMetrics.calcGenderDiversity(pairs)
                + PairListMetrics.calcPreferenceDeviation(pairs);
        return String.valueOf(MetricTools.round(totalScore, DECIMAL_PLACES));
    }
}
