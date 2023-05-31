package org.example.logic.metrics;

import org.example.data.enums.Sex;
import org.example.logic.structures.GroupMatched;
import org.example.logic.structures.PairMatched;

public class GroupMetrics {

    public static double calcAgeDifference(GroupMatched group) {
        return PairListMetrics.calcAgeDifference(group.getPairs());
    }

    public static double calcGenderDiversity(GroupMatched group) {
        double countFemale = 0;

        for (PairMatched pair : group.getPairs()) {
            if (pair.getPersonA().sex().equals(Sex.FEMALE)) countFemale++;
            if (pair.getPersonB().sex().equals(Sex.FEMALE)) countFemale++;
        }

        double ratio = countFemale / (GroupMatched.groupSize * PairMatched.pairSize);
        return Math.abs(ratio - MetricTools.idealGenderRatio);
    }

    public static double calcPreferenceDeviation(GroupMatched group) {
        return group.getPairs().stream()
                .mapToDouble(PairMetrics::calcPreferenceDeviation)
                .average()
                .orElse(-1);
    }


    //todo
    public static boolean isValid(GroupMatched group) {
        return true;
    }


}
