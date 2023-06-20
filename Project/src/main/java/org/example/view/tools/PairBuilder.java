package org.example.view.tools;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import org.example.data.structures.Solo;
import org.example.logic.metrics.PairMetrics;
import org.example.view.SoloDetailView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PairBuilder implements SoloTableListener {

    @FXML
    private AnchorPane tableAPane;
    @FXML
    private AnchorPane tableBPane;
    @FXML
    private AnchorPane detailViewAPane;
    @FXML
    private AnchorPane detailViewBPane;
    @FXML
    private Button buildPairButton;

    SoloTable soloTableA;
    SoloTable soloTableB;
    SoloDetailView detailViewA;
    SoloDetailView detailViewB;
    Solo selectedSoloA;
    Solo selectedSoloB;

    private List<Solo> soloList;

    @FXML
    public void handleFinishButton() {
        System.out.println("finish button");
    }

    public void setup(List<Solo> soloList) {
        this.soloList = new ArrayList<>(soloList);
        soloTableA = new SoloTable(this.soloList, tableAPane);
        soloTableA.addListener(this);

        try {
            detailViewA = SoloDetailView.createNewSoloDetailView(detailViewAPane);
            detailViewA.setup();
            detailViewB = SoloDetailView.createNewSoloDetailView(detailViewBPane);
            detailViewB.setup();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        checkBuildPairButton();
    }

    public void showPossibleMatches(Solo solo) {
        List<Solo> possibleMatches = soloList.stream()
                .filter(s -> PairMetrics.isValid(s, solo))
                .toList();

        soloTableB = new SoloTable(possibleMatches, tableBPane);
        soloTableB.addListener(this);
    }

    public void checkBuildPairButton() {
        boolean isDisabled = selectedSoloA == null || selectedSoloB == null;
        buildPairButton.setDisable(isDisabled);
    }

    public void setSoloA(Solo solo) {
        selectedSoloA = solo;
        showPossibleMatches(solo);
        detailViewA.displaySolo(solo);
    }

    public void setSoloB(Solo solo) {
        selectedSoloB = solo;
        detailViewB.displaySolo(solo);
    }

    @Override
    public void onSelectItemLeftClick(Solo solo, SoloTable soloTable) {
        System.out.println("called");
        if (soloTableA != null && soloTableA.equals(soloTable)) {
            setSoloA(solo);
        } else if (soloTableB != null && soloTableB.equals(soloTable)) {
            setSoloB(solo);
        }
        checkBuildPairButton();
    }
}
