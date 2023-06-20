package org.example.view;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import org.example.data.structures.Solo;
import org.example.logic.structures.MatchingRepository;
import org.example.logic.structures.PairMatched;
import org.example.view.properties.PairMatchedProperty;
import org.example.view.properties.SoloProperty;
import org.example.view.tools.DisbandPair;
import org.example.view.tools.SoloTable;
import org.example.view.tools.TableViewTools;

import java.util.List;

public class PairListTabController {

    @FXML
    private AnchorPane successorTablePane;
    @FXML
    private TableView<SoloProperty> successorTableView;
    @FXML
    private TableView<PairMatchedProperty> matchedPairsTableView;
    DataTabController dataTabController;
    private MatchingRepository matchingRepository;

    public void setup(DataTabController dataTabController) {
        this.dataTabController = dataTabController;
        matchingRepository = new MatchingRepository(dataTabController.getDataManagement());
        matchingRepository.matchPairs();
        updateTables();
    }

    @FXML
    public void dissolvePair() {
        System.out.println("called");
        PairMatchedProperty pairMatchedProperty = matchedPairsTableView.getSelectionModel().getSelectedItem();
        PairMatched pairMatched = pairMatchedProperty.getPairMatched();
        DisbandPair disbandPair = new DisbandPair(matchingRepository, pairMatched);
        disbandPair.run();
        updateTables();
    }

    private void updateTables() {
        successorTableView.getItems().clear();
        successorTableView.getColumns().clear();
        matchedPairsTableView.getItems().clear();
        matchedPairsTableView.getColumns().clear();

        successorTableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        matchedPairsTableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        List<Solo> soloSuccessors = (List<Solo>) matchingRepository.soloSuccessors;
        TableViewTools.fillSoloTable(soloSuccessors, successorTableView);
        List<PairMatched> pairs = (List<PairMatched>) matchingRepository.getMatchedPairsCollection();
        TableViewTools.fillPairMatchedTable(pairs, matchedPairsTableView);

        successorTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        matchedPairsTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
    }
}
