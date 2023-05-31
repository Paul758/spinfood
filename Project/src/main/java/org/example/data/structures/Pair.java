package org.example.data.structures;


import org.example.data.enums.FoodPreference;
import org.example.data.factory.Kitchen;
import org.example.data.factory.Person;

/**
 * Data holding class for a pair of people. Contains two people and their food preference and kitchen.
 * @author David Riemer
 * @version 1.0
 * @see org.example.data.factory.Person
 * @see org.example.data.enums.FoodPreference
 * @see org.example.data.factory.Kitchen
 * @see org.example.data.structures.EventParticipant
 *
 */
public class Pair extends EventParticipant{

    public final Person personA;
    public final Person personB;

    public Pair(Person personA, Person personB, FoodPreference foodPreference, Kitchen kitchen){
      this.personA = personA;
      this.personB = personB;
      super.foodPreference = foodPreference;
      super.kitchen = kitchen;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        Pair pair = (Pair) obj;
        return this.personA.equals(pair.personA) && this.personB.equals(pair.personB)
                || this.personA.equals(pair.personB) && this.personB.equals(pair.personA);
    }

    @Override
    public String toString() {
        return  "Pair first person information: " + personA.toString() + ", second person information: " + personB.toString() + super.toString();
    }
}
