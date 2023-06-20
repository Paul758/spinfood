package org.example.logic.matchingalgorithms;

import org.example.data.DataManagement;
import org.example.logic.structures.GroupMatched;
import org.example.logic.structures.MatchingRepository;
import org.example.logic.structures.PairMatched;

import java.util.Collection;

public class GroupMatchDisplay {

    public static void main(String[] args) {
        MatchingRepository matchingRepository = new MatchingRepository(
                new DataManagement(
                        "src/main/java/org/example/artifacts/teilnehmerliste.csv",
                        "src/main/java/org/example/artifacts/partylocation.csv"));


        matchingRepository.matchPairs();
        matchingRepository.matchGroups();
        Collection<GroupMatched> groupMatchedCollection = matchingRepository.getMatchedGroupsCollection();

        System.out.println("Amount of groups: " + groupMatchedCollection.size());
        System.out.println("Amount of successor pairs: " + matchingRepository.pairSuccessors.size());

        //matchingRepository.matchGroups(matchingRepository.pairSuccessors);
        //Collection<GroupMatched> groupMatchedCollectionSecond = matchingRepository.getMatchedGroupsCollection();

        //System.out.println("Amount of groups after second matching: " + groupMatchedCollectionSecond.size());
        //System.out.println("Amount of successor pairs after second matching: " + matchingRepository.pairSuccessors.size());

        //matchingRepository.matchGroups(matchingRepository.pairSuccessors);
        //Collection<GroupMatched> groupMatchedCollectionThird = matchingRepository.getMatchedGroupsCollection();

        //System.out.println("Amount of groups after second matching: " + groupMatchedCollectionThird.size());
        //System.out.println("Amount of successor pairs after second matching: " + matchingRepository.pairSuccessors.size());

        System.out.println("Food preferences of successors: ");
        for (PairMatched pair : matchingRepository.pairSuccessors) {
            System.out.println(pair.getFoodPreference());
        }
    }
}
