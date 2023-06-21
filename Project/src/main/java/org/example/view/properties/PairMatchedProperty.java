package org.example.view.properties;

import org.example.logic.metrics.PairMetrics;
import org.example.logic.structures.PairMatched;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class PairMatchedProperty {
    private final PairMatched pairMatched;

    public PairMatchedProperty(PairMatched pairMatched) {
        this.pairMatched = pairMatched;
    }

    public static LinkedHashMap<String, List<String>> getColumnNames() {
        List<String> soloA = List.of("genderA", "ageA", "foodPreferenceA");
        List<String> soloB = List.of("genderB", "ageB", "foodPreferenceB");
        List<String> pairData = List.of("preMatched");
        List<String> metrics = List.of("ageDifference", "genderDiversity", "preferenceDeviation");

        LinkedHashMap<String, List<String>> values = new LinkedHashMap<>();
        values.put("soloA", soloA);
        values.put("soloB", soloB);
        values.put("pairData", pairData);
        values.put("metrics", metrics);
        return values;
    }

    public PairMatched getPairMatched() {
        return pairMatched;
    }

    public String getAgeA() {
        return  String.valueOf(pairMatched.getSoloA().getPerson().age());
    }

    public String getGenderA() {
        return pairMatched.getSoloA().getPerson().sex().toString();
    }

    public String getFoodPreferenceA() {
        return pairMatched.getSoloA().getFoodPreference().toString();
    }

    public String getAgeB() {
        return  String.valueOf(pairMatched.getSoloB().getPerson().age());
    }

    public String getGenderB() {
        return pairMatched.getSoloB().getPerson().sex().toString();
    }

    public String getFoodPreferenceB() {
        return pairMatched.getSoloB().getFoodPreference().toString();
    }

    public String getPreMatched() {
        return String.valueOf(pairMatched.isPreMatched());
    }

    public String getAgeDifference() {
        return String.valueOf(PairMetrics.calcAgeDifference(pairMatched));
    }

    public String getGenderDiversity() {
        return String.valueOf(PairMetrics.calcGenderDiversity(pairMatched));
    }

    public String getPreferenceDeviation() {
        return String.valueOf(PairMetrics.calcPreferenceDeviation(pairMatched));
    }
}
