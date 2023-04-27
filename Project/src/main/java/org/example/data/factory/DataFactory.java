package org.example.data.factory;


import org.example.data.enums.FoodPreference;
import org.example.data.structures.EventParticipant;
import org.example.data.structures.Pair;
import org.example.data.structures.Solo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataFactory {

    public EventParticipant createDataFromLine(Collection<String> values) {
        ArrayList<String> data = new ArrayList<>(values);

        String foodPreferenceValue = data.get(2);
        FoodPreference foodPreference = FoodPreference.parseFoodPreference(foodPreferenceValue);
        data.remove(2);

        Collection<String> firstPersonValues = data.subList(0, 4);
        Collection<String> kitchenValues = data.subList(4, 8);
        Collection<String> secondPersonValues = data.subList(8, 12);


        IData person = PersonFactory.getInstance().createDataObject(firstPersonValues);
        IData kitchen = KitchenFactory.getInstance().createDataObject(kitchenValues);

        EventParticipant participant;
        if (isPairRegistration(data)){
            IData secondPerson = PersonFactory.getInstance().createDataObject(secondPersonValues);
            participant = new Pair(person, secondPerson, foodPreference, kitchen);
        } else {
            participant = new Solo(person, foodPreference, kitchen);
        }


        return participant;
    }

    private boolean isPairRegistration(ArrayList<String> data) {
        return !data.get(8).equals("");
    }

    public IData createDataObject(Collection<String> data){
        throw new RuntimeException("this method shouldn't be called");
    }
}
