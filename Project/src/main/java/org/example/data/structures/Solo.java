package org.example.data.structures;


import org.example.data.enums.FoodPreference;
import org.example.data.factory.Kitchen;
import org.example.data.factory.Person;

public class Solo extends EventParticipant{

    public final Person person;

    public Solo(Person person, FoodPreference foodPreference, Kitchen kitchen){
        super.foodPreference = foodPreference;
        super.kitchen = kitchen;
        this.person = person;
    }

    @Override
    public String toString() {
        return "Solo " + person.toString() + super.toString();
    }
}
