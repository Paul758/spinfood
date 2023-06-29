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
        TableViewTools.fillTable(soloList, tableViewA, SoloProperty::new, SoloProperty.getColumnNames());
        checkBuildPairButton();
    }

    @FXML
    private void selectSoloA() {
        SoloProperty soloProperty = tableViewA.getSelectionModel().getSelectedItem();
        if (soloProperty != null) {
            selectedSoloA = soloProperty.getSolo();
            showPossibleMatches(selectedSoloA);
            checkBuildPairButton();
        }
    }

    @FXML
    private void selectSoloB() {
        SoloProperty soloProperty = tableViewB.getSelectionModel().getSelectedItem();
        if (soloProperty != null) {
            selectedSoloB = soloProperty.getSolo();
            checkBuildPairButton();
        }
    }

    @FXML
    private void buildPair() {
        MatchingRepository matchingRepository = controller.getMatchingRepository();
        CreatePairCommand command = new CreatePairCommand(selectedSoloA, selectedSoloB, matchingRepository);
        controller.run(command);
        controller.closePopupWindow();
    }

    private void showPossibleMatches(Solo solo) {
        List<Solo> possibleMatches = soloList.stream()
                .filter(s -> PairMetrics.isValid(s, solo))
                .toList();

        TableViewTools.fillTable(possibleMatches, tableViewB, SoloProperty::new, SoloProperty.getColumnNames());
    }

    private void checkBuildPairButton() {
        boolean isDisabled = selectedSoloA == null || selectedSoloB == null;
        buildPairButton.setDisable(isDisabled);
    }
}
