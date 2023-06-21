package org.example.view;

import org.example.view.commands.DisbandPair;

import java.util.ArrayDeque;

public abstract class TabController {

    ArrayDeque<UIAction> undoHistory = new ArrayDeque<>();
    ArrayDeque<UIAction> redoHistory = new ArrayDeque<>();

    public void undo(){
        System.out.println("undo called");
        System.out.println("redoHistorySize before undo is " + redoHistory.size());
        if(undoHistory.isEmpty()) {
            return;
        }
        UIAction lastAction = undoHistory.removeLast();
        redoHistory.addLast(lastAction);
        lastAction.undo();

        updateUI();
        System.out.println("redoHistorySize after undo is " + redoHistory.size());
    }

    public void redo() {
        System.out.println("redo called");
        System.out.println("redo History now is sized: " + redoHistory.size() );
        if(redoHistory.isEmpty()) {
            System.out.println("redoHistory is empty, returning nothing");
            return;
        }
        UIAction lastAction = redoHistory.removeLast();
        run(lastAction);
        updateUI();

    }

    public void run(UIAction action) {
        System.out.println("current action is " + action);
        undoHistory.addLast(action);
        action.run();
        updateUI();
    }

    public abstract void updateUI();

}
