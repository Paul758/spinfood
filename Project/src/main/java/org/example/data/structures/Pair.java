package org.example.data.structures;


import org.example.data.enums.FoodPreference;
import org.example.data.factory.Kitchen;
import org.example.data.factory.Person;

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
    public String toString() {
        return  "Pair first person information: " + personA.toString() + ", second person information: " + personB.toString() + super.toString();
    }
}
