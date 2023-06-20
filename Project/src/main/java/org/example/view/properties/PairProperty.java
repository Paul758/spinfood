package org.example.view.properties;

import org.example.data.structures.Pair;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PairProperty {
    private final Pair pair;

    public PairProperty(Pair pair) {
        this.pair = pair;
    }

    public static LinkedHashMap<String, List<String>> getColumnNames() {
        List<String> soloA = List.of("nameA", "sexA", "ageA");
        List<String> soloB = List.of("nameA", "sexA", "ageA");
        List<String> pairData = List.of("foodPreference", "kitchenType");

        LinkedHashMap<String, List<String>> values = new LinkedHashMap<>();
        values.put("soloA", soloA);
        values.put("soloB", soloB);
        values.put("pairData", pairData);
        return values;
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

    public String getKitchenType() {
        return pair.getKitchen().getKitchenType().toString();
    }
}
