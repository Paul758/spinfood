package org.example.view.properties;

import javafx.scene.image.ImageView;
import org.example.logic.metrics.GroupMetrics;
import org.example.logic.metrics.MetricTools;
import org.example.logic.structures.GroupMatched;
import org.example.view.tools.ViewTools;

import java.util.LinkedHashMap;
import java.util.List;

@SuppressWarnings("unused")
public record GroupMatchedProperty(GroupMatched groupMatched) {

    private static final int DECIMAL_PLACES = 4;

    public static LinkedHashMap<String, List<Entry>> getColumnNames() {
        List<Entry> cook = List.of(
                new Entry("genderCook"),
                new Entry("ageCook"),
                new Entry(EntryType.IMAGE, "foodPreferenceCookIcon")
        );

        List<Entry> pairA = List.of(
                new Entry("genderA"),
                new Entry("ageA"),
                new Entry(EntryType.IMAGE, "foodPreferenceAIcon")
        );

        List<Entry> pairB = List.of(
                new Entry("genderB"),
                new Entry("ageB"),
                new Entry(EntryType.IMAGE, "foodPreferenceBIcon")
        );

        List<Entry> data = List.of(
                new Entry("mealType"),
                new Entry("foodPreferenceIcon")
        );

        List<Entry> metrics = List.of(
                new Entry("ageDifference"),
                new Entry("genderDiversity"),
                new Entry("preferenceDeviation"),
                new Entry("isValid")
        );

        LinkedHashMap<String, List<Entry>> map = new LinkedHashMap<>();
        map.put("cook", cook);
        map.put("pairA", pairA);
        map.put("pairB", pairB);
        map.put("", data);
        map.put("metrics", metrics);
        return map;
    }

    public String getGenderCook() {
        return groupMatched.getCook().getSoloA().getPerson().sex()
                + ", "
                + groupMatched.getCook().getSoloB().getPerson().sex();
    }

    public String getGenderA() {
        return groupMatched.getPairA().getSoloA().getPerson().sex()
                + ", "
                + groupMatched.getPairA().getSoloB().getPerson().sex();
    }

    public String getGenderB() {
        return groupMatched.getPairB().getSoloA().getPerson().sex()
                + ", "
                + groupMatched.getPairB().getSoloB().getPerson().sex();
    }

    public String getAgeCook() {
        return groupMatched.getCook().getSoloA().getPerson().age()
                + ", "
                + groupMatched.getCook().getSoloB().getPerson().age();
    }

    public String getAgeA() {
        return groupMatched.getPairA().getSoloA().getPerson().age()
                + ", "
                + groupMatched.getPairA().getSoloB().getPerson().age();
    }

    public String getAgeB() {
        return groupMatched.getPairB().getSoloA().getPerson().age()
                + ", "
                + groupMatched.getPairB().getSoloB().getPerson().age();
    }

    public ImageView getFoodPreferenceCookIcon() {
        return ViewTools.getFoodPreferenceIcon(groupMatched.getCook().getFoodPreference());
    }

    public ImageView getFoodPreferenceAIcon() {
        return ViewTools.getFoodPreferenceIcon(groupMatched.getPairA().getFoodPreference());
    }

    public ImageView getFoodPreferenceBIcon() {
        return ViewTools.getFoodPreferenceIcon(groupMatched.getPairB().getFoodPreference());
    }

    public ImageView getFoodPreferenceIcon() {
        return ViewTools.getFoodPreferenceIcon(groupMatched.getFoodPreference());
    }

    public String getMealType() {
        return groupMatched.getMealType().toString();
    }

    public String getIsValid() {
        return String.valueOf(GroupMetrics.isValid(groupMatched));
    }

    public String getAgeDifference() {
        double ageDifference = GroupMetrics.calcAgeDifference(groupMatched);
        return String.valueOf(MetricTools.round(ageDifference, DECIMAL_PLACES));
    }

    public String getGenderDiversity() {
        double genderDiversity = GroupMetrics.calcGenderDiversity(groupMatched);
        return String.valueOf(MetricTools.round(genderDiversity, DECIMAL_PLACES));
    }

    public String getPreferenceDeviation() {
        double preferenceDeviation = GroupMetrics.calcPreferenceDeviation(groupMatched);
        return String.valueOf(MetricTools.round(preferenceDeviation, DECIMAL_PLACES));
    }
}
