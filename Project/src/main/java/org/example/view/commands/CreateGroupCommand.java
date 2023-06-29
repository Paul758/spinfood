package org.example.view.commands;

import org.example.logic.structures.GroupMatched;
import org.example.logic.structures.MatchingRepository;
import org.example.logic.structures.PairMatched;
import org.example.view.UIAction;

public class CreateGroupCommand implements UIAction {
    MatchingRepository matchingRepository;
    GroupMatched groupMatched;

    public CreateGroupCommand(MatchingRepository matchingRepository, GroupMatched groupMatched) {
        this.matchingRepository = matchingRepository;
        this.groupMatched = groupMatched;
    }

    @Override
    public void run() {
        matchingRepository.getMatchedGroupsCollection().add(groupMatched);
        matchingRepository.UpdatePairSuccessors();
    }

    @Override
    public void undo() {
        matchingRepository.getMatchedGroupsCollection().remove(groupMatched);
        matchingRepository.UpdatePairSuccessors();
    }

    @Override
    public void redo() {
        run();
    }
}
