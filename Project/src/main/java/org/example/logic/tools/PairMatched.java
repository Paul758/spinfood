package org.example.logic.tools;

import org.example.data.enums.FoodPreference;
import org.example.data.enums.KitchenType;
import org.example.data.factory.Kitchen;
import org.example.data.structures.Pair;
import org.example.data.structures.Solo;
public class PairMatched extends Match {

    Solo soloA;
    Solo soloB;
    FoodPreference foodPreference;

    int foodPreferenceDeviation;
    int ageRangeDeviation;

    boolean prematched;

    public PairMatched(Pair pair){
        this(   new Solo(pair.personA, pair.foodPreference, pair.kitchen),
                new Solo(pair.personB, pair.foodPreference, pair.kitchen));

        prematched = true;
    }

    public PairMatched(Solo soloA, Solo soloB){
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
    protected FoodPreference calculateFoodPreference() {

        int foodValueA = MatchingTools.getFoodPreference(soloA.foodPreference);
        int foodValueB = MatchingTools.getFoodPreference(soloB.foodPreference);

        return FoodPreference.parseFoodPreference(Math.max(foodValueA, foodValueB));
    }

    @Override
    protected int calculateAgeRangeDeviation() {
        int ageValueA = MatchingTools.getAgeRange(soloA.person.age());
        int ageValueB = MatchingTools.getAgeRange(soloB.person.age());
        return  Math.abs(ageValueA - ageValueB);
    }

    @Override
    protected float calculateSexDeviation() {
        if(soloA.person.sex().equals(soloB.person.sex())){
            return 0f;
        } else {
            return 0.5f;
        }
    }

    @Override
    protected int calculateFoodPreferenceDeviation() {
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

    @Override
    public String toString() {
        return soloA.toString() + "\n" + soloB.toString();
    }
}
