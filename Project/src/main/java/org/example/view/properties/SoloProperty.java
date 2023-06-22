package org.example.view.properties;

import javafx.scene.image.ImageView;
import org.example.data.enums.FoodPreference;
import org.example.data.structures.Solo;
import org.example.view.tools.ViewTools;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class SoloProperty {
    private Solo solo;
    private final String gender;
    private final String age;
    private final FoodPreference foodPreference;
    private final String kitchenType;

    public SoloProperty(Solo solo) {
        this.solo = solo;
        this.gender = solo.getPerson().sex().toString();
        this.age = String.valueOf(solo.getPerson().age());
        this.foodPreference = solo.getFoodPreference();
        this.kitchenType = solo.kitchen.getKitchenType().toString();
    }

    public static LinkedHashMap<String, List<Entry>> getColumnNames() {
        List<Entry> data = List.of(
                new Entry("gender"),
                new Entry("age"),
                new Entry("foodPreference"),
                new Entry("foodPreferenceIcon"),
                new Entry("kitchenType"));

        LinkedHashMap<String, List<Entry>> map = new LinkedHashMap<>();
        map.put("", data);
        return map;
    }

    public Solo getSolo() {
        return solo;
    }
    public String getGender() {
        return gender;
    }

    public String getAge() {
        return age;
    }

    public String getFoodPreference() {
        return foodPreference.toString();
    }

    public ImageView getFoodPreferenceIcon() {
        return ViewTools.getFoodPreferenceIcon(foodPreference);
    }

    public String getKitchenType() {
        return kitchenType;
    }
}
