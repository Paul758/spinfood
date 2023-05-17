package org.example;

import org.example.data.*;
import org.example.data.enums.KitchenType;
import org.example.data.structures.EventParticipant;
import org.example.data.structures.Pair;
import org.example.logic.tools.Benchmark;
import org.example.logic.tools.CostCoefficients;
import org.example.logic.tools.PairMatched;
import org.example.logic.tools.PairMatchingAlgorithm;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        test1();
    }



    private static void test1() {
        String fileToRead = "src/main/java/org/example/artifacts/solo-2000.csv";
        String partyLocation = "src/main/java/org/example/artifacts/partylocation.csv";
        DataManagement dataManagement = new DataManagement(fileToRead, partyLocation);

        System.out.println("Start matching");
        List<PairMatched> pairMatchedList = PairMatchingAlgorithm.match(dataManagement.soloParticipants);

        System.out.println("Start benchmark");
        System.out.println("Priority: high count");
        Benchmark.matchedPairsBenchmark(pairMatchedList, dataManagement.soloParticipants);
        System.out.println();


        for(Pair pair : dataManagement.pairParticipants) {
            pairMatchedList.add(new PairMatched(pair));
        }

        System.out.println(dataManagement.partyLocation);
    }

    private static void test2() {
        String fileToRead = "src/main/java/org/example/artifacts/solo-10.csv";
        String partyLocation = "src/main/java/org/example/artifacts/partylocation.csv";
        DataManagement dataManagement = new DataManagement(fileToRead, partyLocation);

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
        String partyLocation = "src/main/java/org/example/artifacts/partylocation.csv";
        DataManagement dataManagement = new DataManagement(fileToRead, partyLocation);
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