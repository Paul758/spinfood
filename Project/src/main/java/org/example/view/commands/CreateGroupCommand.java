package org.example.view.commands;

import org.example.logic.structures.GroupMatched;
import org.example.logic.structures.MatchingRepository;

/**
 * This class represents a command to create a group.
 * It is implemented as an object for the command pattern to be used in the undo/redo functionality.
 * It holds a groupMatched object and a matchingRepository object.
 * @see org.example.logic.structures.PairMatched
 * @see org.example.logic.structures.MatchingRepository
 * @see org.example.view.commands.UIAction
 */
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
