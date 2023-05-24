package org.example.logic.structures;

import org.example.data.enums.FoodPreference;
import org.example.data.structures.EventParticipant;
import org.example.data.structures.Solo;

public abstract class Match {

    public abstract FoodPreference calculateFoodPreference();

    public abstract int calculateAgeRangeDeviation();

    public abstract float calculateSexDeviation();

    public abstract int calculateFoodPreferenceDeviation();

}
