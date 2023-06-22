package org.example.view.properties;

import javafx.scene.image.ImageView;
import org.example.logic.metrics.PairMetrics;
import org.example.logic.structures.PairMatched;
import org.example.view.tools.ViewTools;

import java.util.LinkedHashMap;
import java.util.List;

public record PairMatchedProperty(PairMatched pairMatched) {

    public static LinkedHashMap<String, List<Entry>> getColumnNames() {
        List<Entry> soloA = List.of(
                new Entry("genderA"),
                new Entry("ageA"),
                new Entry(EntryType.IMAGE, "foodPreferenceAIcon"));

        List<Entry> soloB = List.of(
                new Entry("genderB"),
                new Entry("ageB"),
                new Entry(EntryType.IMAGE, "foodPreferenceBIcon"));

        //List<String> pairData = List.of("preMatched");
        //List<String> metrics = List.of("ageDifference", "genderDiversity", "preferenceDeviation");

        LinkedHashMap<String, List<Entry>> values = new LinkedHashMap<>();
        values.put("soloA", soloA);
        values.put("soloB", soloB);
        //values.put("pairData", pairData);
        //values.put("metrics", metrics);
        return values;
    }

    public String getAgeA() {
        return String.valueOf(pairMatched.getSoloA().getPerson().age());
    }

    public String getGenderA() {
        return pairMatched.getSoloA().getPerson().sex().toString();
    }

    public String getFoodPreferenceA() {
        return pairMatched.getSoloA().getFoodPreference().toString();
    }

    public ImageView getFoodPreferenceAIcon() {
        return ViewTools.getFoodPreferenceIcon(pairMatched.getSoloA().getFoodPreference());
    }

    public String getAgeB() {
        return String.valueOf(pairMatched.getSoloB().getPerson().age());
    }

    public String getGenderB() {
        return pairMatched.getSoloB().getPerson().sex().toString();
    }

    public String getFoodPreferenceB() {
        return pairMatched.getSoloB().getFoodPreference().toString();
    }

    public ImageView getFoodPreferenceBIcon() {
        return ViewTools.getFoodPreferenceIcon(pairMatched.getSoloA().getFoodPreference());
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
