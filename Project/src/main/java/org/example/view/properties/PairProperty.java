package org.example.view.properties;

import javafx.scene.image.ImageView;
import org.example.data.structures.Pair;
import org.example.view.tools.ViewTools;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PairProperty {
    private final Pair pair;

    public PairProperty(Pair pair) {
        this.pair = pair;
    }

    public static LinkedHashMap<String, List<Entry>> getColumnNames() {
        List<Entry> soloA = List.of(
                new Entry("nameA"),
                new Entry("sexA"),
                new Entry("ageA")
        );

        List<Entry> soloB = List.of(
                new Entry("nameB"),
                new Entry("sexB"),
                new Entry("ageB")
        );

        List<Entry> pairData = List.of(
                new Entry("foodPreferenceIcon"),
                new Entry("kitchenType")
        );

        LinkedHashMap<String, List<Entry>> map = new LinkedHashMap<>();
        map.put("soloA", soloA);
        map.put("soloB", soloB);
        map.put("pairData", pairData);
        return map;
    }

    public String getNameA() {
        return pair.soloA.person.name();
    }

    public String getAgeA() {
        return  String.valueOf(pair.soloA.person.age());
    }

    public String getSexA() {
        return pair.soloA.person.sex().toString();
    }

    public String getNameB() {
        return pair.soloB.person.name();
    }

    public String getAgeB() {
        return  String.valueOf(pair.soloB.person.age());
    }

    public String getSexB() {
        return pair.soloB.person.sex().toString();
    }

    public String getFoodPreference() {
        return pair.getFoodPreference().toString();
    }
    public ImageView getFoodPreferenceIcon() {
        return ViewTools.getFoodPreferenceIcon(pair.getFoodPreference());
    }

    public String getKitchenType() {
        return pair.getKitchen().getKitchenType().toString();
    }
}
