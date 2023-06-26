package org.example.view.properties;

import javafx.scene.image.ImageView;
import org.example.logic.metrics.GroupMetrics;
import org.example.logic.structures.GroupMatched;
import org.example.view.tools.ViewTools;

import java.util.LinkedHashMap;
import java.util.List;

public record GroupMatchedProperty(GroupMatched groupMatched) {

    public static LinkedHashMap<String, List<Entry>> getColumnNames() {
        List<Entry> pairA = List.of(
                new Entry(EntryType.IMAGE, "foodPreferenceAIcon")
        );

        List<Entry> pairB = List.of(
                new Entry(EntryType.IMAGE, "foodPreferenceBIcon")
        );

        List<Entry> pairC = List.of(
                new Entry(EntryType.IMAGE, "foodPreferenceCIcon")
        );

        List<Entry> metrics = List.of(
                new Entry("isValid")
        );

        LinkedHashMap<String, List<Entry>> map = new LinkedHashMap<>();
        map.put("pairA", pairA);
        map.put("pairB", pairB);
        map.put("pairC", pairC);
        map.put("metrics", metrics);
        return map;
    }

    public ImageView getFoodPreferenceAIcon() {
        return ViewTools.getFoodPreferenceIcon(groupMatched.getPairList().get(0).getFoodPreference());
    }

    public ImageView getFoodPreferenceBIcon() {
        return ViewTools.getFoodPreferenceIcon(groupMatched.getPairList().get(1).getFoodPreference());
    }

    public ImageView getFoodPreferenceCIcon() {
        return ViewTools.getFoodPreferenceIcon(groupMatched.getPairList().get(2).getFoodPreference());
    }

    public String getIsValid() {
        return String.valueOf(GroupMetrics.isValid(groupMatched));
    }
}
