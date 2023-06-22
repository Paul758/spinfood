package org.example.view.tools;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.example.data.structures.Solo;
import org.example.logic.metrics.PairMetrics;
import org.example.logic.structures.PairMatched;
import org.example.view.PairListTabController;
import org.example.view.SoloDetailView;
import org.example.view.properties.SoloProperty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PairBuilder {
    @FXML
    private TableView<SoloProperty> tableViewA, tableViewB;
    @FXML
    private Text textA, textB;
    @FXML
    private Button buildPairButton;

    PairListTabController pairListTabController;
    Solo selectedSoloA;
    Solo selectedSoloB;

    private List<Solo> soloList;

    public void setup(List<Solo> soloList, PairListTabController pairListTabController) {
        this.soloList = new ArrayList<>(soloList);
        this.pairListTabController = pairListTabController;
        List<Solo> emptyList = new ArrayList<>();

        TableViewTools.fillTable(soloList, tableViewA, SoloProperty::new, SoloProperty.getColumnNames());
        TableViewTools.fillTable(emptyList, tableViewB, SoloProperty::new, SoloProperty.getColumnNames());

        setupMouseEvents();
        checkBuildPairButton();
    }

    private void setupMouseEvents() {
        tableViewA.setOnMouseClicked((MouseEvent e) -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                System.out.println("called left click a");
                SoloProperty soloProperty = tableViewA.getSelectionModel().getSelectedItem();
                Solo solo = soloProperty.getSolo();
                setSoloA(solo);
            }
        });

        tableViewB.setOnMouseClicked((MouseEvent e) -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                System.out.println("called left click b");
                SoloProperty soloProperty = tableViewB.getSelectionModel().getSelectedItem();
                Solo solo = soloProperty.getSolo();
                setSoloB(solo);
            }
        });
    }

    @FXML
    private void buildPair() {
        pairListTabController.closePairBuilder(selectedSoloA, selectedSoloB);
    }

    public void showPossibleMatches(Solo solo) {
        List<Solo> possibleMatches = soloList.stream()
                .filter(s -> PairMetrics.isValid(s, solo))
                .toList();

        tableViewB.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        tableViewB.getItems().clear();
        tableViewB.getColumns().clear();
        TableViewTools.fillTable(possibleMatches, tableViewB, SoloProperty::new, SoloProperty.getColumnNames());
        tableViewB.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
    }

    public void checkBuildPairButton() {
        boolean isDisabled = selectedSoloA == null || selectedSoloB == null;
        System.out.println("can build pair: " + !isDisabled);
        buildPairButton.setDisable(isDisabled);
    }

    public void setSoloA(Solo solo) {
        selectedSoloA = solo;
        showPossibleMatches(solo);
        checkBuildPairButton();
        textA.setText(ViewTools.getSoloData(solo));
    }

    public void setSoloB(Solo solo) {
        selectedSoloB = solo;
        checkBuildPairButton();
        textB.setText(ViewTools.getSoloData(solo));
    }
}
