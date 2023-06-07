package org.example.data.jsonwrapper;

import org.example.data.enums.FoodPreference;
import org.example.data.enums.Sex;
import org.example.data.structures.Solo;

public class ParticipantWrapper {
    String id;
    String name;
    FoodPreference foodPreference;
    int age;
    Sex gender;
    
    public ParticipantWrapper(Solo solo) {
        id = solo.getPerson().id();
        name = solo.getPerson().name();
        foodPreference = solo.foodPreference;
        age = solo.getPerson().age();
        gender = solo.getPerson().sex();
    }
}
