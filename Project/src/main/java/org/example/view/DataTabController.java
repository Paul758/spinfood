package org.example.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.Main;
import org.example.data.DataManagement;
import org.example.data.structures.Pair;
import org.example.data.structures.Solo;
import org.example.logic.matchingalgorithms.MatchCosts;
import org.example.view.properties.PairProperty;
import org.example.view.properties.SoloProperty;
import org.example.view.tools.MatchCostChooser;
import org.example.view.tools.PairBuilder;
import org.example.view.tools.TableViewTools;
import org.example.view.tools.ViewTools;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DataTabController {
    @FXML
    private Button defaultValues;
    @FXML
    private AnchorPane soloTablePane, pairTablePane;
    @FXML
    private TableView<SoloProperty> soloTableView;
    @FXML
    private TableView<PairProperty> pairTableView;

    private DataManagement dataManagement;
    private Main main;
    private Stage matchCostStage;

    @FXML
    public void initialize() {
        System.out.println("initialize data tab controller");
    }

    public void setup(Main main) {
        this.main = main;
    }

    @FXML
    public void loadDefaultValues() {
        String participants = "src/main/java/org/example/artifacts/teilnehmerliste.csv";
        String party = "src/main/java/org/example/artifacts/partylocation.csv";
        dataManagement = new DataManagement(participants, party);

        TableViewTools.fillTable(dataManagement.soloParticipants, soloTableView, SoloProperty::new, SoloProperty.getColumnNames());
        TableViewTools.fillPairTable(dataManagement.pairParticipants, pairTableView);
    }

    @FXML
    public void createPairList() throws IOException {

        main.createPairTab(this, null);
    }

    public DataManagement getDataManagement() {
        return dataManagement;
    }

    @FXML
    public void showMatchCostChooserWindow() throws IOException {
        System.out.println("open match cost chooser");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(PairBuilder.class.getResource("/MatchCostChooser.fxml"));
        Parent root = fxmlLoader.load();

        MatchCostChooser matchCostChooser = fxmlLoader.getController();
        matchCostChooser.setup(this);

        matchCostStage = new Stage();
        matchCostStage.setTitle("Pair Builder");
        matchCostStage.setScene(new Scene(root));
        matchCostStage.initModality(Modality.APPLICATION_MODAL);
        matchCostStage.showAndWait();
    }

    public void closeMatchCostChooserWindow(MatchCosts matchCosts) throws IOException {
        matchCostStage.close();
        main.createPairTab(this, matchCosts);
    }
}
