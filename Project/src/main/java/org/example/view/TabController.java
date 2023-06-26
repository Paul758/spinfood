package org.example.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.logic.matchingalgorithms.MatchCosts;
import org.example.logic.structures.MatchingRepository;
import org.example.view.tools.MatchCostChooser;
import org.example.view.tools.PairBuilder;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ResourceBundle;

public abstract class TabController {

    ArrayDeque<UIAction> undoHistory = new ArrayDeque<>();
    ArrayDeque<UIAction> redoHistory = new ArrayDeque<>();
    private String name;
    protected MatchingRepository matchingRepository;
    private Stage matchCostStage;

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

    protected void openMatchCostChooserWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(PairBuilder.class.getResource("/MatchCostChooser.fxml"));
        //Specify locale
        ResourceBundle bundle = ResourceBundle.getBundle("uiElements", Settings.getInstance().getLocale());
        fxmlLoader.setResources(bundle);
        Parent root = fxmlLoader.load();

        MatchCostChooser matchCostChooser = fxmlLoader.getController();
        matchCostChooser.setup(this);

        matchCostStage = new Stage();
        matchCostStage.setTitle("Pair Builder");
        matchCostStage.setScene(new Scene(root));
        matchCostStage.initModality(Modality.APPLICATION_MODAL);
        matchCostStage.show();
    }

    public void closeMatchCostChooserWindow(MatchCosts matchCosts) {
        matchCostStage.close();
        closeMatchCost(matchCosts);
    }

    public abstract void closeMatchCost(MatchCosts matchCosts);

    public MatchingRepository getMatchingRepository() {
        return matchingRepository;
    }

    public String getName() {
        return name;
    }
}
