package org.example.logic.structures;

import org.example.data.Coordinate;
import org.example.data.enums.FoodPreference;
import org.example.data.enums.KitchenType;
import org.example.data.factory.Kitchen;
import org.example.data.factory.Person;
import org.example.data.structures.Pair;
import org.example.data.structures.Solo;
import org.example.logic.tools.MatchingTools;
import org.example.logic.enums.MealType;

/**
 * Data storage class for the matched pairs in the logic layer
 */
public class PairMatched implements Comparable<PairMatched> {
    public static final int pairSize = 2;
    private Solo soloA;
    private Solo soloB;
    private FoodPreference foodPreference;
    private Kitchen kitchen;
    public boolean preMatched;
    private Coordinate partyLocation;
    private Double distanceToPartyLocation;
    public MealType cooksMealType;
    private GroupMatched starterGroup;
    private GroupMatched mainGroup;
    private GroupMatched dessertGroup;

    public PairMatched(Pair pair){
        this(pair.soloA, pair.soloB);
        this.preMatched = true;
    }

    public PairMatched(Solo soloA, Solo soloB){
        this.soloA = soloA;
        this.soloB = soloB;
        this.foodPreference = calculateFoodPreference(soloA, soloB);
        this.kitchen = calculateKitchen(soloA, soloB);
        this.preMatched = false;
    }

    /**
     * Gradually sets the field variables related to the group membership based on the meal type / course
     * @param groupMatched A group which the pair is part of
     */
    public void addToGroup(GroupMatched groupMatched) {
        switch (groupMatched.getMealType()) {
            case NONE -> throw new IllegalArgumentException();
            case STARTER -> starterGroup = groupMatched;
            case MAIN ->  mainGroup = groupMatched;
            case DESSERT -> dessertGroup = groupMatched;
        }
    }

    public GroupMatched getStarterGroup() {
        return starterGroup;
    }

    public GroupMatched getMainGroup() {
        return mainGroup;
    }

    public GroupMatched getDessertGroup() {
        return dessertGroup;
    }

    public Coordinate getPartyLocation() {
        return partyLocation;
    }

    public Coordinate getStarterLocation() {
        return starterGroup.getKitchenCoordinate();
    }

    public Coordinate getMainLocation() {
        return mainGroup.getKitchenCoordinate();
    }

    public Coordinate getDessertLocation() {
        return dessertGroup.getKitchenCoordinate();
    }

    /**
     * Determine the common foodPreference
     * @param soloA first member of the pair
     * @param soloB second member of the pair
     * @return the common food preference
     */
    private FoodPreference calculateFoodPreference(Solo soloA, Solo soloB) {
        int foodValueA = MatchingTools.getIntValueFoodPreference(soloA.foodPreference);
        int foodValueB = MatchingTools.getIntValueFoodPreference(soloB.foodPreference);
        return FoodPreference.parseFoodPreference(Math.max(foodValueA, foodValueB));
    }

    /**
     * Determine which kitchen of the two solos should be used
     * @param soloA first member of the pair
     * @param soloB second member of the pair
     * @return the kitchen that the pair uses.
     */
    private Kitchen calculateKitchen(Solo soloA, Solo soloB) {
        Kitchen kitchenA = soloA.kitchen;
        Kitchen kitchenB = soloB.kitchen;

        if (kitchenA.kitchenType.equals(KitchenType.YES)) return kitchenA;
        else if (kitchenB.kitchenType.equals(KitchenType.YES)) return kitchenB;
        else if (kitchenA.kitchenType.equals(KitchenType.MAYBE)) return kitchenA;
        else if (kitchenB.kitchenType.equals(KitchenType.MAYBE)) return kitchenB;
        else throw new IllegalStateException(this + " has no kitchen");
    }

    public boolean containsPerson(Person person) {
        return soloA .person.equals(person) || soloB.person.equals(person);
    }

    @Override
    public int compareTo(PairMatched o) {
        return Double.compare(this.getDistanceToPartyLocation(), o.getDistanceToPartyLocation());
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof PairMatched other)){
            return false;
        }
        return this.soloA.equals(other.soloA) && this.soloB.equals(other.soloB);
    }

    @Override
    public String toString() {
        return soloA + ", " + soloB + ", " + getFoodPreference();
    }

    //Getters, Setters
    public void setDistanceToPartyLocation(Coordinate partyLocation) {
        this.distanceToPartyLocation = Coordinate.getDistance(getKitchen().coordinate, partyLocation);
    }

    public double getDistanceToPartyLocation() {
        if (this.distanceToPartyLocation == null) {
            throw new IllegalStateException("Distance to party location isn't set");
        }
        return this.distanceToPartyLocation;
    }

    public FoodPreference getFoodPreference() {
        return foodPreference;
    }
    public Kitchen getKitchen() {
        return kitchen;
    }

    public boolean contains(Solo solo) {
        return soloA.equals(solo) || soloB.equals(solo);
    }

    public void setSoloA(Solo newSolo) {
        soloA = newSolo;
    }

    public void setSoloB(Solo newSolo) {
        soloB = newSolo;
    }

    public Solo getSoloA(){
        return soloA;
    }

    public Solo getSoloB(){
        return soloB;
    }

    public void updatePairMatchedData() {
        this.foodPreference = calculateFoodPreference(soloA, soloB);
        this.kitchen = calculateKitchen(soloA, soloB);
        this.preMatched = false;
    }
}
