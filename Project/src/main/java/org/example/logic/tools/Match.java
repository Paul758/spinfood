package org.example.logic.tools;

import org.example.data.Coordinate;
import org.example.data.enums.FoodPreference;
import org.example.data.factory.Kitchen;
import org.example.data.factory.Person;

public abstract class Match implements Comparable<Match> {

    public abstract Person getPersonA();
    public abstract Person getPersonB();
    public abstract FoodPreference getFoodPreference();
    public abstract Kitchen getKitchen();
    public abstract boolean isValid();

    public boolean containsPerson(Person person) {
        return getPersonA().equals(person) || getPersonB().equals(person);
    }




}
