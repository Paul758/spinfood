package org.example.view.windows;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import org.example.data.structures.Solo;
import org.example.logic.metrics.PairMetrics;
import org.example.logic.structures.MatchingRepository;
import org.example.view.controller.PairListTabController;
import org.example.view.commands.CreatePairCommand;
import org.example.view.properties.SoloProperty;
import org.example.view.tools.TableViewTools;

import java.util.ArrayList;
import java.util.List;

/**
 * The functionality for the window where the user can select to solo participants and can create
 * a new pair from them
 */
public class PairBuilder {
    @FXML
    private TableView<SoloProperty> tableViewA, tableViewB;
    @FXML
    private Button buildPairButton;

    private final List<Solo> soloList;
    private final PairListTabController controller;
    private Solo selectedSoloA;
    private Solo selectedSoloB;

    public PairBuilder(List<Solo> successors, PairListTabController controller) {
        this.soloList = new ArrayList<>(successors);
        this.controller = controller;
    }

    @FXML
    private void initialize() {
        TableViewTools.fillTable(soloList, tableViewA, SoloProperty::new, SoloProperty.getSummaryViewColumns());
        checkBuildPairButton();
    }

    /**
     * gets the solo participant which the user has selected in the solo a table view
     */
    @FXML
    private void selectSoloA() {
        SoloProperty soloProperty = tableViewA.getSelectionModel().getSelectedItem();
        if (soloProperty != null) {
            selectedSoloA = soloProperty.solo();
            showPossibleMatches(selectedSoloA);
            checkBuildPairButton();
        }
    }

    /**
     * gets the solo participant which the user has selected in the solo b table view
     */
    @FXML
    private void selectSoloB() {
        SoloProperty soloProperty = tableViewB.getSelectionModel().getSelectedItem();
        if (soloProperty != null) {
            selectedSoloB = soloProperty.solo();
            checkBuildPairButton();
        }
    }

    /**
     * creates a new pair from the selected solo participants and closes the window
     */
    @FXML
    private void buildPair() {
        MatchingRepository matchingRepository = controller.getMatchingRepository();
        CreatePairCommand command = new CreatePairCommand(selectedSoloA, selectedSoloB, matchingRepository);
        controller.run(command);
        controller.closePopupWindow();
    }

    /**
     * Shows in table view b only the valid matches for the given solo participant
     * @param solo a Solo Object
     */
    private void showPossibleMatches(Solo solo) {
        List<Solo> possibleMatches = soloList.stream()
                .filter(s -> PairMetrics.isValid(s, solo))
                .toList();

        TableViewTools.fillTable(possibleMatches, tableViewB, SoloProperty::new, SoloProperty.getSummaryViewColumns());
    }

    /**
     * Checks if two solo objects are selected
     */
    private void checkBuildPairButton() {
        boolean isDisabled = selectedSoloA == null || selectedSoloB == null;
        buildPairButton.setDisable(isDisabled);
    }
}
