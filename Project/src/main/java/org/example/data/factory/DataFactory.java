package org.example.data.factory;


import org.example.data.enums.FoodPreference;
import org.example.data.structures.EventParticipant;
import org.example.data.structures.Pair;
import org.example.data.structures.Solo;
import org.example.data.tools.CSVReader;
import org.example.data.tools.Keywords;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Creates the event participant objects and wraps them in a higher instance class.
 */
public class DataFactory {

    /**
     * Creates a person, kitchen, and (if it exists) a partner person from the input values.
     * @param values A Collection of values from a single row of the .csv file.
     * @return EventParticipant object that is either a single unmatched person or a pair
     */
    public static EventParticipant createDataFromLine(final Collection<String> values, Map<String, Integer> keyWordMap) {
        ArrayList<String> data = new ArrayList<>(values);

        String foodPreferenceValue = data.get(keyWordMap.get(Keywords.foodPreference));
        FoodPreference foodPreference = FoodPreference.parseFoodPreference(foodPreferenceValue);

        Person person = PersonFactory.createPerson(data, keyWordMap);
        Kitchen kitchen = KitchenFactory.createKitchen(data, keyWordMap);

        EventParticipant participant;

        if (isPairRegistration(data, keyWordMap)) {
            Person secondPerson = PersonFactory.createPartner(data, keyWordMap);
            participant = new Pair(person, secondPerson, foodPreference, kitchen);
        } else {
            participant = new Solo(person, foodPreference, kitchen);
        }

        return participant;
    }

    private static boolean isPairRegistration(final ArrayList<String> data, Map<String, Integer> keyWordMap) {
        return !(data.get(keyWordMap.get(Keywords.idPartner)).equals("")
                || data.get(keyWordMap.get(Keywords.namePartner)).equals("")
                || data.get(keyWordMap.get(Keywords.agePartner)).equals("")
                || data.get(keyWordMap.get(Keywords.sexPartner)).equals(""));
    }
}
