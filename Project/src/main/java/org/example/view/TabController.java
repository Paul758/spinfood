package org.example.view;

import org.example.view.commands.DisbandPair;

import java.util.ArrayDeque;

public abstract class TabController {

    ArrayDeque<UIAction> undoHistory = new ArrayDeque<>();
    ArrayDeque<UIAction> redoHistory = new ArrayDeque<>();

    public void undo(){

        if(undoHistory.isEmpty()) {
            return;
        }
        UIAction lastAction = undoHistory.removeLast();
        redoHistory.addLast(lastAction);
        lastAction.undo();
        updateUI();
    }

    public void redo() {
        if(redoHistory.isEmpty()) {
            return;
        }
        UIAction lastAction = redoHistory.removeLast();
        run(lastAction);
        updateUI();
    }

    public void run(UIAction action) {
        undoHistory.addLast(action);
        action.run();
        updateUI();
    }

    public abstract void updateUI();

}
