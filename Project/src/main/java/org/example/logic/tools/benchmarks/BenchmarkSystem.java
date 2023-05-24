package org.example.logic.tools.benchmarks;

import org.example.data.structures.Solo;
import org.example.logic.repository.MatchingRepository;
import org.example.logic.structures.GroupMatched;
import org.example.logic.structures.PairMatched;

import java.util.Collection;
import java.util.List;

public class BenchmarkSystem {

    MatchingRepository matchingRepository;

    public BenchmarkSystem(MatchingRepository matchingRepository){
        this.matchingRepository = matchingRepository;
    }


    public void runBenchmarkOnPairs(){
        List<PairMatched> matchedPairs = (List<PairMatched>) matchingRepository.getMatchedPairsCollection();
        List<Solo > solos = (List<Solo>) matchingRepository.getSoloDataCollection();
        PairBenchmark.matchedPairsBenchmark(matchedPairs, solos);
    }

    public void runBenchmarkOnGroups(){
        Collection<GroupMatched> matchedGroups = matchingRepository.getMatchedGroupsCollection();

    }


}
