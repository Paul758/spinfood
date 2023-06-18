package org.example.view.tools;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.data.structures.Solo;
import org.example.logic.metrics.PairMetrics;
import org.example.view.SoloDetailView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PairBuilder implements SoloTableListener {

    @FXML
    private AnchorPane paneA;
    @FXML
    private AnchorPane paneB;
    @FXML
    private AnchorPane detailViewAPane;
    @FXML
    private AnchorPane detailViewBPane;

    SoloTable soloTableA;
    SoloTable soloTableB;
    SoloDetailView detailViewA;
    SoloDetailView detailViewB;

    private List<Solo> soloList;

    @FXML
    public void handleFinishButton() {
        System.out.println("finish button");
    }

    public void setup(List<Solo> soloList) {
        this.soloList = new ArrayList<>(soloList);
        soloTableA = new SoloTable(this.soloList, paneA);
        soloTableA.addListener(this);

        try {
            detailViewA = SoloDetailView.createNewSoloDetailView(detailViewAPane);
            detailViewA.setup();
            detailViewB = SoloDetailView.createNewSoloDetailView(detailViewBPane);
            detailViewB.setup();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showPossibleMatches(Solo solo) {
        List<Solo> possibleMatches = soloList.stream()
                .filter(s -> PairMetrics.isValid(s, solo))
                .toList();

        soloTableB = new SoloTable(possibleMatches, paneB);
        soloTableB.addListener(this);
    }

    @Override
    public void onSelectItemLeftClick(Solo solo, SoloTable soloTable) {
        System.out.println("called");
        if (soloTableA != null && soloTableA.equals(soloTable)) {
            showPossibleMatches(solo);
            detailViewA.displaySolo(solo);
        } else if (soloTableB != null && soloTableB.equals(soloTable)) {
            detailViewB.displaySolo(solo);
        }
    }
}
