package org.example;

import org.example.data.*;
import org.example.logic.tools.Benchmark;
import org.example.logic.tools.PairMatched;
import org.example.logic.tools.PairMatchingAlgorithm;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        String fileToRead = "src/main/java/org/example/artifacts/teilnehmerliste.csv";
        DataManagement dataManagement = new DataManagement(fileToRead);
        PairMatchingAlgorithm pairMatchingAlgorithm = new PairMatchingAlgorithm(dataManagement.soloParticipants,1f, 1f, 1f, true);
        List<PairMatched> pairMatchedList = pairMatchingAlgorithm.match();

        System.out.println("Priority: high count");
        Benchmark.matchedPairsBenchmark(pairMatchedList, dataManagement.soloParticipants);
        System.out.println();

        PairMatchingAlgorithm pairMatchingAlgorithm2 = new PairMatchingAlgorithm(dataManagement.soloParticipants, 10f, 1f, 1f, true);
        List<PairMatched> pairMatchedList2 = pairMatchingAlgorithm2.match();

        System.out.println("Priority: high count, age");
        Benchmark.matchedPairsBenchmark(pairMatchedList2, dataManagement.soloParticipants);
        System.out.println();

        PairMatchingAlgorithm pairMatchingAlgorithm3 = new PairMatchingAlgorithm(dataManagement.soloParticipants,1f, 1f, 1f, false);
        List<PairMatched> pairMatchedList3 = pairMatchingAlgorithm3.match();

        System.out.println("Priority: low count");
        Benchmark.matchedPairsBenchmark(pairMatchedList3, dataManagement.soloParticipants);
        System.out.println();

        PairMatchingAlgorithm pairMatchingAlgorithm4 = new PairMatchingAlgorithm(dataManagement.soloParticipants,5f, 1f, 1f, false);
        List<PairMatched> pairMatchedList4 = pairMatchingAlgorithm4.match();

        System.out.println("Priority: low count, age");
        Benchmark.matchedPairsBenchmark(pairMatchedList4, dataManagement.soloParticipants);
        System.out.println();
    }
}