package org.example.logic.tools;

import org.example.data.enums.FoodPreference;
import org.example.data.factory.Person;
import org.example.data.factory.Kitchen;
import org.example.data.structures.Pair;

public class PairRegistered extends Match {
    private final Pair pair;

    public PairRegistered(Pair pair) {
        this.pair = pair;
    }

    @Override
    public Person getPersonA() {
        return pair.personA;
    }

    @Override
    public Person getPersonB() {
        return pair.personB;
    }

    @Override
    public FoodPreference getFoodPreference() {
        return pair.foodPreference;
    }

    @Override
    public Kitchen getKitchen() {
        return pair.kitchen;
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
