package org.example.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.data.structures.Solo;
import org.example.logic.structures.MatchingRepository;
import org.example.logic.structures.PairMatched;
import org.example.view.commands.CreatePairCommand;
import org.example.view.properties.PairMatchedProperty;
import org.example.view.properties.SoloProperty;
import org.example.view.commands.DisbandPair;
import org.example.view.tools.PairBuilder;
import org.example.view.tools.TableViewTools;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

public class PairListTabController extends TabController {

    @FXML
    private AnchorPane successorTablePane;
    @FXML
    private TableView<SoloProperty> successorTableView;
    @FXML
    private TableView<PairMatchedProperty> matchedPairsTableView;
    @FXML
    private Button createPairButton;
    DataTabController dataTabController;
    private MatchingRepository matchingRepository;
    private Stage pairBuilderStage;

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
        run(disbandPair);
        updateTables();
    }

    @Override
    public void updateUI() {
        updateTables();
    }

    @FXML
    public void openPairBuilder() throws IOException {
        System.out.println("Open pair builder");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(PairBuilder.class.getResource("/PairBuilder.fxml"));

        //Specify locale resources
        ResourceBundle bundle = ResourceBundle.getBundle("PairBuilderBundle", Settings.getInstance().getLocale());
        fxmlLoader.setResources(bundle);

        Parent root = fxmlLoader.load();

        PairBuilder pairBuilder = fxmlLoader.getController();
        pairBuilder.setup((List<Solo>) matchingRepository.soloSuccessors, this);

        pairBuilderStage = new Stage();
        pairBuilderStage.setTitle("Pair Builder");
        pairBuilderStage.setScene(new Scene(root));
        pairBuilderStage.initModality(Modality.APPLICATION_MODAL);
        pairBuilderStage.showAndWait();
    }

    public void closePairBuilder(Solo soloA, Solo soloB) {
        if (soloA != null && soloB != null) {
            CreatePairCommand createPairCommand = new CreatePairCommand(soloA, soloB, matchingRepository);
            run(createPairCommand);
            updateTables();
        }
        pairBuilderStage.close();
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
