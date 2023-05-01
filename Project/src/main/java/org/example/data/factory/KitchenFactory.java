package org.example.data.factory;

import org.example.data.enums.KitchenType;
import org.example.data.tools.CSVReader;
import org.example.data.tools.Keywords;

import java.util.ArrayList;
import java.util.Collection;


/** Class to create the kitchen data objects
 *
 */
public class KitchenFactory{

    public static Kitchen createKitchen(Collection<String> values) {
        ArrayList<String> data = new ArrayList<>(values);

        int story;
        float longitude;
        float latitude;

        //KitchenType
        KitchenType kitchenType = KitchenType.parseKitchenType(data.get(CSVReader.keyWordMap.get(Keywords.kitchen)));

        //story
        if(data.get(CSVReader.keyWordMap.get(Keywords.kitchenStory)).equals("")){
            story = 0;
        } else {
            story = Math.round(Float.parseFloat(data.get(CSVReader.keyWordMap.get(Keywords.kitchenStory))));
        }

        //coordinates
        if (kitchenType == KitchenType.NO) {
            longitude = 0;
            latitude = 0;
        } else {
            longitude = Float.parseFloat(data.get(CSVReader.keyWordMap.get(Keywords.kitchenLongitude)));
            latitude = Float.parseFloat(data.get(CSVReader.keyWordMap.get(Keywords.kitchenLatitude)));
        }

        return new Kitchen(kitchenType, story, longitude, latitude);
    }
}
