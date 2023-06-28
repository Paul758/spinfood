package org.example.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.Main;
import org.example.data.structures.Pair;
import org.example.data.structures.Solo;
import org.example.logic.matchingalgorithms.MatchCosts;
import org.example.logic.structures.MatchingRepository;
import org.example.logic.structures.PairMatched;
import org.example.view.tools.MatchCostChooser;
import org.example.view.tools.PairBuilder;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public abstract class TabController {

    ArrayDeque<UIAction> undoHistory = new ArrayDeque<>();
    ArrayDeque<UIAction> redoHistory = new ArrayDeque<>();
    protected Main parent;
    private final String name;
    protected MatchingRepository matchingRepository;
    protected List<TabController> children = new ArrayList<>();
    private Stage matchCostStage;
    private Stage popupStage;

    private Tab tab;

    public TabController(MatchingRepository matchingRepository, Main parent, String name) {
        this.matchingRepository = matchingRepository;
        this.parent = parent;
        this.name = name;
    }

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

    public void addChild(TabController tabController) {
        children.add(tabController);
    }

    public void delete() {
        parent.closeTab(this);
        children.forEach(TabController::delete);
    }

    public void removeSolo(Solo soloToRemove) {
        System.out.println("called on " + name);
        System.out.println(matchingRepository.getSoloDataCollection().contains(soloToRemove));
        matchingRepository.removeSoloData(soloToRemove);
        matchingRepository.removeSolo(soloToRemove);

        children.forEach(c -> c.removeSolo(soloToRemove));
        updateUI();
    }

    public void removePair(PairMatched pairMatchedToRemove, Pair pairToRemove) {
        System.out.println("called on " + name);
        System.out.println(matchingRepository.getMatchedPairsCollection().contains(pairMatchedToRemove));
        matchingRepository.removePairData(pairToRemove);
        matchingRepository.removePair(pairMatchedToRemove);

        children.forEach(c -> c.removePair(pairMatchedToRemove, pairToRemove));
        updateUI();
    }

    protected void openPopupWindow(Parent root, String title) {
        popupStage = new Stage();
        popupStage.setTitle(title);
        popupStage.setScene(new Scene(root));
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.showAndWait();
    }

    public void closePopupWindow() {
        popupStage.close();
    }

    protected void openMatchCostChooserWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(PairBuilder.class.getResource("/MatchCostChooser.fxml"));
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

    protected void closeMatchCost(MatchCosts matchCosts) {

    }

    public MatchingRepository getMatchingRepository() {
        return matchingRepository;
    }

    public String getName() {
        return name;
    }

    public Tab getTab() {
        return tab;
    }

    public void setTab(Tab tab) {
        this.tab = tab;
    }

    public List<TabController> getChildren() {
        return children;
    }
}
