package org.example.logic.tools;

import org.example.data.DataManagement;
import org.example.data.structures.Pair;
import org.example.data.structures.Solo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MatchingSystem {

    static Collection<PairMatched> matchedPairs = new ArrayList<>();

    static Collection<Collection<GroupMatched>> matchedGroups;
    List<PairMatched> successorPairs;

    public static void main(String[] args) {
        String fileToRead = "src/main/java/org/example/artifacts/teilnehmerliste.csv";
        String partyLocation = "src/main/java/org/example/artifacts/partylocation.csv";
        DataManagement dataManagement = new DataManagement(fileToRead, partyLocation);

        matchedPairs = matchPairs(dataManagement);
        GroupMatchingAlgorithm.setPartyLocation(dataManagement.partyLocation);
        matchedGroups = matchGroups(matchedPairs);

        System.out.println(matchedGroups);
    }


    public static Collection<PairMatched> matchPairs(DataManagement dataManagement) {
        List<Solo> soloParticipants = dataManagement.soloParticipants;
        List<Pair> pairParticipants = dataManagement.pairParticipants;

        List<PairMatched> matchedPairs = PairMatchingAlgorithm.match(soloParticipants);

        for (Pair pair : pairParticipants) {
            matchedPairs.add(new PairMatched(pair));
        }

        return matchedPairs;
    }

    public static Collection<Collection<GroupMatched>> matchGroups(Collection<PairMatched> matchedPairs) {
        List<PairMatched> pairs = new ArrayList<>(matchedPairs);
        Collection<Collection<GroupMatched>> matchedGroups = GroupMatchingAlgorithm.match(pairs);

        List<Collection<GroupMatched>> matchedGroupsList =  matchedGroups.stream().toList();
        System.out.println("sizes");
        System.out.println(matchedGroupsList.get(0).size());
        System.out.println(matchedGroupsList.get(1).size());
        System.out.println(matchedGroupsList.get(2).size());


        return matchedGroups;
    }




}
