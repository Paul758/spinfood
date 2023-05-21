package org.example.logic.tools;

import org.example.data.DataManagement;
import org.example.data.structures.Pair;
import org.example.data.structures.Solo;

import java.util.Collection;
import java.util.List;

public class MatchingSystem {

    GroupMatchingAlgorithm groupMatchingAlgorithm;
    PairMatchingAlgorithm pairMatchingAlgorithm;

    public static Collection<PairMatched> matchPairs(DataManagement dataManagement) {
        List<Solo> soloParticipants = dataManagement.soloParticipants;
        List<Pair> pairParticipants = dataManagement.pairParticipants;

        List<PairMatched> matchedPairs = PairMatchingAlgorithm.match(soloParticipants);

        for (Pair pair : pairParticipants) {
            matchedPairs.add(new PairMatched(pair));
        }

        return matchedPairs;
    }

    public static Collection<GroupMatched> matchGroups() {
        throw new RuntimeException("not implemented yet");
    }



}
