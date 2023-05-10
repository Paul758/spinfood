package org.example.logic.tools;

import org.example.data.enums.FoodPreference;
import org.example.data.structures.EventParticipant;
import org.example.data.structures.Solo;

public abstract class Match {

     protected abstract FoodPreference calculateFoodPreference();

    protected abstract int calculateAgeRangeDeviation();

}
