package org.example.logic.metrics;

import org.example.data.Coordinate;
import org.example.data.enums.Sex;
import org.example.logic.enums.MealType;
import org.example.logic.structures.PairMatched;
import org.example.logic.tools.MatchingTools;

public class PairMetrics {

    public static double calcAgeDifference(PairMatched pair) {
        int ageValueA = MatchingTools.getAgeRange(pair.getPersonA().age());
        int ageValueB = MatchingTools.getAgeRange(pair.getPersonB().age());
        return  Math.abs(ageValueA - ageValueB);
    }

    public static double calcGenderDifference(PairMatched pair) {
        double countFemale = 0;

        if (pair.getPersonA().sex().equals(Sex.FEMALE)) countFemale++;
        if (pair.getPersonB().sex().equals(Sex.FEMALE)) countFemale++;

        double ratio = countFemale / PairMatched.pairSize;
        return Math.abs(ratio - MetricTools.idealGenderRatio);
    }

    public static double calcPreferenceDeviation(PairMatched pair) {
        int valueA = MetricTools.getPreferenceValue(pair.getFoodPreferenceA());
        int valueB = MetricTools.getPreferenceValue(pair.getFoodPreferenceB());
        return Math.abs(valueA - valueB);
    }

    public static double calcPathLength(PairMatched pair) {
        Coordinate partyLocation = pair.getPartyLocation();
        Coordinate starter = pair.getStarterGroup().getKitchenCoordinate();
        Coordinate main = pair.getMainGroup().getKitchenCoordinate();
        Coordinate dessert = pair.getDessertGroup().getKitchenCoordinate();

        double distanceStarterToMain = Coordinate.getDistance(starter, main);
        double distanceMainToDessert = Coordinate.getDistance(main, dessert);
        double distanceDessertToParty = Coordinate.getDistance(dessert, partyLocation);

        return  distanceStarterToMain + distanceMainToDessert + distanceDessertToParty;
    }

    public static boolean isValidAfterPairMatch(PairMatched pair) {
        return !pair.getPersonA().equals(pair.getPersonB());
    }

    public static boolean isValidAfterGroupMatch(PairMatched pair) {
        return isValidAfterPairMatch(pair)
                && pair.getPartyLocation() != null
                && pair.getStarterGroup() != null
                && pair.getMainGroup() != null
                && pair.getDessertGroup() != null
                && !pair.cooksMealType.equals(MealType.NONE);
    }
}
