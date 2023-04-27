package org.example.data.structures;


import org.example.data.enums.FoodPreference;
import org.example.data.factory.IData;
import org.example.data.factory.Kitchen;
import org.example.data.factory.Person;

public class Pair extends EventParticipant{

    public final Person personA;
    public final Person personB;

    public Pair(IData personA, IData personB, FoodPreference foodPreference, IData kitchen){
      this.personA = (Person) personA;
      this.personB = (Person) personB;
      super.foodPreference = foodPreference;
      super.kitchen = (Kitchen) kitchen;
    }

    @Override
    public String toString() {
        return  "Pair first person information=" + personA.toString() + ", second person information =" + personB.toString() + super.toString();
    }
}
