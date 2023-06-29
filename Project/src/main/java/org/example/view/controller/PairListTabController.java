package org.example.view.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import org.example.Main;
import org.example.data.structures.Solo;
import org.example.logic.matchingalgorithms.MatchCosts;
import org.example.logic.structures.MatchingRepository;
import org.example.logic.structures.PairMatched;
import org.example.view.properties.PairListProperty;
import org.example.view.properties.PairMatchedProperty;
import org.example.view.properties.SoloProperty;
import org.example.view.commands.DisbandPairCommand;
import org.example.view.tools.Settings;
import org.example.view.windows.PairBuilder;
import org.example.view.tools.TableViewTools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PairListTabController extends TabController {

    @FXML
    private TableView<SoloProperty> successorTableView;
    @FXML
    private TableView<PairMatchedProperty> matchedPairsTableView;
    @FXML
    private TableView<PairListProperty> metricsTableView;
    @FXML
    private Button createPairButton;
    @FXML
    private Button matchGroupsButton;

    public PairListTabController(MatchingRepository matchingRepository, Main parent, String name) {
        super(matchingRepository, parent, name);
    }

    @FXML
    private void initialize() {
        updateUI();
    }

    @FXML
    public void disbandPair() {
        PairMatchedProperty pairMatchedProperty = matchedPairsTableView.getSelectionModel().getSelectedItem();
        PairMatched pairMatched = pairMatchedProperty.pairMatched();
        DisbandPairCommand disbandPairCommand = new DisbandPairCommand(matchingRepository, pairMatched);
        run(disbandPairCommand);
    }

    @Override
    public void updateUI() {
        List<Solo> soloSuccessors = new ArrayList<>(matchingRepository.soloSuccessors);
        TableViewTools.fillTable(soloSuccessors, successorTableView, SoloProperty::new, SoloProperty.getColumnNames());

        List<PairMatched> pairs = new ArrayList<>(matchingRepository.getMatchedPairsCollection());
        TableViewTools.fillTable(pairs, matchedPairsTableView, PairMatchedProperty::new, PairMatchedProperty.getColumnNames());

        List<PairListTabController> controllers = List.of(this);
        TableViewTools.fillTable(controllers, metricsTableView, PairListProperty::new, PairListProperty.getColumnNames());
    }

    @FXML
    public void createGroupTab() throws IOException {
        this.openMatchCostChooserWindow(this::closeMatchCostChooserWindow);
    }

    public void closeMatchCostChooserWindow(MatchCosts matchCosts) {
        try {
            parent.createGroupTab(this, matchCosts);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void openPairBuilder() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/PairBuilder.fxml"));
        fxmlLoader.setControllerFactory((Class<?> controllerClass) -> {
            List<Solo> successors = new ArrayList<>(matchingRepository.soloSuccessors);
            return new PairBuilder(successors, this);
        });

        ResourceBundle bundle = ResourceBundle.getBundle("PairBuilderBundle", Settings.getInstance().getLocale());
        fxmlLoader.setResources(bundle);

        Parent root = fxmlLoader.load();
        openPopupWindow(root, "Pair Builder");
    }
}
