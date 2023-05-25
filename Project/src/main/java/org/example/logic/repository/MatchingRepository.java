package org.example.logic.repository;

import org.example.data.DataManagement;
import org.example.data.structures.Pair;
import org.example.data.structures.Solo;
import org.example.logic.structures.GroupMatched;
import org.example.logic.structures.PairMatched;
import org.example.logic.tools.MatchingSystem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MatchingRepository {

    DataManagement dataManagement;
    Collection<Solo> soloDataCollection = new ArrayList<>();
    Collection<Pair> pairDataCollection = new ArrayList<>();

    Collection<PairMatched> matchedPairsCollection = new ArrayList<>();
    Collection<GroupMatched> matchedGroupsCollection =  new ArrayList<>();

    Collection<Solo> soloSuccessors =  new ArrayList<>();
    public Collection<PairMatched> pairSuccessors = new ArrayList<>();

    public MatchingRepository(DataManagement dataManagement){
        soloDataCollection = dataManagement.soloParticipants;
        pairDataCollection = dataManagement.pairParticipants;
        System.out.println("pairDataCollection");
        System.out.println(pairDataCollection);
        createAndAddPrematchedPairs();

        Collection<PairMatched> matchedPairs = MatchingSystem.matchPairs((List<Solo>) this.getSoloDataCollection());
        this.addMatchedPairsCollection(matchedPairs);
        Collection<GroupMatched> matchedGroups = MatchingSystem.matchGroups((List<PairMatched>) this.getMatchedPairsCollection());
        this.addMatchedGroupsCollection(matchedGroups);

    }

    public void createAndAddPrematchedPairs() {
        Collection<PairMatched> preMatchedPairs = new ArrayList<>();
        for (Pair pair : pairDataCollection) {
            PairMatched pairMatched = new PairMatched(pair);
            preMatchedPairs.add(pairMatched);
        }
        addMatchedPairsCollection(preMatchedPairs);
    }


    public void setMatchedPairsCollection(Collection<PairMatched> matchedPairsCollection) {
        this.matchedPairsCollection = matchedPairsCollection;
    }

    public void setMatchedGroupsCollection(Collection<GroupMatched> matchedGroupsCollection) {
        this.matchedGroupsCollection = matchedGroupsCollection;
    }

    public void addMatchedPairsCollection(Collection<PairMatched> matchedPairsCollection) {
        this.matchedPairsCollection.addAll(matchedPairsCollection);
    }

    public void addMatchedGroupsCollection(Collection<GroupMatched> matchedGroupsCollection) {
        this.matchedGroupsCollection.addAll(matchedGroupsCollection);
    }

    public Collection<Solo> getSoloDataCollection() {
        return soloDataCollection;
    }

    public Collection<Pair> getPairDataCollection() {
        return pairDataCollection;
    }

    public Collection<PairMatched> getMatchedPairsCollection() {
        return matchedPairsCollection;
    }

    public Collection<GroupMatched> getMatchedGroupsCollection() {
        return matchedGroupsCollection;
    }

    public void calculateUnmatchedPairs() {

        Collection<PairMatched> unmatchedPairs = new ArrayList<>(matchedPairsCollection);

        for (PairMatched pair : matchedPairsCollection) {

            for (GroupMatched group : matchedGroupsCollection) {
                if(group.contains(pair)){
                    unmatchedPairs.remove(pair);
                }
            }
        }
        pairSuccessors = unmatchedPairs;
    }
}
