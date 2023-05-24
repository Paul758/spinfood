package org.example;

import org.example.data.*;
import org.example.data.structures.Pair;
import org.example.data.structures.Solo;
import org.example.logic.repository.MatchingRepository;
import org.example.logic.structures.GroupMatched;
import org.example.logic.structures.PairMatched;
import org.example.logic.tools.*;
import org.example.logic.tools.algorithms.CostCoefficients;
import org.example.logic.tools.algorithms.PairMatchingAlgorithm;
import org.example.logic.tools.benchmarks.BenchmarkSystem;
import org.example.logic.tools.benchmarks.PairBenchmark;

import java.util.Collection;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //test1();
        //test2();
        testMatch();
    }

    private static void testMatch() {
        String fileToRead = "src/main/java/org/example/artifacts/teilnehmerliste.csv";
        String partyLocation = "src/main/java/org/example/artifacts/partylocation.csv";
        DataManagement dataManagement = new DataManagement(fileToRead, partyLocation);
        MatchingRepository matchingRepository = new MatchingRepository(dataManagement);
        Collection<PairMatched> matchedPairs = MatchingSystem.matchPairs((List<Solo>) matchingRepository.getSoloDataCollection());
        matchingRepository.addMatchedPairsCollection(matchedPairs);
        Collection<GroupMatched> matchedGroups = MatchingSystem.matchGroups((List<PairMatched>) matchingRepository.getMatchedPairsCollection());
        matchingRepository.addMatchedGroupsCollection(matchedGroups);

        //print results
        //pairs
        System.out.println("pair count");
        System.out.println(matchingRepository.getMatchedPairsCollection().size());

        //groups
        System.out.println("groups count");
        System.out.println(matchingRepository.getMatchedGroupsCollection().size());

        //unmatched pairs
        System.out.println("unmatched pairs size");
        matchingRepository.calculateUnmatchedPairs();
        System.out.println(matchingRepository.pairSuccessors.size());
        //Benchmarks
        BenchmarkSystem benchmarkSystem = new BenchmarkSystem(matchingRepository);
        benchmarkSystem.runBenchmarkOnPairs();


    }


    private static void test1() {
        String fileToRead = "src/main/java/org/example/artifacts/teilnehmerliste.csv";
        String partyLocation = "src/main/java/org/example/artifacts/partylocation.csv";
        DataManagement dataManagement = new DataManagement(fileToRead, partyLocation);

        System.out.println("Start matching");
        List<PairMatched> pairMatchedList = PairMatchingAlgorithm.match(dataManagement.soloParticipants);

        System.out.println("Start benchmark");
        System.out.println("Priority: high count");
        PairBenchmark.matchedPairsBenchmark(pairMatchedList, dataManagement.soloParticipants);
        System.out.println();


        for(Pair pair : dataManagement.pairParticipants) {
            pairMatchedList.add(new PairMatched(pair));
        }
        System.out.println(dataManagement.partyLocation);


        /*List<GroupMatched> starterGroups = groupsMatched.get(0);
        List<GroupMatched> mainCourseGroups = groupsMatched.get(1);
        List<GroupMatched> dessertCourseGroups = groupsMatched.get(2);
        starterGroups.forEach(System.out::println);
        System.out.println("The amount of pairs is: " + pairMatchedList.size());
        System.out.println("The amount of starter groups is: " + starterGroups.size());
        System.out.println("The amount of main groups is: " + mainCourseGroups.size());
        System.out.println("The amount of dessert groups is: " + dessertCourseGroups.size());

        System.out.println("successors:");
        System.out.println("Size of successor list: " + GroupMatchingAlgorithm.getSuccessorList().size());
        GroupMatchingAlgorithm.getSuccessorList().forEach(System.out::println);

        System.out.println("The amount of dessert groups is: " + dessertCourseGroups.size());*/

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
        PairBenchmark.matchedPairsBenchmark(pairMatchedList, dataManagement.soloParticipants);
        System.out.println();
    }


    private static void test3() {
        String fileToRead = "src/main/java/org/example/artifacts/teilnehmerliste.csv";
        String partyLocation = "src/main/java/org/example/artifacts/partylocation.csv";
        DataManagement dataManagement = new DataManagement(fileToRead, partyLocation);
        List<PairMatched> pairMatchedList = PairMatchingAlgorithm.match(dataManagement.soloParticipants);

        System.out.println("None");
        PairBenchmark.matchedPairsBenchmark(pairMatchedList, dataManagement.soloParticipants);
        System.out.println();

        CostCoefficients coefficients = new CostCoefficients(10,1,1,1);
        List<PairMatched> pairMatchedList2 = PairMatchingAlgorithm.match(dataManagement.soloParticipants, coefficients, 0.5f);

        System.out.println("Priority: age");

        PairBenchmark.matchedPairsBenchmark(pairMatchedList2, dataManagement.soloParticipants);
        System.out.println();

        List<PairMatched> pairMatchedList3 = PairMatchingAlgorithm.match(dataManagement.soloParticipants);

        System.out.println("Priority: low count");
        PairBenchmark.matchedPairsBenchmark(pairMatchedList3, dataManagement.soloParticipants);
        System.out.println();

        List<PairMatched> pairMatchedList4 = PairMatchingAlgorithm.match(dataManagement.soloParticipants);

        System.out.println("Priority: low count, age");
        PairBenchmark.matchedPairsBenchmark(pairMatchedList4, dataManagement.soloParticipants);
        System.out.println();
    }
}