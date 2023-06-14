package org.example.logic.structures;

import org.example.data.DataManagement;
import org.example.data.enums.FoodPreference;
import org.example.data.enums.KitchenType;
import org.example.data.structures.Pair;
import org.example.data.structures.Solo;
import org.example.logic.tools.MatchingSystem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Holds all necessary data that is used in the logic layer of the project.
 * Data can be queried and modified with methods in this class
 * @author Paul Groß
 *
 */
public class MatchingRepository {

    public DataManagement dataManagement;
    Collection<Solo> soloDataCollection = new ArrayList<>();
    Collection<Pair> pairDataCollection = new ArrayList<>();

    Collection<PairMatched> matchedPairsCollection = new ArrayList<>();
    Collection<GroupMatched> matchedGroupsCollection =  new ArrayList<>();

    public Collection<Solo> soloSuccessors =  new ArrayList<>();
    public Collection<PairMatched> pairSuccessors = new ArrayList<>();

    /**
     * If a matching repository is created with a data management object, the data of the solo and pair participants are read in.
     * PairMatched pairs are created, which are defined by pair registrations.
     * A PairMatching algorithm is called to define further pairs.
     * After that, a GroupMatching algorithm is called to group the pairs.
     * After the matching algorithms are run, the successor solos and pairs are stored in lists.
     * @author Paul Groß
     * @param dataManagement object that contains data about registered solo participants and pair participants
     */
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

    /**
     * The pairs from the DataManagement object are transferred into PairMatched objects
     * @author Paul Groß
     */
    public void createAndAddPrematchedPairs() {
        Collection<PairMatched> preMatchedPairs = new ArrayList<>();
        for (Pair pair : pairDataCollection) {
            PairMatched pairMatched = new PairMatched(pair);
            preMatchedPairs.add(pairMatched);
        }
        addMatchedPairsCollection(preMatchedPairs);
    }

    /**
     * The distance from each pair to the party location is set
     * @author Paul Groß
     */
    public void setDistanceToPartyLocationForPairs(){
        Collection<PairMatched> pairs = getMatchedPairsCollection();
        for (PairMatched pair : pairs){
            pair.setDistanceToPartyLocation(this.dataManagement.partyLocation);
        }
    }

    /**
     * Find the solos that haven't been matched
     * @author Paul Groß
     */
    private void UpdateSoloSuccessors(){
        Collection<Solo> solos = new ArrayList<>(getSoloDataCollection());
        Collection<PairMatched> pairMatchedList = new ArrayList<>(matchedPairsCollection);

        for (Solo solo : soloDataCollection) {
            for (PairMatched pair : pairMatchedList) {
                if(pair.contains(solo)){
                    solos.remove(solo);
                }
            }
        }
        soloSuccessors = solos;
    }

    /**
     * Find the pairs that haven't been matched and add them to a list
     * @author Paul Groß
     */
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

    //Code for handling the un-registration of EventParticipants

    /**
     * Removes a solo and tries to find a successor
     * @param solo a solo that unregistered from the event
     */
    public void removeSolo(Solo solo){
        //If solo is not matched, just remove it
        if(soloSuccessors.contains(solo)){
            soloDataCollection.remove(solo);
            soloSuccessors.remove(solo);
            return;
        }

        //Find the pair that is affected by the removing of a solo
        PairMatched affectedPair = getMatchedPairsCollection().stream().filter(p -> p.contains(solo)).toList().get(0);

        //Find a replacement
        Solo newSolo = findReplacement(affectedPair, solo);

        //If a replacement solo is found, replace it in the affected pair
        if(newSolo != null) {
            if (affectedPair.getSoloA().equals(solo)) {
                affectedPair.setSoloA(newSolo);
            } else if (affectedPair.getSoloB().equals(solo)) {
                affectedPair.setSoloB(newSolo);
            }
            affectedPair.updatePairMatchedData();
            return;
        }

        //If there is no replacement, the pair has to be deleted and the remaining solo needs to be added to the
        //successor list
        Solo remainingSolo = null;
        if (affectedPair.getSoloA().equals(solo)) {
            remainingSolo = affectedPair.getSoloB();
        } else if (affectedPair.getSoloB().equals(solo)) {
            remainingSolo = affectedPair.getSoloA();
        }

        soloSuccessors.add(remainingSolo);

        //remove the pair
        removePair(affectedPair);
    }

