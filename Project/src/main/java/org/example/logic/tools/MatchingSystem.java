package org.example.logic.tools;

import org.example.data.structures.Solo;
import org.example.logic.structures.GroupMatched;
import org.example.logic.structures.PairMatched;
import org.example.logic.tools.algorithms.GroupMatchingAlgorithm;
import org.example.logic.tools.algorithms.GroupMatchingGraph;
import org.example.logic.tools.algorithms.PairMatchingAlgorithm;

import java.util.Collection;
import java.util.List;

public class MatchingSystem {

    public static Collection<PairMatched> matchPairs(List<Solo> unmatchedSolos) {
        return PairMatchingAlgorithm.match(unmatchedSolos);
    }

    public static Collection<GroupMatched> matchGroups(List<PairMatched> unmatchedPairs) {
        return GroupMatchingGraph.match(unmatchedPairs);
    }


}
