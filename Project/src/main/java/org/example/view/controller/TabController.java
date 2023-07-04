package org.example.view.controller;

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
import org.example.view.tools.Settings;
import org.example.view.commands.UIAction;
import org.example.view.windows.MatchCostChooser;
import org.example.view.windows.PairBuilder;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

/**
 * The parent class from which the various tab controller classes inherit
 */
public abstract class TabController {

    ArrayDeque<UIAction> undoHistory = new ArrayDeque<>();
    ArrayDeque<UIAction> redoHistory = new ArrayDeque<>();
    protected Main parent;
    private final String name;
    protected MatchingRepository matchingRepository;
    private final List<TabController> children = new ArrayList<>();
    private Stage popupStage;
    private Tab tab;

    public TabController(MatchingRepository matchingRepository, Main parent, String name) {
        this.matchingRepository = matchingRepository;
        this.parent = parent;
        this.name = name;
    }

    /**
     * Implements the functionality to undo commands
     */
    public void undo(){
        if(undoHistory.isEmpty()) {
            return;
        }
        UIAction lastAction = undoHistory.removeLast();
        redoHistory.addLast(lastAction);
        lastAction.undo();
        updateUI();
    }

    /**
     * Implements the functionality to redo commands
     */
    public void redo() {
        if(redoHistory.isEmpty()) {
            return;
        }
        UIAction lastAction = redoHistory.removeLast();
        undoHistory.addLast(lastAction);
        lastAction.redo();
        updateUI();
    }

    /**
     * Implements the functionality to run commands
     */
    public void run(UIAction action) {
        undoHistory.addLast(action);
        redoHistory.clear();
        action.run();
        updateUI();
    }

    /**
     * Is used to update all ui elements after a command is executed, undone or redone
     */
    public abstract void updateUI();


    /**
     * Adds another TabController to the children of this TabController
     * @param tabController the child TabController
     */
    public void addChild(TabController tabController) {
        children.add(tabController);
    }

    /**
     * Deletes this TabController and all its children
     */
    public void delete() {
        parent.closeTab(this);
        children.forEach(TabController::delete);
    }

    /**
     * Removes a Solo object from the matching repository of this TabController
     * @param soloToRemove the Solo object to remove
     */
    public void removeSolo(Solo soloToRemove) {
        System.out.println("called on " + name);
        System.out.println(matchingRepository.getSoloDataCollection().contains(soloToRemove));
        matchingRepository.removeSoloData(soloToRemove);
        matchingRepository.removeSolo(soloToRemove);

        children.forEach(c -> c.removeSolo(soloToRemove));
        updateUI();
    }

    /**
     * Removes a Pair Object and the corresponding PairMatched Object
     * from the matching repository of this TabController
     * @param pairToRemove the Solo object to remove
     * @param pairMatchedToRemove the pairMatched Object to remove
     */
    public void removePair(PairMatched pairMatchedToRemove, Pair pairToRemove) {
        System.out.println("called on " + name);
        System.out.println(matchingRepository.getMatchedPairsCollection().contains(pairMatchedToRemove));
        matchingRepository.removePairData(pairToRemove);
        matchingRepository.removePair(pairMatchedToRemove);

        children.forEach(c -> c.removePair(pairMatchedToRemove, pairToRemove));
        updateUI();
    }

    /**
     * opens a new window that must be closed before the user can continue using the main window
     * @param root the content of the window
     * @param title the title of the window
     */
    protected void openPopupWindow(Parent root, String title) {
        ResourceBundle bundle = ResourceBundle.getBundle("uiElements",
                Settings.getInstance().getLocale());

        popupStage = new Stage();
        popupStage.setTitle(bundle.getString(title));
        popupStage.setScene(new Scene(root));
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.showAndWait();
    }

    /**
     * closes the new window
     */
    public void closePopupWindow() {
        popupStage.close();
    }

    /**
     * Opens a window where the user can choose the priorities of a MatchCostObject
     * which is used in the Matching Algorithms
     * @param onClose a function that is called when the window is closed
     */
    protected void openMatchCostChooserWindow(Consumer<MatchCosts> onClose) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PairBuilder.class.getResource("/MatchCostChooser.fxml"));
        fxmlLoader.setControllerFactory((Class<?> controllerClass) -> new MatchCostChooser(this, onClose));
        //Specify locale
        ResourceBundle bundle = ResourceBundle.getBundle("uiElements", Settings.getInstance().getLocale());
        fxmlLoader.setResources(bundle);
        Parent root = fxmlLoader.load();

        openPopupWindow(root, "matchCostChooser");
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
