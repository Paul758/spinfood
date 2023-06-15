package org.example.view.tools;

import org.example.data.structures.Solo;
import org.example.logic.structures.MatchingRepository;
import org.example.logic.structures.PairMatched;
import org.example.view.UIAction;

public class DisbandPair implements UIAction {

    MatchingRepository matchingRepository;
    PairMatched pairToDisband;
    public DisbandPair(MatchingRepository matchingRepository, PairMatched pairToDisband) {
        this.matchingRepository = matchingRepository;
        this.pairToDisband = pairToDisband;
    }


    @Override
    public void run() {
        matchingRepository.disbandPair(pairToDisband);
    }

    @Override
    public void undo() {
        Solo soloA = pairToDisband.getSoloA();
        Solo soloB = pairToDisband.getSoloB();

        matchingRepository.soloSuccessors.remove(soloA);
        matchingRepository.soloSuccessors.remove(soloB);

        matchingRepository.getMatchedPairsCollection().add(pairToDisband);
    }
}
