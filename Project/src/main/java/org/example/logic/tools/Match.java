package org.example.logic.tools;

import org.example.data.Coordinate;
import org.example.data.enums.FoodPreference;
import org.example.data.factory.Kitchen;
import org.example.data.factory.Person;

public abstract class Match implements Comparable<Match> {
    private Double distanceToPartyLocation;
    public abstract Person getPersonA();
    public abstract Person getPersonB();
    public abstract FoodPreference getFoodPreference();
    public abstract Kitchen getKitchen();
    public abstract boolean isValid();

    public boolean containsPerson(Person person) {
        return getPersonA().equals(person) || getPersonB().equals(person);
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

    @Override
    public int compareTo(Match o) {
        return Double.compare(this.getDistanceToPartyLocation(), o.distanceToPartyLocation);
    }
}
