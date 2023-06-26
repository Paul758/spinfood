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
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.Main;
import org.example.data.DataManagement;
import org.example.data.structures.Pair;
import org.example.data.structures.Solo;
import org.example.logic.matchingalgorithms.MatchCosts;
import org.example.logic.structures.MatchingRepository;
import org.example.view.properties.PairProperty;
import org.example.view.properties.SoloProperty;
import org.example.view.tools.MatchCostChooser;
import org.example.view.tools.PairBuilder;
import org.example.view.tools.TableViewTools;
import org.example.view.tools.ViewTools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DataTabController extends TabController {

    @FXML
    private TableView<SoloProperty> soloTableView;
    @FXML
    private TableView<PairProperty> pairTableView;
    @FXML
    private Text participantPathText, partyLocationPathText;
    @FXML
    private Button loadParticipantsButton, loadPartyLocationButton, matchPairsButton;

    String participantFilePath, partyLocationFilePath;
    private DataManagement dataManagement;
    private Main main;
    private Stage matchCostStage;
    private List<PairListTabController> children;

    public void setup(Main main) {
        this.main = main;
        checkMatchPairsButton();
    }

    @FXML
    public void loadDefaultValues() {
        String participants = "src/main/java/org/example/artifacts/teilnehmerliste.csv";
        String party = "src/main/java/org/example/artifacts/partylocation.csv";
        DataManagement dataManagement = new DataManagement(participants, party);
        this.matchingRepository = new MatchingRepository(dataManagement);
        updateUI();
    }

    @FXML
    public void createPairList() throws IOException {
        main.createPairTab(this, null);
    }

    @FXML
    public void showMatchCostChooserWindow() throws IOException {
        this.openMatchCostChooserWindow();
    }

    private void checkMatchPairsButton() {
        boolean notActive = matchingRepository == null
                || matchingRepository.dataManagement == null
                || matchingRepository.dataManagement.partyLocation == null;

        matchPairsButton.setDisable(notActive);
    }

    @FXML
    private void loadParticipants() {
        participantFilePath = getFilePath();
        participantPathText.setText(participantFilePath);
        checkFilesReady();
    }

    @FXML
    private void loadPartyLocation() {
        partyLocationFilePath = getFilePath();
        partyLocationPathText.setText(partyLocationFilePath);
        checkFilesReady();
    }

    private String getFilePath() {
        Stage fileChooserStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showOpenDialog(fileChooserStage);
        return file.getAbsolutePath();
    }

    private void checkFilesReady() {
        if (participantFilePath != null && partyLocationFilePath != null) {
            DataManagement dataManagement = new DataManagement(participantFilePath, partyLocationFilePath);
            this.matchingRepository = new MatchingRepository(dataManagement);
            updateUI();
        }
    }

    @Override
    public void updateUI() {
        List<Solo> solos = new ArrayList<>(matchingRepository.dataManagement.soloParticipants);
        TableViewTools.fillTable(solos, soloTableView, SoloProperty::new, SoloProperty.getColumnNames());
        List<Pair> pairs = new ArrayList<>(matchingRepository.dataManagement.pairParticipants);
        TableViewTools.fillTable(pairs, pairTableView, PairProperty::new, PairProperty.getColumnNames());
        checkMatchPairsButton();
    }

    @Override
    public void closeMatchCost(MatchCosts matchCosts) {
        try {
            main.createPairTab(this, matchCosts);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
