package org.example.logic.metrics;

import org.example.data.Coordinate;
import org.example.data.enums.FoodPreference;
import org.example.data.enums.KitchenType;
import org.example.data.enums.Sex;
import org.example.logic.structures.PairMatched;
import org.example.logic.tools.MatchingTools;

public class PairMetrics {

    /**
     * The age difference of a pair is absolute value of the numerical values assigned to the age range
     * of the two persons in the pair
     * @param pair a PairMatchedObject
     * @return the difference between the values assigned to each age range
     */
    public static double calcAgeDifference(PairMatched pair) {
        int ageValueA = MatchingTools.getAgeRange(pair.getSoloA().getPerson().age());
        int ageValueB = MatchingTools.getAgeRange(pair.getSoloB().getPerson().age());
        return  Math.abs(ageValueA - ageValueB);
    }

    /**
     * The gender diversity of a pair is the total deviation of the number of women in relation to
     * the total number of people in the pair from the ideal value of 0.5 (1 woman in a group of 2 people).
     * @param pair a PairMatched object
     * @return the gender diversity of the pair
     */
    public static double calcGenderDiversity(PairMatched pair) {
        double countFemale = 0;

        if (pair.getSoloA().getPerson().sex().equals(Sex.FEMALE)) countFemale++;
        if (pair.getSoloB().getPerson().sex().equals(Sex.FEMALE)) countFemale++;

        double ratio = countFemale / PairMatched.pairSize;
        return Math.abs(ratio - MetricTools.idealGenderRatio);
    }

    /**
     * The preference deviation of a pair is determined by calculating the absolute difference between the
     * numerical values representing the food preferences of both persons in the pair.
     * @param pair a PairMatched object
     * @return the preference deviation of the pair
     */
    public static double calcPreferenceDeviation(PairMatched pair) {
        int valueA = MetricTools.getPreferenceValue(pair.getSoloA().getFoodPreference());
        int valueB = MetricTools.getPreferenceValue(pair.getSoloB().getFoodPreference());

        return Math.abs(valueA - valueB);
    }

    /**
     * The path length of a pair is the sum of the distances between the following locations (beeline):
     * 1. From the appetizer to the main course
     * 2. From the main course to the dessert
     * 3. From the dessert to the after-dinner party
     * @param pair a PairMatched Object
     * @param partyLocation a Coordinate object representing the party location
     * @return the path length of the pair
     */
    public static double calcPathLength(PairMatched pair, Coordinate partyLocation) {
        if (pair.getStarterLocation() == null || pair.getMainLocation() == null || pair.getDessertLocation() == null) {
            throw new IllegalArgumentException("The pair is not in all necessary groups");
        }

        if (partyLocation == null) {
            throw new IllegalArgumentException("No valid party location");
        }

        Coordinate starter = pair.getStarterLocation();
        Coordinate main = pair.getMainLocation();
        Coordinate dessert = pair.getDessertLocation();

        double distanceStarterToMain = Coordinate.getDistance(starter, main);
        double distanceMainToDessert = Coordinate.getDistance(main, dessert);
        double distanceDessertToParty = Coordinate.getDistance(dessert, partyLocation);

        return  distanceStarterToMain + distanceMainToDessert + distanceDessertToParty;
    }

    /**
     * A pair is valid when:
     * 1. the pair has a valid food preference combination
     * 2. there is at least one kitchen available
     * @param pair a PairMatched object
     * @return true if the pair is valid, otherwise false
     */
    public static boolean isValid(PairMatched pair) {
        return isPreferenceCombinationValid(pair) && isKitchenAvailable(pair);
    }

    /**
     * The preference combination of a pair is valid, if no one with a meat preference is together with someone with a
     * veggie or vegan preference.
     * @param pair a PairMatched object
     * @return true if the preference combination is valid, otherwise false
     */
    public static boolean isPreferenceCombinationValid(PairMatched pair) {
        FoodPreference preferenceA = pair.getSoloA().getFoodPreference();
        FoodPreference preferenceB = pair.getSoloB().getFoodPreference();

        boolean aIsMeat = preferenceA.equals(FoodPreference.MEAT);
        boolean aIsVeggieOrVegan = preferenceA.equals(FoodPreference.VEGGIE) || preferenceA.equals(FoodPreference.VEGAN);
        boolean bIsMeat = preferenceB.equals(FoodPreference.MEAT);
        boolean bIsVeggieOrVegan = preferenceB.equals(FoodPreference.VEGGIE) || preferenceB.equals(FoodPreference.VEGAN);

        return !(aIsMeat && bIsVeggieOrVegan || bIsMeat && aIsVeggieOrVegan);
    }

    /**
     * A kitchen is available when one person of the pair has a kitchen or an emergency kitchen
     * @param pair a PairMatched object
     * @return true if a kitchen is available, otherwise false
     */
    public static boolean isKitchenAvailable(PairMatched pair) {
        KitchenType kitchenTypeA = pair.getSoloA().getKitchen().getKitchenType();
        KitchenType kitchenTypeB = pair.getSoloB().getKitchen().getKitchenType();

        return kitchenTypeA.equals(KitchenType.YES) || kitchenTypeA.equals(KitchenType.MAYBE)
                || kitchenTypeB.equals(KitchenType.YES) || kitchenTypeB.equals(KitchenType.MAYBE);
    }
}
