package org.example.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.example.Main;
import org.example.data.DataManagement;
import org.example.data.structures.Pair;
import org.example.data.structures.Solo;
import org.example.view.properties.PairProperty;
import org.example.view.properties.SoloProperty;
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

        TableViewTools.fillSoloTable(dataManagement.soloParticipants, soloTableView);
        TableViewTools.fillPairTable(dataManagement.pairParticipants, pairTableView);
    }

    @FXML
    public void createPairList() throws IOException {
        main.createPairTab(this);
    }

    public DataManagement getDataManagement() {
        System.out.println("Datamanagment: " +  dataManagement == null);
        return dataManagement;
    }
}
