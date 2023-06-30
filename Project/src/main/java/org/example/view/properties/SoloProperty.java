package org.example.view.properties;

import javafx.scene.image.ImageView;
import org.example.data.structures.Solo;
import org.example.logic.metrics.MetricTools;
import org.example.view.tools.ViewTools;

import java.util.LinkedHashMap;
import java.util.List;

@SuppressWarnings("unused")
public record SoloProperty(Solo solo) {

    public static LinkedHashMap<String, List<Entry>> getDetailViewColumns() {
        List<Entry> data = List.of(
                new Entry("name"),
                new Entry("gender"),
                new Entry("age"),
                new Entry("foodPreferenceIcon"),
                new Entry("kitchenType"),
                new Entry("kitchenStory"),
                new Entry("longitude"),
                new Entry("latitude")
        );

        LinkedHashMap<String, List<Entry>> map = new LinkedHashMap<>();
        map.put("", data);
        return map;
    }

    public static LinkedHashMap<String, List<Entry>> getSummaryViewColumns() {
        List<Entry> data = List.of(
                new Entry("gender"),
                new Entry("age"),
                new Entry("foodPreferenceIcon"),
                new Entry("kitchenType"));

        LinkedHashMap<String, List<Entry>> map = new LinkedHashMap<>();
        map.put("", data);
        return map;
    }

    public String getName() {
        return solo.getPerson().name();
    }

    public String getGender() {
        return solo.getPerson().sex().toString();
    }

    public String getAge() {
        return String.valueOf(solo.getPerson().age());
    }

    public String getFoodPreference() {
        return solo.getFoodPreference().toString();
    }

    public ImageView getFoodPreferenceIcon() {
        return ViewTools.getFoodPreferenceIcon(solo.getFoodPreference());
    }

    public String getKitchenType() {
        return solo.getKitchen().getKitchenType().toString();
    }

    public String getKitchenStory() {
        return String.valueOf(solo.getKitchen().story);
    }

    public String getLongitude() {
        return String.valueOf(MetricTools.round(solo.getKitchen().coordinate.longitude, 4));
    }

    public String getLatitude() {
        return String.valueOf(MetricTools.round(solo.getKitchen().coordinate.latitude, 4));
    }
}
