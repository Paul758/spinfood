package org.example.data.jsonwrapper;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.example.data.enums.KitchenType;
import org.example.data.factory.Kitchen;

@JsonRootName(value = "Kitchen")
public class KitchenWrapper {

    boolean emergencyKitchen;
    int story;
    double longitude;
    double latitude;


    public KitchenWrapper(Kitchen kitchen){
        emergencyKitchen = kitchen.getKitchenType().equals(KitchenType.MAYBE);
        story = kitchen.story;
        longitude = kitchen.coordinate.longitude;
        latitude = kitchen.coordinate.latitude;
    }

    @JsonGetter
    public boolean getEmergencyKitchen(){
        return emergencyKitchen;
    }

    @JsonGetter
    public int getStory(){
        return story;
    }

    @JsonGetter
    public double getLongitude(){
        return longitude;
    }

    @JsonGetter
    public double getLatitude(){
        return latitude;
    }


}
