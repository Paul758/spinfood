package org.example.logic.structures;

import org.example.data.Coordinate;
import org.example.data.enums.FoodPreference;
import org.example.data.factory.Kitchen;
import org.example.logic.tools.MatchingTools;
import org.example.logic.enums.MealType;

import java.util.ArrayList;
import java.util.List;

/**
 * Data storage class for the matched groups in the logic layer
 */
public class GroupMatched {
    public static final int groupSize = 3;
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
            pair.addToGroup(this);
        }
    }

    public PairMatched getCook() {
        return cook;
    }

    public MealType getMealType() {
        return mealType;
    }

    public boolean containsPair(PairMatched pairMatched) {
        return pairs.contains(pairMatched);
    }

    /**
     * Calculates the food preference of the group, the food preference of the group is the food preference of a pair
     * that has the highest priority.
     * The priories are: 1. vegan, 2. veggie, 3. meat, 4. none
     * @return the food preference of the group
     */
    protected FoodPreference calculateFoodPreference() {
        int foodPreferencePairA = MatchingTools.getIntValueFoodPreference(pairA.getFoodPreference());
        int foodPreferencePairB = MatchingTools.getIntValueFoodPreference(pairB.getFoodPreference());
        int foodPreferencePairC = MatchingTools.getIntValueFoodPreference(pairC.getFoodPreference());
        return FoodPreference.parseFoodPreference(Math.max(foodPreferencePairA, Math.max(foodPreferencePairB, foodPreferencePairC)));
        //throw new IllegalStateException("not implemented yet");
    }

    @Override
    public String toString() {
        return "(" + pairs.get(0).toString() + "), ("
                + pairs.get(1).toString() + "), ("
                + pairs.get(2).toString() + ")";
    }

    /**
     * Replaces a pair of the group with a new pair
     * @param thisPair the pair of the group that should be replaced
     * @param newPair the new pair
     */
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

    public Coordinate getKitchenCoordinate() {
        return cook.getKitchen().coordinate;
    }

    public void removePair(PairMatched pair) {
        pairs.remove(pair);
    }

    public void addPair(PairMatched pair) {
        pairs.add(pair);
    }
}
