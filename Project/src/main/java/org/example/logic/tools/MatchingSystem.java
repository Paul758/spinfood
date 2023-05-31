package org.example.logic.tools;

import org.example.data.structures.Solo;
import org.example.logic.enums.Criteria;
import org.example.logic.structures.GroupMatched;
import org.example.logic.structures.PairMatched;
import org.example.logic.matchingalgorithms.GraphGroupMatching;
import org.example.logic.matchingalgorithms.MatchCosts;
import org.example.logic.matchingalgorithms.GraphPairMatching;

import java.util.Collection;
import java.util.List;

public class MatchingSystem {

    //TODO Refactor, don't use this class anymore
    public static Collection<PairMatched> matchPairs(List<Solo> unmatchedSolos) {
        Criteria food = Criteria.IDENTICAL_FOOD_PREFERENCE;
        Criteria age = Criteria.AGE_DIFFERENCE;
        Criteria gender = Criteria.GENDER_DIFFERENCE;
        Criteria mostMatches = Criteria.MATCH_COUNT;
        Criteria path = Criteria.PATH_LENGTH;

        return GraphPairMatching.match(unmatchedSolos, new MatchCosts(food, age, gender, mostMatches, path));
    }

    public static Collection<GroupMatched> matchGroups(List<PairMatched> unmatchedPairs) {
        Criteria food = Criteria.IDENTICAL_FOOD_PREFERENCE;
        Criteria age = Criteria.AGE_DIFFERENCE;
        Criteria gender = Criteria.GENDER_DIFFERENCE;
        Criteria mostMatches = Criteria.MATCH_COUNT;
        Criteria path = Criteria.PATH_LENGTH;

        return GraphGroupMatching.match(unmatchedPairs, new MatchCosts(food, age, gender, mostMatches, path));
    }


}
