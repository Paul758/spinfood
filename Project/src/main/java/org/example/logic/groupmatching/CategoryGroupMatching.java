package org.example.logic.groupmatching;

import org.example.data.enums.FoodPreference;
import org.example.data.factory.Kitchen;
import org.example.logic.structures.GroupMatched;
import org.example.logic.structures.PairMatched;
import org.example.logic.tools.algorithms.KMeansSameSize;
import org.example.logic.tools.algorithms.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryGroupMatching {
    public static List<GroupMatched> match(List<PairMatched> pairs) {
        List<List<PairMatched>> pairsWithSameKitchen = getPairsWithSameKitchen(pairs);

        List<PairMatched> anyMeal = new ArrayList<>(pairs);
        List<PairMatched> starter = new ArrayList<>();
        List<PairMatched> main = new ArrayList<>();
        List<PairMatched> dessert = new ArrayList<>();

        for (List<PairMatched> list : pairsWithSameKitchen) {
            for (int i = 0; i < list.size(); i++) {
                PairMatched pair = list.get(i);
                anyMeal.remove(pair);
                if (i == 0) {
                    starter.add(pair);
                } else if (i == 1) {
                    main.add(pair);
                } else if (i == 2) {
                    dessert.add(pair);
                } else {
                    pairs.remove(pair);
                }
            }
        }

        List<PairMatched> none = new ArrayList<>(GroupMatchingTools.getPreferenceList(pairs, FoodPreference.NONE));
        List<PairMatched> meat = new ArrayList<>(GroupMatchingTools.getPreferenceList(pairs, FoodPreference.MEAT));
        List<PairMatched> veggie = new ArrayList<>(GroupMatchingTools.getPreferenceList(pairs, FoodPreference.VEGGIE));
        List<PairMatched> vegan = new ArrayList<>(GroupMatchingTools.getPreferenceList(pairs, FoodPreference.VEGAN));


        System.out.println(pairs.size());
        System.out.println(anyMeal.size());
        System.out.println(starter.size());
        System.out.println(main.size());
        System.out.println(dessert.size());
        System.out.println("none: " + none.size());
        System.out.println("meat: " + meat.size());
        System.out.println("veggie: " + veggie.size());
        System.out.println("vegan: " + vegan.size());

        GroupMatchingTools.fillUpList(veggie, none, 9);
        GroupMatchingTools.fillUpList(vegan, none, 9);
        meat.addAll(none);
        none.removeAll(meat);
        cutList(meat);
        cutList(veggie);
        cutList(vegan);

        System.out.println("none: " + none.size());
        System.out.println("meat: " + meat.size());
        System.out.println("veggie: " + veggie.size());
        System.out.println("vegan: " + vegan.size());

        List<Point> meatPoints = meat.stream()
                .map(p -> new Point(p, p.getKitchen().coordinate.longitude, p.getKitchen().coordinate.latitude))
                .toList();

        meatPoints.forEach(System.out::println);
        List<List<Point>> resultMeat = KMeansSameSize.getCluster(new ArrayList<>(meatPoints), 9);

        System.out.println("--------------------------------------------");
        System.out.println("result meat");
        for (int i = 0; i < resultMeat.size(); i++) {
            for (Point point : resultMeat.get(i)) {
                System.out.println(point.x + ", " + point.y + ", " + i);
            }
        }

        return null;
    }

    public static List<GroupMatched> createGroups(List<PairMatched> pairs) {
        return null;
    }



    private static void cutList(List<PairMatched> list) {
        int excessiveElements = list.size() % 9;
        for (int i = 0; i < excessiveElements; i++) {
            list.remove(list.size() - 1 - i);
        }
    }

    private static List<List<PairMatched>> getPairsWithSameKitchen(List<PairMatched> pairs) {
        Map<Kitchen, Integer> kitchenCountMap = new HashMap<>();
        Map<Kitchen, List<PairMatched>> sameKitchenMap = new HashMap<>();
        List<List<PairMatched>> pairsWithSameKitchen = new ArrayList<>();

        // counts how many pairs have the same kitchen
        for (PairMatched pair : pairs) {
            Kitchen kitchen = pair.getKitchen();
            if (!kitchenCountMap.containsKey(kitchen)) {
                kitchenCountMap.put(kitchen, 0);
            } else {
                Integer count = kitchenCountMap.get(kitchen);
                kitchenCountMap.put(kitchen, count + 1);
            }
        }

        // groups all pairs with the same kitchen
        for (PairMatched pair : pairs) {
            Kitchen kitchen = pair.getKitchen();
            if (kitchenCountMap.get(kitchen) > 1) {
                if (!sameKitchenMap.containsKey(kitchen)) {
                    sameKitchenMap.put(kitchen, new ArrayList<>());
                }
                sameKitchenMap.get(kitchen).add(pair);
            }
        }

        for (Kitchen kitchen : sameKitchenMap.keySet()) {
            pairsWithSameKitchen.add(sameKitchenMap.get(kitchen));
        }

        return pairsWithSameKitchen;
    }
}
