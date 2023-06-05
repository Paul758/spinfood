package org.example.data.structures;


import org.example.data.enums.FoodPreference;
import org.example.data.factory.Kitchen;

/**
 * Abstract class for a participant of an event. Contains a food preference and a kitchen.
 * To be implemented as a Solo or Pair.
 * @author David Riemer
 * @version 1.0
 */
public abstract class EventParticipant {

    public FoodPreference foodPreference;
    public Kitchen kitchen;

    public FoodPreference getFoodPreference() {
        return foodPreference;
    }

    public Kitchen getKitchen() {
        return kitchen;
    }

    @Override
    public String toString() {
        return "foodPreference = " + foodPreference + " " + kitchen;
    }
}
