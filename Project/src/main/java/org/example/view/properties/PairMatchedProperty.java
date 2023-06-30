package org.example.view.properties;

import javafx.scene.image.ImageView;
import org.example.logic.metrics.PairMetrics;
import org.example.logic.structures.PairMatched;
import org.example.view.tools.ViewTools;

import java.util.LinkedHashMap;
import java.util.List;

@SuppressWarnings("unused")
public record PairMatchedProperty(PairMatched pairMatched) {

    public static LinkedHashMap<String, List<Entry>> getColumnNames() {
        List<Entry> soloA = List.of(
                new Entry("genderA"),
                new Entry("ageA"),
                new Entry(EntryType.IMAGE, "foodPreferenceAIcon")
        );

        List<Entry> soloB = List.of(
                new Entry("genderB"),
                new Entry("ageB"),
                new Entry(EntryType.IMAGE, "foodPreferenceBIcon")
        );

        List<Entry> pairData = List.of(
                new Entry("preMatched"),
                new Entry("foodPreferenceIcon")

        );

        List<Entry> metrics = List.of(
                new Entry("ageDifference"),
                new Entry("genderDiversity"),
                new Entry("preferenceDeviation"),
                new Entry("isValid")
        );

        LinkedHashMap<String, List<Entry>> values = new LinkedHashMap<>();
        values.put("soloA", soloA);
        values.put("soloB", soloB);
        values.put("", pairData);
        values.put("metrics", metrics);
        return values;
    }

    public static LinkedHashMap<String, List<Entry>> getSummaryViewColumns() {
        List<Entry> data = List.of(
                new Entry("genderCombined"),
                new Entry("ageCombined"),
                new Entry(EntryType.IMAGE, "foodPreferenceIcon")
        );

        LinkedHashMap<String, List<Entry>> values = new LinkedHashMap<>();
        values.put("", data);
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
        return ViewTools.getFoodPreferenceIcon(pairMatched.getSoloB().getFoodPreference());
    }

    public String getPreMatched() {
        return String.valueOf(pairMatched.isPreMatched());
    }

    public ImageView getFoodPreferenceIcon() {
        return ViewTools.getFoodPreferenceIcon(pairMatched.getFoodPreference());
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

    public String getIsValid() {
        return String.valueOf(PairMetrics.isValid(pairMatched));
    }

    public String getGenderCombined() {
        return pairMatched.getSoloA().getPerson().sex().toString()
                + ", "
                + pairMatched.getSoloB().getPerson().sex().toString();
    }

    public String getAgeCombined() {
        return pairMatched.getSoloA().getPerson().age()
                + ", "
                + pairMatched.getSoloA().getPerson().age();
    }
}
