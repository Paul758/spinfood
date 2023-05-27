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

    public Collection<Solo> soloSuccessors =  new ArrayList<>();
    public Collection<PairMatched> pairSuccessors = new ArrayList<>();

    public MatchingRepository(DataManagement dataManagement){
        this.dataManagement = dataManagement;
        addSoloCollection(dataManagement.soloParticipants);
        addPairCollection(dataManagement.pairParticipants);

        createAndAddPrematchedPairs();

        Collection<PairMatched> matchedPairs = MatchingSystem.matchPairs((List<Solo>) this.getSoloDataCollection());
        this.addMatchedPairsCollection(matchedPairs);

        setDistanceToPartyLocationForPairs();

        Collection<GroupMatched> matchedGroups = MatchingSystem.matchGroups((List<PairMatched>) this.getMatchedPairsCollection());
        this.addMatchedGroupsCollection(matchedGroups);

        UpdateSoloSuccessors();
        UpdatePairSuccessors();
        
    }

    public void createAndAddPrematchedPairs() {
        Collection<PairMatched> preMatchedPairs = new ArrayList<>();
        for (Pair pair : pairDataCollection) {
            PairMatched pairMatched = new PairMatched(pair);
            preMatchedPairs.add(pairMatched);
        }
        addMatchedPairsCollection(preMatchedPairs);
    }

    public void setDistanceToPartyLocationForPairs(){
        Collection<PairMatched> pairs = getMatchedPairsCollection();
        for (PairMatched pair : pairs){
            pair.setDistanceToPartyLocation(this.dataManagement.partyLocation);
        }
    }

    public void printFoodPreferencesOfPairs(){
        System.out.println("Now printing foodPreferences of matched pairs");
        for (PairMatched pair :
                getMatchedPairsCollection()) {
            System.out.println(pair.getFoodPreference());
        }
    }

    //Code for handling the un-registration of EventParticipants
    public void removeSolo(Solo solo){
        if(soloSuccessors.contains(solo)){
            soloDataCollection.remove(solo);
            soloSuccessors.remove(solo);
            return;
        }

        PairMatched affectedPair = getMatchedPairsCollection().stream().filter(p -> p.containsPerson(solo.person)).toList().get(0);
        Solo newSolo = findReplacement(solo);

        if(newSolo != null){
            if (affectedPair.getPersonA().equals(solo.person)) {
                affectedPair.setPersonA(solo.person);
            } else if (affectedPair.getPersonB().equals(solo.person)) {
                affectedPair.setPersonB(solo.person);
            }
            return;
        }

        //If there is no replacement solo, the pair has to be removed from the group
        removePair(affectedPair);
    }

    private Solo findReplacement(Solo solo) {
        for (Solo replacementSolo : soloDataCollection) {
            if(replacementSolo.foodPreference.equals(solo.foodPreference)){
                return replacementSolo;
            }
        }
        return null;
    }

    public void removePair(PairMatched pair) {
        if (pairSuccessors.contains(pair)) {
            getMatchedPairsCollection().remove(pair);
            pairSuccessors.remove(pair);
            return;
        }

        List<GroupMatched> affectedGroups = getMatchedGroupsCollection().stream().filter(g -> g.containsPair(pair)).toList();
        PairMatched newPair = findReplacement(pair);

        if (newPair != null) {
            replacePairInGroups(pair, newPair, affectedGroups);
        } else {
            affectedGroups.forEach(g -> {g.deleteGroup(); getMatchedGroupsCollection().remove(g);});
        }

        getMatchedPairsCollection().remove(pair);
        UpdatePairSuccessors();
    }

    private void replacePairInGroups(PairMatched pair, PairMatched newPair, List<GroupMatched> affectedGroups) {
        for (GroupMatched group : affectedGroups) {
            group.switchPairs(pair, newPair);
        }
    }

    private PairMatched findReplacement(PairMatched pair) {
        for (PairMatched replacementPair : getMatchedPairsCollection()) {
            if(replacementPair.getFoodPreference().equals(pair.getFoodPreference())){
                return replacementPair;
            }
        }
        return null;
    }

    private void UpdateSoloSuccessors(){
        Collection<Solo> solos = new ArrayList<>(getSoloDataCollection());
        Collection<PairMatched> pairMatchedList = new ArrayList<>(matchedPairsCollection);

        for (Solo solo : soloDataCollection) {

            for (PairMatched pair : pairMatchedList) {
                if(solo.person.equals(pair.getPersonA()) || solo.person.equals(pair.getPersonB())){
                    solos.remove(solo);
                }
            }
        }
        soloSuccessors = solos;
    }


    public void UpdatePairSuccessors() {

        Collection<PairMatched> unmatchedPairs = new ArrayList<>(matchedPairsCollection);

        for (PairMatched pair : matchedPairsCollection) {

            for (GroupMatched group : matchedGroupsCollection) {
                if(group.containsPair(pair)){
                    unmatchedPairs.remove(pair);
                }
            }
        }
        pairSuccessors = unmatchedPairs;
    }

    public void printFoodPreferenceOfPairPersons(){
        List<PairMatched> pairMatchedList = (List<PairMatched>) getMatchedPairsCollection();

        for (PairMatched pair : pairMatchedList) {
            System.out.println("FoodPreferences");
            System.out.println("A: " + pair.getPersonAFoodPreference() + " B: " + pair.getPersonBFoodPreference());
        }
        
    }


    //Getters, Setters
    public void addMatchedPairsCollection(Collection<PairMatched> matchedPairsCollection) {
        this.matchedPairsCollection.addAll(matchedPairsCollection);
    }

    public void addMatchedGroupsCollection(Collection<GroupMatched> matchedGroupsCollection) {
        this.matchedGroupsCollection.addAll(matchedGroupsCollection);
    }

    public void addSoloCollection(Collection<Solo> solos) {
        this.soloDataCollection.addAll(solos);
    }

    public void addPairCollection(Collection<Pair> pairs) {
        this.pairDataCollection.addAll(pairs);
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


}
