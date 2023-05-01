package org.example.data.factory;

import org.example.data.enums.KitchenType;

import java.util.ArrayList;
import java.util.Collection;

public class KitchenFactory extends DataFactory{

    private static final KitchenFactory kitchenFactory = new KitchenFactory();
    public static KitchenFactory getInstance(){
        return kitchenFactory;
    }

    @Override
    public IData createDataObject(Collection<String> values) {
        ArrayList<String> data = new ArrayList<>(values);

        int story;
        float longitude;
        float latitude;

        //KitchenType
        KitchenType kitchenType = KitchenType.parseKitchenType(data.get(0));

        //story
        if(data.get(1).equals("")){
            story = 0;
        } else {
            story = Math.round(Float.parseFloat(data.get(1)));
        }

        //coordinates
        if(kitchenType == KitchenType.NO){
            longitude = 0;
            latitude = 0;
        } else{
            longitude = Float.parseFloat(data.get(2));
            latitude = Float.parseFloat(data.get(3));
        }

        return new Kitchen(kitchenType, story, longitude, latitude);
    }
}
