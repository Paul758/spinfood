package org.example.logic.groupmatching;

import org.example.data.Coordinate;
import org.example.logic.tools.GroupMatched;
import org.example.logic.tools.HungarianAlgorithm;
import org.example.logic.tools.Match;

import java.util.Comparator;
import java.util.List;

public class GroupMatchingAlgorithm {
    public static List<GroupMatched> match(List<Match> matchList) {
        List<Match> cutList = cutList(matchList);
        cutList.sort(Comparator.naturalOrder());

        int value = cutList.size() / 3;
        List<Match> starterList = cutList.subList(0, value);
        List<Match> mainList = cutList.subList(value, value * 2);
        List<Match> dessertList = cutList.subList(value * 2, cutList.size());

        System.out.println(cutList.size());
        System.out.println(starterList.size());
        System.out.println(mainList.size());
        System.out.println(dessertList.size());

        double[][] costs = calculateCost(mainList, starterList, value);
        int[][] assignment = HungarianAlgorithm.match(costs);

        for (int[] arr : assignment) {
            System.out.println(Coordinate.getDistance(mainList.get(arr[0]).getKitchen().coordinate,
                    starterList.get(arr[1]).getKitchen().coordinate)*1000);
        }

        return null;
    }

    private static double[][] calculateCost(List<Match> worker, List<Match> task, int length) {
        double[][] costs = new double[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; i < length; i++) {
                Coordinate c1 = worker.get(i).getKitchen().coordinate;
                Coordinate c2 = task.get(j).getKitchen().coordinate;
                costs[i][j] = Coordinate.getDistance(c1, c2);
            }
        }
        return costs;
    }

    private static List<Match> cutList(List<Match> list) {
        int newSize = list.size() - (list.size() % 9);
        return list.subList(0, newSize);
    }
}
