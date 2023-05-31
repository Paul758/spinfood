package org.example.logic.groupmatching;

import org.example.data.enums.FoodPreference;
import org.example.logic.structures.PairMatched;

import java.util.ArrayList;
import java.util.List;

public class GroupMatchingTools {
    public static List<PairMatched> getPreferenceList(List<PairMatched> pairs, FoodPreference preference) {
        return pairs.stream()
                .filter(p -> p.getFoodPreference().equals(preference))
                .toList();
    }

    public static void fillUpList(List<PairMatched> target, List<PairMatched> source, int multiple) {
        int neededElements = multiple - (target.size() % multiple);
        System.out.println("neededElements " + neededElements);
        if (source.size() >= neededElements) {
            for (int i = 0; i < neededElements; i++) {
                PairMatched pair = source.get(i);
                source.remove(pair);
                target.add(pair);
            }
        }
    }


}
