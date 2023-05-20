package org.example.logic.tools;

import org.example.data.Coordinate;
import org.example.data.enums.FoodPreference;
import org.example.data.enums.KitchenType;
import org.example.data.factory.Kitchen;
import org.example.data.factory.Person;
import org.example.data.structures.Pair;
import org.example.data.structures.Solo;

public class PairMatched implements Comparable<PairMatched> {

    private final Person personA;
    private final Person personB;
    private final  FoodPreference foodPreference;
    private final   Kitchen kitchen;
    private Double distanceToPartyLocation;
    int foodPreferenceDeviation;
    int ageRangeDeviation;
    boolean prematched;

    public PairMatched(Pair pair){
        this.personA = pair.personA;
        this.personB = pair.personB;
        this.foodPreference = pair.foodPreference;
        this.kitchen = pair.kitchen;

        this.foodPreferenceDeviation = 0;
        this.prematched = true;
    }

    public PairMatched(Solo soloA, Solo soloB){
        this.personA = soloA.person;
        this.personB = soloB.person;
        this.foodPreference = calculateFoodPreference(soloA, soloB);
        this.kitchen = calculateKitchen(soloA, soloB);

        this.foodPreferenceDeviation = MatchingTools.calculateFoodPreferenceDeviation(soloA.foodPreference, soloB.foodPreference);
        this.prematched = false;
    }


    public Person getPersonA() {
        return personA;
    }

    public Person getPersonB() {
        return personB;
    }

    public FoodPreference getFoodPreference() {
        return foodPreference;
    }

    public Kitchen getKitchen() {
        return kitchen;
    }

    public boolean isValid() {
        return !personA.equals(personB);
    }

    private FoodPreference calculateFoodPreference(Solo soloA, Solo soloB) {
        int foodValueA = MatchingTools.getFoodPreference(soloA.foodPreference);
        int foodValueB = MatchingTools.getFoodPreference(soloB.foodPreference);
        return FoodPreference.parseFoodPreference(Math.max(foodValueA, foodValueB));
    }

    private Kitchen calculateKitchen(Solo soloA, Solo soloB) {
        Kitchen kitchenA = soloA.kitchen;
        Kitchen kitchenB = soloB.kitchen;

        if (kitchenA.kitchenType.equals(KitchenType.YES)) return kitchenA;
        else if (kitchenB.kitchenType.equals(KitchenType.YES)) return kitchenB;
        else if (kitchenA.kitchenType.equals(KitchenType.MAYBE)) return kitchenA;
        else if (kitchenB.kitchenType.equals(KitchenType.MAYBE)) return kitchenB;
        else throw new IllegalStateException(this + " has no kitchen");
    }

    public void setDistanceToPartyLocation(Coordinate partyLocation) {
        distanceToPartyLocation = Coordinate.getDistance(getKitchen().coordinate, partyLocation);
    }

    public double getDistanceToPartyLocation() {
        if (distanceToPartyLocation == null) {
            throw new IllegalStateException("Distance to party location isn't set");
        }
        return distanceToPartyLocation;
    }

    public boolean containsPerson(Person person) {
        return getPersonA().equals(person) || getPersonB().equals(person);
    }

    @Override
    public int compareTo(PairMatched o) {
        return Double.compare(this.getDistanceToPartyLocation(), o.getDistanceToPartyLocation());
    }

    @Override
    public String toString() {
        return getPersonA() + "\n" +
                getPersonB() + "\n" +
                getFoodPreference() +
                getKitchen();
    }
}
