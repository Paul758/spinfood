package org.example.logic.tools;

import org.example.data.enums.FoodPreference;
import org.example.data.enums.KitchenType;
import org.example.data.factory.Kitchen;
import org.example.data.factory.Person;
import org.example.data.structures.Solo;
public class PairMatched extends Match {

    private final Solo soloA;
    private final Solo soloB;
    private final FoodPreference foodPreference;
    private final Kitchen kitchen;

    int foodPreferenceDeviation;
    int ageRangeDeviation;

    public PairMatched(Solo soloA, Solo soloB){
        this.soloA = soloA;
        this.soloB = soloB;
        this.foodPreference = calculateFoodPreference();
        this.kitchen = calculateKitchen();
    }

    @Override
    public Person getPersonA() {
        return soloA.person;
    }

    @Override
    public Person getPersonB() {
        return soloB.person;
    }

    @Override
    public FoodPreference getFoodPreference() {
        return foodPreference;
    }

    @Override
    public Kitchen getKitchen() {
        return kitchen;
    }

    @Override
    public boolean isValid() {
        return !soloA.person.equals(soloB.person);
    }

    protected FoodPreference calculateFoodPreference() {
        int foodValueA = MatchingTools.getFoodPreference(soloA.foodPreference);
        int foodValueB = MatchingTools.getFoodPreference(soloB.foodPreference);
        return FoodPreference.parseFoodPreference(Math.max(foodValueA, foodValueB));
    }

    private Kitchen calculateKitchen() {
        Kitchen kitchenA = soloA.kitchen;
        Kitchen kitchenB = soloB.kitchen;

        if (kitchenA.kitchenType.equals(KitchenType.YES)) return kitchenA;
        else if (kitchenB.kitchenType.equals(KitchenType.YES)) return kitchenB;
        else if (kitchenA.kitchenType.equals(KitchenType.MAYBE)) return kitchenA;
        else if (kitchenB.kitchenType.equals(KitchenType.MAYBE)) return kitchenB;
        else throw new IllegalStateException(this + " has no kitchen");
    }

    public int calculateFoodPreferenceDeviation() {
        int foodValueA = MatchingTools.getFoodPreference(soloA.foodPreference);
        int foodValueB = MatchingTools.getFoodPreference(soloB.foodPreference);
        return Math.abs(foodValueA - foodValueB);
    }




    protected int calculateAgeRangeDeviation() {
        int ageValueA = MatchingTools.getAgeRange(soloA.person.age());
        int ageValueB = MatchingTools.getAgeRange(soloB.person.age());
        return  Math.abs(ageValueA - ageValueB);
    }

    @Override
    public String toString() {
        return soloA.toString() + "\n" + soloB.toString();
    }
}
