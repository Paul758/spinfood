package org.example.view.properties;

import javafx.scene.image.ImageView;
import org.example.logic.structures.GroupMatched;
import org.example.view.tools.ViewTools;

import java.util.LinkedHashMap;
import java.util.List;

public record GroupMatchedProperty(GroupMatched groupMatched) {

    public static LinkedHashMap<String, List<Entry>> getColumnNames() {
        List<Entry> index = List.of(new Entry(EntryType.INDEX, "#"));

        List<Entry> pairA = List.of(
                new Entry(EntryType.IMAGE, "foodPreferenceAIcon")
        );

        List<Entry> pairB = List.of(
                new Entry(EntryType.IMAGE, "foodPreferenceBIcon")
        );

        List<Entry> pairC = List.of(
                new Entry(EntryType.IMAGE, "foodPreferenceCIcon")
        );

        LinkedHashMap<String, List<Entry>> map = new LinkedHashMap<>();
        map.put("index", index);
        map.put("pairA", pairA);
        map.put("pairB", pairB);
        map.put("pairC", pairC);
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
}
