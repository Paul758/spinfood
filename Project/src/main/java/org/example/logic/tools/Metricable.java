package org.example.logic.tools;

public interface Metricable {
    double getPathLength();
    double getGenderDeviation();
    double getAgeRangeDeviation();
    double getFoodPreferenceDeviation();
    boolean isValid();
}
