package org.example;

import org.example.data.*;
import org.example.data.structures.Pair;
import org.example.logic.groupmatching.GroupMatchingAlgorithm;
import org.example.logic.tools.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        test4();
    }

    private static void test4() {
        String fileToRead = "src/main/java/org/example/artifacts/teilnehmerliste.csv";
        DataManagement dataManagement = new DataManagement(fileToRead);
        List<PairMatched> pairMatchedList = PairMatchingAlgorithm.match(dataManagement.soloParticipants);
        List<Match> pairs = new ArrayList<>(pairMatchedList);
        for (Pair pair : dataManagement.pairParticipants) {
            pairs.add(new PairRegistered(pair));
        }

        Coordinate partyLocation = new Coordinate(8.6746166676233,50.5909317660173);
        int counter = 0;
        for (Match pair : pairs) {
            counter++;
            pair.setDistanceToPartyLocation(partyLocation);
            System.out.println(pair.getDistanceToPartyLocation());
        }
        System.out.println("Solo: " + dataManagement.soloParticipants.size());
        System.out.println("Pair: " + dataManagement.pairParticipants.size());
        System.out.println(counter);

        pairs.sort(Comparator.naturalOrder());
        pairs.forEach(p -> System.out.println(p.getDistanceToPartyLocation()));

        GroupMatchingAlgorithm.match(pairs);
    }


    private static void test1() {
        String fileToRead = "src/main/java/org/example/artifacts/solo-2000.csv";
        DataManagement dataManagement = new DataManagement(fileToRead);

        System.out.println("Start matching");
        List<PairMatched> pairMatchedList = PairMatchingAlgorithm.match(dataManagement.soloParticipants);

        System.out.println("Start benchmark");
        System.out.println("Priority: high count");
        Benchmark.matchedPairsBenchmark(pairMatchedList, dataManagement.soloParticipants);
        System.out.println();
    }

    private static void test2() {
        String fileToRead = "src/main/java/org/example/artifacts/solo-10.csv";
        DataManagement dataManagement = new DataManagement(fileToRead);

        List<PairMatched> pairMatchedList = PairMatchingAlgorithm.match(dataManagement.soloParticipants);

        for (PairMatched pairMatched : pairMatchedList) {
            System.out.println(pairMatched);
            System.out.println();
        }

        System.out.println("Priority: high count");
        Benchmark.matchedPairsBenchmark(pairMatchedList, dataManagement.soloParticipants);
        System.out.println();
    }


    private static void test3() {
        String fileToRead = "src/main/java/org/example/artifacts/teilnehmerliste.csv";
        DataManagement dataManagement = new DataManagement(fileToRead);
        List<PairMatched> pairMatchedList = PairMatchingAlgorithm.match(dataManagement.soloParticipants);

        System.out.println("None");
        Benchmark.matchedPairsBenchmark(pairMatchedList, dataManagement.soloParticipants);
        System.out.println();

        CostCoefficients coefficients = new CostCoefficients(10,1,1,1);
        List<PairMatched> pairMatchedList2 = PairMatchingAlgorithm.match(dataManagement.soloParticipants, coefficients, 0.5f);

        System.out.println("Priority: age");

        Benchmark.matchedPairsBenchmark(pairMatchedList2, dataManagement.soloParticipants);
        System.out.println();

        List<PairMatched> pairMatchedList3 = PairMatchingAlgorithm.match(dataManagement.soloParticipants);

        System.out.println("Priority: low count");
        Benchmark.matchedPairsBenchmark(pairMatchedList3, dataManagement.soloParticipants);
        System.out.println();

        List<PairMatched> pairMatchedList4 = PairMatchingAlgorithm.match(dataManagement.soloParticipants);

        System.out.println("Priority: low count, age");
        Benchmark.matchedPairsBenchmark(pairMatchedList4, dataManagement.soloParticipants);
        System.out.println();
    }
}