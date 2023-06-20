package org.example.view.properties;

import javafx.scene.image.ImageView;
import org.example.data.enums.FoodPreference;
import org.example.data.structures.Solo;
import org.example.view.tools.ViewTools;

import java.util.ArrayList;
import java.util.List;

public class SoloProperty {
    private final String gender;
    private final String age;
    private final FoodPreference foodPreference;
    private final String kitchenType;

    public SoloProperty(Solo solo) {
        this.gender = solo.getPerson().sex().toString();
        this.age = String.valueOf(solo.getPerson().age());
        this.foodPreference = solo.getFoodPreference();
        this.kitchenType = solo.kitchen.getKitchenType().toString();
    }

    public static List<String> simpleData() {
        return List.of("gender", "age", "foodPreference", "kitchenType");
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
