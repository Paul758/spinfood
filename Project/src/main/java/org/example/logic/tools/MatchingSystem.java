package org.example.logic.tools;

import org.example.data.structures.Solo;
import org.example.logic.enums.Criteria;
import org.example.logic.structures.GroupMatched;
import org.example.logic.structures.PairMatched;
import org.example.logic.tools.algorithms.GroupMatchingGraph;
import org.example.logic.tools.algorithms.MatchCosts;
import org.example.logic.tools.algorithms.PairMatchingAlgorithm;

import java.util.Collection;
import java.util.List;

public class MatchingSystem {



    public static Collection<PairMatched> matchPairs(List<Solo> unmatchedSolos) {
        Criteria food = Criteria.IDENTICAL_FOOD_PREFERENCE;
        Criteria age = Criteria.AGE_DIFFERENCE;
        Criteria gender = Criteria.GENDER_DIFFERENCE;
        Criteria mostMatches = Criteria.MATCH_COUNT;
        Criteria path = Criteria.PATH_LENGTH;
        return PairMatchingAlgorithm.match(unmatchedSolos, new MatchCosts(food, age, gender, mostMatches, path));
    }

    public static Collection<GroupMatched> matchGroups(List<PairMatched> unmatchedPairs) {
        Criteria food = Criteria.IDENTICAL_FOOD_PREFERENCE;
        Criteria age = Criteria.AGE_DIFFERENCE;
        Criteria gender = Criteria.GENDER_DIFFERENCE;
        Criteria mostMatches = Criteria.MATCH_COUNT;
        Criteria path = Criteria.PATH_LENGTH;

        return GroupMatchingGraph.match(unmatchedPairs, new MatchCosts(food, age, gender, mostMatches, path));
    }


}
