package org.example.logic.repository;

import org.example.data.DataManagement;
import org.example.data.enums.FoodPreference;
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
        this.dataManagement = dataManagement;
        soloDataCollection = dataManagement.soloParticipants;
        pairDataCollection = dataManagement.pairParticipants;
        System.out.println("pairDataCollection");
        System.out.println(pairDataCollection);
        createAndAddPrematchedPairs();
        setDistanceToPartyLocationForPairs();
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

    public void setDistanceToPartyLocationForPairs(){
        Collection<PairMatched> pairs = getMatchedPairsCollection();

        for (PairMatched pair : pairs){
            pair.setDistanceToPartyLocation(this.dataManagement.partyLocation);
        }
    }


    public void printFoodPreferencesOfPairs(){
        System.out.println("now printing foodPreferences of matched pairs");
        for (PairMatched pair :
                getMatchedPairsCollection()) {
            System.out.println(pair.foodPreference);
        }
    }



    public void unregisterParticipant(Solo unregisteredParticipant){
        if(isInGroup(unregisteredParticipant)) {


        }

        if(isInPair(unregisteredParticipant)){

        }

    }

    private boolean isInGroup(Solo unregisteredParticipant) {
        for (GroupMatched group : getMatchedGroupsCollection()) {
            for (PairMatched pair: group.getPairList()) {

                if (!pair.contains(unregisteredParticipant)) {
                    continue;
                }

                Solo replacement = findReplacement(unregisteredParticipant);

                if (replacement == null) {
                    break;
                }

                if (pair.soloA.equals(unregisteredParticipant)) {
                    pair.soloA = replacement;
                } else {
                    pair.soloB = replacement;
                }
            }
        }

    }

    private boolean isInPair(Solo unregisteredParticipant) {
        for (PairMatched pair : getMatchedPairsCollection()) {
            if(pair.soloA.equals(unregisteredParticipant) || pair.soloB.equals(unregisteredParticipant)){
                return true;
            }
        }
    }

    public void removeFromPair(Solo unregisteredParticipant){

    }

    public void removeFromGroup(Solo unregisteredParticipant){

    }

    public Solo findReplacement(Solo unregisteredParticipant){
        for (Solo solo : soloSuccessors) {
            if(solo.foodPreference.equals(unregisteredParticipant.foodPreference)){
                return solo;
            }
            //TODO check for age compatibility
        }
        return null;
    }
}
