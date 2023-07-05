package org.example.view.commands;

import org.example.data.structures.Solo;
import org.example.logic.structures.MatchingRepository;
import org.example.logic.structures.PairMatched;

/**
 * This class represents a command to create a pair.
 * @see org.example.logic.structures.PairMatched
 * @see org.example.logic.structures.MatchingRepository
 * @see org.example.view.commands.UIAction
 */
public class CreatePairCommand implements UIAction {

    PairMatched pairMatched;
    MatchingRepository matchingRepository;

    public CreatePairCommand(Solo soloA, Solo soloB, MatchingRepository matchingRepository) {
        this.pairMatched = new PairMatched(soloA, soloB);
        this.matchingRepository = matchingRepository;
    }

    @Override
    public void run() {
        matchingRepository.soloSuccessors.remove(pairMatched.getSoloA());
        matchingRepository.soloSuccessors.remove(pairMatched.getSoloB());
        matchingRepository.getMatchedPairsCollection().add(pairMatched);
    }

    @Override
    public void undo() {
        matchingRepository.soloSuccessors.add(pairMatched.getSoloA());
        matchingRepository.soloSuccessors.add(pairMatched.getSoloB());
        matchingRepository.getMatchedPairsCollection().remove(pairMatched);
    }

    @Override
    public void redo() {
        run();
    }
}
