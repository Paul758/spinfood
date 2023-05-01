package org.example.data.structures;


import org.example.data.enums.FoodPreference;
import org.example.data.factory.Kitchen;

public abstract class EventParticipant {

    public FoodPreference foodPreference;
    public Kitchen kitchen;

    @Override
    public String toString() {
        return "foodPreference = " + foodPreference + " " + kitchen;
    }
}
