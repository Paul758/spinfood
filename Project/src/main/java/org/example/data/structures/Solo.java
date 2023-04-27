package org.example.data.structures;


import org.example.data.enums.FoodPreference;
import org.example.data.factory.IData;
import org.example.data.factory.Kitchen;
import org.example.data.factory.Person;

public class Solo extends EventParticipant{

    public final Person person;

    public Solo(IData person, FoodPreference foodPreference, IData kitchen){
        super.foodPreference = foodPreference;
        super.kitchen = (Kitchen) kitchen;
        this.person = (Person) person;
    }

    @Override
    public String toString() {
        return "Solo " + person.toString() + super.toString();
    }
}
