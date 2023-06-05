package org.example.data.structures;


import org.example.data.enums.FoodPreference;
import org.example.data.factory.Kitchen;
import org.example.data.factory.Person;
import org.example.logic.structures.PairMatched;

/**
 * Data holding class for a single person. Contains a person and their food preference and kitchen.
 *
 */
public class Solo extends EventParticipant{

    public final Person person;

    public PairMatched pair;

    public Solo(Person person, FoodPreference foodPreference, Kitchen kitchen){
        super.foodPreference = foodPreference;
        super.kitchen = kitchen;
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        Solo solo = (Solo) obj;
        return this.person.equals(solo.person);
    }

    @Override
    public String toString() {
        return "Solo " + person.toString() + super.toString();
    }
}
