package org.example.logic.structures;

import org.example.data.Coordinate;
import org.example.data.enums.FoodPreference;
import org.example.data.structures.Pair;
import org.example.logic.tools.MatchingTools;
import org.example.logic.enums.MealType;
import org.example.logic.tools.Metricable;

import java.util.ArrayList;
import java.util.List;

public class GroupMatched implements Metricable {

    private static final int groupSize = 3;
    private PairMatched cook;
    MealType mealType;
    PairMatched pairA;
    PairMatched pairB;
    PairMatched pairC;
    private List<PairMatched> pairs;

    FoodPreference groupFoodPreference;


    public GroupMatched(PairMatched cook, PairMatched guestA, PairMatched guestB, MealType mealType) {
        this.cook = cook;
        this.cook.cooksMealType = mealType;
        this.mealType = mealType;

        this.pairs = new ArrayList<>();
        this.pairs.add(cook);
        this.pairs.add(guestA);
        this.pairs.add(guestB);

        this.pairA = cook;
        this.pairB = guestA;
        this.pairC = guestB;

        this.groupFoodPreference = calculateFoodPreference();

        for (PairMatched pair : pairs) {
            pair.addGroup(this);
        }
    }

    public boolean containsPair(PairMatched pairMatched) {
        return pairs.contains(pairMatched);
    }

    public Coordinate getKitchenCoordinate() {
        return cook.getKitchen().coordinate;
    }

    protected FoodPreference calculateFoodPreference() {
        //Calculate here
        int foodPreferencePairA = MatchingTools.getIntValueFoodPreference(pairA.getFoodPreference());
        int foodPreferencePairB = MatchingTools.getIntValueFoodPreference(pairB.getFoodPreference());
        int foodPreferencePairC = MatchingTools.getIntValueFoodPreference(pairC.getFoodPreference());
        return FoodPreference.parseFoodPreference(Math.max(foodPreferencePairA, Math.max(foodPreferencePairB, foodPreferencePairC)));

        //throw new IllegalStateException("not implemented yet");
    }

    public void deleteGroup() {

    }

    @Override
    public String toString() {
        return "(" + pairs.get(0).toString() + "), ("
                + pairs.get(1).toString() + "), ("
                + pairs.get(2).toString() + ")";
    }

    @Override
    public double getPathLength() {
        double sum = 0;
        for (PairMatched pair : pairs) {
            sum += pair.getPathLength();
        }
        return sum / groupSize;
    }

    @Override
    public double getGenderDeviation() {
        double sum = 0;
        for (PairMatched pair : pairs) {
            sum += pair.getGenderDeviation();
        }
        return sum / groupSize;
    }

    @Override
    public double getAgeRangeDeviation() {
        double sum = 0;
        for (PairMatched pair : pairs) {
            sum += pair.getAgeRangeDeviation();
        }
        return sum / groupSize;
    }

    @Override
    public double getFoodPreferenceDeviation() {
        double sum = 0;
        for (PairMatched pair : pairs) {
            sum += pair.getFoodPreferenceDeviation();
        }
        return sum / groupSize;
    }

    @Override
    public boolean isValid() {
        return cook != null
                && pairs.size() == groupSize
                && pairs.get(0).isValid()
                && pairs.get(1).isValid()
                && pairs.get(2).isValid()
                && validFoodPreferences();
    }

    private boolean validFoodPreferences() {
        int meatNoneCount = 0;
        int veggieVeganCount = 0;

        for (PairMatched pair : pairs) {
            boolean isMeatOrNone = pair.getFoodPreference().equals(FoodPreference.MEAT)
                    || pair.getFoodPreference().equals(FoodPreference.NONE);

            if (isMeatOrNone) meatNoneCount++;
            else veggieVeganCount++;
        }

        return !(meatNoneCount == 1 && veggieVeganCount == 2);
    }

    public void switchPairs(PairMatched thisPair, PairMatched newPair) {
        if(thisPair.equals(pairA)){
            pairA = newPair;
            System.out.println("switched pair A");
        }
        else if(thisPair.equals(pairB)){
            pairB = newPair;
            System.out.println("switched pair B");
        }
        else if(thisPair.equals(pairC)){
            pairC = newPair;
            System.out.println("switched pair C");
        }

        if(thisPair.equals(cook)){
            System.out.println("old pair was cook, new cook is " + newPair);
            cook = newPair;
        }

        removePair(thisPair);
        addPair(newPair);
    }

    public List<PairMatched> getPairList(){
        return pairs;
    }

    public PairMatched getCook(){
        return cook;
    }

    public void removePair(PairMatched pair) {
        pairs.remove(pair);
    }

    public void addPair(PairMatched pair) {
        pairs.add(pair);
    }
}
