package org.example.data.factory;

import org.example.data.enums.KitchenType;
import org.example.data.tools.Keywords;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


/** Class to create the kitchen data objects.
 *
 */
public class KitchenFactory {
    /**
     * @param values Collection of values that are written in a single row in the .csv file
     * @param keyWordMap maps the column header keywords of the .csv file to integer indices. Is used to read the values
     * @return Returns a class that holds the values of a kitchen
     */
    public static Kitchen createKitchen(Collection<String> values, Map<String, Integer> keyWordMap) {
        ArrayList<String> data = new ArrayList<>(values);

        int story;
        double longitude;
        double latitude;

        //KitchenType
        KitchenType kitchenType = KitchenType.parseKitchenType(data.get(keyWordMap.get(Keywords.kitchen)));

        //story
        if (data.get(keyWordMap.get(Keywords.kitchenStory)).equals("")) {
            story = 0;
        } else {
            story = Math.round(Float.parseFloat(data.get(keyWordMap.get(Keywords.kitchenStory))));
        }

        //coordinates
        if (kitchenType == KitchenType.NO) {
            longitude = 0;
            latitude = 0;
        } else {
            longitude = Double.parseDouble(data.get(keyWordMap.get(Keywords.kitchenLongitude)));
            latitude = Double.parseDouble(data.get(keyWordMap.get(Keywords.kitchenLatitude)));
        }

        return new Kitchen(kitchenType, story, longitude, latitude);
    }
}
