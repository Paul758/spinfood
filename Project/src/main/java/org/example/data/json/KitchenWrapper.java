package org.example.data.json;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.example.data.enums.KitchenType;
import org.example.data.factory.Kitchen;

@JsonPropertyOrder({"emergencyKitchen", "story", "longitude", "latitude"})
public class KitchenWrapper {

    @JsonIgnore
    Kitchen kitchen;

    public KitchenWrapper(Kitchen kitchen){
        this.kitchen = kitchen;
    }

    @JsonGetter
    public boolean getEmergencyKitchen(){
        return kitchen.getKitchenType().equals(KitchenType.MAYBE);
    }

    @JsonGetter
    public int getStory(){
        return kitchen.story;
    }

    @JsonGetter
    public double getLongitude(){
        return kitchen.coordinate.longitude;
    }

    @JsonGetter
    public double getLatitude(){
        return kitchen.coordinate.latitude;
    }
}
