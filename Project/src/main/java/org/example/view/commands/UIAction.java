package org.example.view.commands;

/**
 * @version 1
 * @see CreateGroupCommand
 * @see CreatePairCommand
 * @see DisbandGroupCommand
 * @see DisbandPairCommand
 * This interface holds the basic methods for efficient implementation of the command pattern.
 * After representing a command as an object, the action it represents can be run, undone and redone.
 */
public interface UIAction {

    void run();

    void undo();

    void redo();
}
