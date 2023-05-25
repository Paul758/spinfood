package org.example.logic.structures;

import org.example.data.Coordinate;
import org.example.data.enums.FoodPreference;
import org.example.data.enums.KitchenType;
import org.example.data.factory.Kitchen;
import org.example.data.structures.Pair;
import org.example.data.structures.Solo;
import org.example.logic.structures.Match;
import org.example.logic.tools.MatchingTools;

public class PairMatched extends Match {

    public Solo soloA;
    public Solo soloB;
    public FoodPreference foodPreference;
    public double distanceToPartyLocation;

    public int foodPreferenceDeviation;
    public int ageRangeDeviation;

    public boolean prematched;

    public PairMatched(Pair pair){

        this(   new Solo(pair.personA, pair.foodPreference, pair.kitchen),
                new Solo(pair.personB, pair.foodPreference, pair.kitchen));
        System.out.println("This constructor is called");
        prematched = true;
    }

    public PairMatched(Solo soloA, Solo soloB){
        System.out.println("This constructor is called");
        this.soloA = soloA;
        this.soloB = soloB;
        this.foodPreference = calculateFoodPreference();
        //this.foodPreference = foodPreference;

        int foodValueA = MatchingTools.getFoodPreference(soloA.foodPreference);
        int foodValueB = MatchingTools.getFoodPreference(soloB.foodPreference);

        foodPreferenceDeviation = Math.abs(foodValueA - foodValueB);
        //this.prematched = prematched;
    }

    @Override
    public FoodPreference calculateFoodPreference() {

        int foodValueA = MatchingTools.getFoodPreference(soloA.foodPreference);
        int foodValueB = MatchingTools.getFoodPreference(soloB.foodPreference);
        System.out.println("Setting foodPreference of pair to " + FoodPreference.parseFoodPreference(Math.max(foodValueA, foodValueB)));
        return FoodPreference.parseFoodPreference(Math.max(foodValueA, foodValueB));
    }

    @Override
    public int calculateAgeRangeDeviation() {
        int ageValueA = MatchingTools.getAgeRange(soloA.person.age());
        int ageValueB = MatchingTools.getAgeRange(soloB.person.age());
        return  Math.abs(ageValueA - ageValueB);
    }

    @Override
    public float calculateSexDeviation() {
        if(soloA.person.sex().equals(soloB.person.sex())){
            return 0f;
        } else {
            return 0.5f;
        }
    }

    @Override
    public int calculateFoodPreferenceDeviation() {
        return Math.abs(MatchingTools.getFoodPreference(this.foodPreference) - MatchingTools.getFoodPreference(soloA.foodPreference))
                + Math.abs(MatchingTools.getFoodPreference(this.foodPreference) - MatchingTools.getFoodPreference(soloA.foodPreference));
    }

    public Kitchen getKitchen(){
        if(!soloA.kitchen.kitchenType.equals(KitchenType.NO)){
            return soloA.kitchen;
        } else {
            return soloB.kitchen;
        }

    }

    public void setDistanceToPartyLocation(Coordinate partyLocation) {
        System.out.println("party Location " + partyLocation);
        this.distanceToPartyLocation = Coordinate.getDistance(partyLocation, getKitchen().coordinate);
    }

    @Override
    public String toString() {
        return soloA.toString() + "\n" + soloB.toString();
    }
}
