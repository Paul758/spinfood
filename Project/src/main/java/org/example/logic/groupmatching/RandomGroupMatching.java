package org.example.logic.groupmatching;

import org.example.logic.tools.GroupMatched;
import org.example.logic.tools.MealType;
import org.example.logic.tools.PairMatched;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomGroupMatching {
    public static List<GroupMatched> match(List<PairMatched> pairs) {
        List<GroupMatched> groups = new ArrayList<>();
        int newLength = pairs.size() - (pairs.size() % 9);
        pairs = pairs.subList(0, newLength);

        List<List<PairMatched>> superGroups = createSuperGroups(pairs);
        System.out.println(superGroups.size());
        superGroups.forEach(l -> System.out.println(l.size()));
        for (List<PairMatched> superGroup : superGroups) {
            List<GroupMatched> groupMatchedList = createGroups(superGroup);
            groups.addAll(groupMatchedList);
        }

        return groups;
    }

    private static List<List<PairMatched>> createSuperGroups(List<PairMatched> pairs) {
        List<List<PairMatched>> superGroups = new ArrayList<>();
        Random random = new Random();
        int pairsInSuperGroup = 9;
        int groupCount = pairs.size() / 9;

        for (int i = 0; i < groupCount; i++) {
            List<PairMatched> superGroup = new ArrayList<>();

            for (int j = 0; j < pairsInSuperGroup; j++) {
                int index = random.nextInt(pairs.size());
                PairMatched pair = pairs.get(index);
                superGroup.add(pair);
                pairs.remove(pair);
            }
            superGroups.add(superGroup);
        }
        return superGroups;
    }

    private static List<GroupMatched> createGroups(List<PairMatched> pairs) {
        if (pairs.size() != 9) throw new IllegalStateException("Supergroup must have exactly 9 pairs");

        List<GroupMatched> groups = new ArrayList<>();

        groups.add(new GroupMatched(pairs.get(0), pairs.get(1), pairs.get(2), MealType.STARTER));
        groups.add(new GroupMatched(pairs.get(5), pairs.get(3), pairs.get(4), MealType.STARTER));
        groups.add(new GroupMatched(pairs.get(8), pairs.get(7), pairs.get(6), MealType.STARTER));

        groups.add(new GroupMatched(pairs.get(3), pairs.get(0), pairs.get(6), MealType.MAIN));
        groups.add(new GroupMatched(pairs.get(1), pairs.get(4), pairs.get(7), MealType.MAIN));
        groups.add(new GroupMatched(pairs.get(2), pairs.get(5), pairs.get(8), MealType.MAIN));

        groups.add(new GroupMatched(pairs.get(4), pairs.get(0), pairs.get(8), MealType.DESSERT));
        groups.add(new GroupMatched(pairs.get(6), pairs.get(5), pairs.get(1), MealType.DESSERT));
        groups.add(new GroupMatched(pairs.get(7), pairs.get(3), pairs.get(2), MealType.DESSERT));

        return groups;
    }
}
