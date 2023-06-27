package org.example.view.tools;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import org.example.data.structures.Solo;
import org.example.logic.metrics.PairMetrics;
import org.example.logic.structures.MatchingRepository;
import org.example.view.PairListTabController;
import org.example.view.commands.CreatePairCommand;
import org.example.view.properties.SoloProperty;

import java.util.ArrayList;
import java.util.List;

public class PairBuilder {
    @FXML
    private TableView<SoloProperty> tableViewA, tableViewB;
    @FXML
    private Text textA, textB;
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
    private void buildPair() {
        MatchingRepository matchingRepository = controller.getMatchingRepository();
        CreatePairCommand command = new CreatePairCommand(selectedSoloA, selectedSoloB, matchingRepository);
        controller.run(command);
        controller.closePopupWindow();
    }

    public void showPossibleMatches(Solo solo) {
        List<Solo> possibleMatches = soloList.stream()
                .filter(s -> PairMetrics.isValid(s, solo))
                .toList();

        TableViewTools.fillTable(possibleMatches, tableViewB, SoloProperty::new, SoloProperty.getColumnNames());
    }

    public void checkBuildPairButton() {
        boolean isDisabled = selectedSoloA == null || selectedSoloB == null;
        buildPairButton.setDisable(isDisabled);
    }

    @FXML
    public void selectSoloA() {
        SoloProperty soloProperty = tableViewA.getSelectionModel().getSelectedItem();
        selectedSoloA = soloProperty.getSolo();
        showPossibleMatches(selectedSoloA);
        checkBuildPairButton();

        textA.setText(ViewTools.getSoloData(selectedSoloA));
    }

    @FXML
    public void selectSoloB() {
        SoloProperty soloProperty = tableViewB.getSelectionModel().getSelectedItem();
        selectedSoloB = soloProperty.getSolo();
        checkBuildPairButton();

        textB.setText(ViewTools.getSoloData(selectedSoloB));
    }
}
