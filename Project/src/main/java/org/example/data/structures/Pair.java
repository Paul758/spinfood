package org.example.data.structures;


import org.example.data.enums.FoodPreference;
import org.example.data.enums.KitchenType;
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

    public final Solo soloA;
    public final Solo soloB;

    public Pair(Person personA, Person personB, FoodPreference foodPreference, Kitchen kitchen) {
        this.soloA = new Solo(personA,foodPreference, kitchen);
        this.soloB = new Solo(personB, foodPreference, new Kitchen(KitchenType.NO,-1,-1,-1));
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
        return this.soloA.equals(pair.soloA) && this.soloB.equals(pair.soloB)
                || this.soloA.equals(pair.soloB) && this.soloB.equals(pair.soloA);
    }

    @Override
    public String toString() {
        return  "Pair first person information: " + soloA.toString() + ", second person information: " + soloB.toString() + super.toString();
    }
}
