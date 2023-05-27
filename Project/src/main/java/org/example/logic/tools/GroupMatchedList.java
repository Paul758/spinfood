package org.example.logic.tools;

import org.example.logic.groupmatching.RandomGroupMatching;
import org.example.logic.structures.GroupMatched;
import org.example.logic.structures.PairMatched;

import java.util.ArrayList;
import java.util.List;

public class GroupMatchedList implements Metricable{

    List<PairMatched> pairs;
    List<GroupMatched> groups;
    List<PairMatched> successors;
    public GroupMatchedList(List<PairMatched> pairs, AlgorithmType algorithmType) {
        this.pairs = new ArrayList<>(pairs);
        if (algorithmType.equals(AlgorithmType.RANDOM)) {
            groups = RandomGroupMatching.match(pairs);
        }

        UpdateSuccessorList();
    }

    public void removePair(PairMatched pair) {
        if (successors.contains(pair)) {
            pairs.remove(pair);
            successors.remove(pair);
            return;
        }

        List<GroupMatched> affectedGroups = groups.stream().filter(g -> g.containsPair(pair)).toList();
        PairMatched newPair = findReplacement(affectedGroups);

        if (newPair != null) {
            replacePairInGroups(pair, newPair, affectedGroups);
        } else {
            affectedGroups.forEach(g -> {g.deleteGroup(); groups.remove(g);});
        }

        pairs.remove(pair);
        UpdateSuccessorList();
    }

    private PairMatched findReplacement(List<GroupMatched> groups) {
        return null;
    }

    private void replacePairInGroups(PairMatched oldPair, PairMatched newPair, List<GroupMatched> groups) {

    }

    private void UpdateSuccessorList() {
        List<PairMatched> pairsWithoutGroup = pairs.stream().filter(PairMatched::isNotInGroup).toList();
        successors = new ArrayList<>(pairsWithoutGroup);
    }


    @Override
    public double getPathLength() {
        double sum = 0;
        for (GroupMatched group : groups) {
            sum += group.getPathLength();
        }

        return sum;
        //return groups.stream().mapToDouble(GroupMatched::getPathLength).sum();
    }

    @Override
    public double getGenderDeviation() {
        return 0;
    }

    @Override
    public double getAgeRangeDeviation() {
        return 0;
    }

    @Override
    public double getFoodPreferenceDeviation() {
        return 0;
    }

    @Override
    public boolean isValid() {
        return false;
    }

    public void print() {
        System.out.println("Pairs total:                " + pairs.size());
        System.out.println("Pairs in group:             " + (pairs.size() - successors.size()));
        System.out.println("Pairs on successor list:    " + successors.size());
        System.out.println("Groups:                     " + groups.size());
    }
}
