package org.example.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.Main;
import org.example.data.structures.Solo;
import org.example.logic.matchingalgorithms.MatchCosts;
import org.example.logic.structures.MatchingRepository;
import org.example.logic.structures.PairMatched;
import org.example.view.commands.CreatePairCommand;
import org.example.view.properties.PairListProperty;
import org.example.view.properties.PairMatchedProperty;
import org.example.view.properties.SoloProperty;
import org.example.view.commands.DisbandPair;
import org.example.view.tools.PairBuilder;
import org.example.view.tools.TableViewTools;
import org.example.view.tools.ViewTools;

import java.io.IOException;
import java.util.ArrayList;
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
    private TableView<PairListProperty> metricsTableView;
    @FXML
    private Text pairListMetricsText;
    @FXML
    private Button createPairButton;
    @FXML
    private Button matchGroupsButton;
    DataTabController dataTabController;
    private Main main;
    private MatchingRepository matchingRepository;
    private String name;
    private Stage pairBuilderStage;

    public void setup(DataTabController dataTabController, Main main, MatchCosts matchCosts, String name) {
        this.dataTabController = dataTabController;
        this.main = main;
        this.matchingRepository = dataTabController.getMatchingRepository();
        this.matchingRepository.matchPairs(matchCosts);
        this.name = name;
        updateUI();
    }

    public String getName() {
        return name;
    }

    @FXML
    public void dissolvePair() {
        System.out.println("called");
        PairMatchedProperty pairMatchedProperty = matchedPairsTableView.getSelectionModel().getSelectedItem();
        PairMatched pairMatched = pairMatchedProperty.pairMatched();
        DisbandPair disbandPair = new DisbandPair(matchingRepository, pairMatched);
        run(disbandPair);
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

    @Override
    public void closeMatchCost(MatchCosts matchCosts) {

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
        }
        pairBuilderStage.close();
    }

    @FXML
    public void createGroupTab() throws IOException {
        matchingRepository.matchGroups();
        main.createGroupTab(matchingRepository);
    }

    public MatchingRepository getMatchingRepository() {
        return matchingRepository;
    }
}