    /**
     * Tries to find a replacement from the successor list the successor may need a kitchen and needs to have
     * the same food preference as the leaving solo or a none food preference
     * @author Paul Groß
     * @param solo a solo that unregistered from the event
     * @return a replacement solo for the unregistered solo participant
     */
    private Solo findReplacement(PairMatched affectedPair, Solo solo) {
        //Check kitchen situation
        Solo pairSoloA = affectedPair.getSoloA();
        Solo pairSoloB = affectedPair.getSoloB();
        boolean soloAhasKitchen = false;
        boolean soloBhasKitchen = false;
        boolean kitchenNeeded = false;

        if(pairSoloA.kitchen.kitchenType.equals(KitchenType.YES)) {
            soloAhasKitchen = true;
        }
        if(pairSoloB.kitchen.kitchenType.equals(KitchenType.YES)) {
            soloBhasKitchen = true;
        }

        //If leaving solo is the one with the kitchen and the remaining doesn't have a kitchen, the new
        //solo needs to have a kitchen
        if(solo.equals(pairSoloA) && !soloBhasKitchen){
            kitchenNeeded = true;
        } else if(solo.equals(pairSoloB) && !soloAhasKitchen){
            kitchenNeeded = true;
        }

        for (Solo replacementSolo : soloSuccessors) {
            if(kitchenNeeded) {
                if(replacementSolo.kitchen.kitchenType.equals(KitchenType.NO)){
                    continue;
                }
            }

            if (!replacementSolo.foodPreference.equals(solo.foodPreference) && !replacementSolo.foodPreference.equals(FoodPreference.NONE)) {
                continue;
            }
            return replacementSolo;
        }
        return null;
    }

    /**
     * Removes a pair that unregistered from the event. Also is called when a solo unregisters from the event
     * and there is no fitting successor.
     * @param pair a pair that unregistered from the event
     */
    public void removePair(PairMatched pair) {
        //If pair is not matched in a group, just remove it
        if (pairSuccessors.contains(pair)) {
            getMatchedPairsCollection().remove(pair);
            pairSuccessors.remove(pair);
            return;
        }

        //Find all affected groups. The pair is a part of 3 groups
        List<GroupMatched> affectedGroups = getMatchedGroupsCollection().stream().filter(g -> g.containsPair(pair)).toList();

        //Try to find a replacement pair
        PairMatched newPair = findReplacement(pair);

        //If a replacement is found, replace the pair in all 3 groups
        //If no replacement is found, the group has to be deleted and the remaining pairs get added to the successor list.
        if (newPair != null) {
            replacePairInGroups(pair, newPair, affectedGroups);
        } else {
            affectedGroups.forEach(g -> {g.removePair(pair);
                                            disbandGroup(g);
                                            getMatchedGroupsCollection().remove(g);});
        }

        getMatchedPairsCollection().remove(pair);
        UpdatePairSuccessors();
    }

    /**
     * Adds all remaining pairs of a group to the successor list
     * @param group a group that needs to be deleted
     */
    private void disbandGroup(GroupMatched group) {
        pairSuccessors.addAll(group.getPairList());
    }

    /**
     * Replaces the unregistered pair of the three groups with the new pair
     * @param pair the pair that unregistered from the event
     * @param newPair the filler pair, that replaces the pair that unregistered
     * @param affectedGroups the three affected groups the unregistered pair was part of
     */
    private void replacePairInGroups(PairMatched pair, PairMatched newPair, List<GroupMatched> affectedGroups) {
        System.out.println("now replacing " + pair + " with " + newPair);
        for (GroupMatched group : affectedGroups) {
            group.switchPairs(pair, newPair);
        }
    }

    /**
     * Tries to find a replacement pair for the pair the unregistered from the event
     * @param pair a pair that unregistered from the event
     * @return a pair that is similar to the unregistered pair that can be used as a replacement
     */
    private PairMatched findReplacement(PairMatched pair) {
        for (PairMatched replacementPair : pairSuccessors) {
            if(replacementPair.getFoodPreference().equals(pair.getFoodPreference())){
                return replacementPair;
            }
        }
        return null;
    }


    //Method for disbanding a pair from view thing
    public void disbandPair(PairMatched pairToDisband) {
        System.out.println("MR: Count of solo successors now is: " + soloSuccessors.size());
        if(pairToDisband.isPreMatched()) {
            //cant disband pair because it is prematched
            throw new RuntimeException("cant disband pair because it is prematched");
        }

        Solo soloA = pairToDisband.getSoloA();
        Solo soloB = pairToDisband.getSoloB();

        soloSuccessors.add(soloA);
        soloSuccessors.add(soloB);

        this.matchedPairsCollection.remove(pairToDisband);

        System.out.println("MR: Count of solo successors now is: " + soloSuccessors.size());
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


    public void printPairSuccessorList(){
        pairSuccessors.forEach(x -> System.out.println(x.getFoodPreference()));
    }


}
