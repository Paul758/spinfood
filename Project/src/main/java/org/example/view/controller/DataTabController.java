package org.example.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.Main;
import org.example.data.DataManagement;
import org.example.data.structures.Pair;
import org.example.data.structures.Solo;
import org.example.logic.matchingalgorithms.MatchCosts;
import org.example.logic.structures.MatchingRepository;
import org.example.logic.structures.PairMatched;
import org.example.view.properties.PairProperty;
import org.example.view.properties.SoloProperty;
import org.example.view.tools.TableViewTools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataTabController extends TabController {

    @FXML
    private TableView<SoloProperty> soloTableView;
    @FXML
    private TableView<PairProperty> pairTableView;
    @FXML
    private Text participantPathText, partyLocationPathText;
    @FXML
    private Button loadParticipantsButton, loadPartyLocationButton, matchPairsButton;
    private String participantFilePath, partyLocationFilePath;

    public DataTabController(MatchingRepository matchingRepository, Main parent, String name) {
        super(matchingRepository, parent, name);
    }

    @FXML
    private void initialize() {
        checkMatchPairsButton();
    }

    @FXML
    public void loadDefaultValues() {
        String participants = "Project/src/main/java/org/example/artifacts/teilnehmerliste.csv";
        String party = "Project/src/main/java/org/example/artifacts/partylocation.csv";
        DataManagement dataManagement = new DataManagement(participants, party);
        this.matchingRepository = new MatchingRepository(dataManagement);
        updateUI();
    }

    @FXML
    private void deleteSolo() {
        SoloProperty soloProperty = soloTableView.getSelectionModel().getSelectedItem();
        Solo soloToDelete = soloProperty.getSolo();
        this.removeSolo(soloToDelete);
    }

    @FXML
    private void deletePair() {
        PairProperty pairProperty = pairTableView.getSelectionModel().getSelectedItem();
        Pair pairToDelete = pairProperty.pair();
        PairMatched pairMatchedToDelete = new PairMatched(pairToDelete);
        this.removePair(pairMatchedToDelete, pairToDelete);
    }

    @FXML
    public void showMatchCostChooserWindow() throws IOException {
        this.openMatchCostChooserWindow(this::closeMatchCostChooserWindow);
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
        List<Solo> solos = new ArrayList<>(matchingRepository.getSoloDataCollection());
        TableViewTools.fillTable(solos, soloTableView, SoloProperty::new, SoloProperty.getColumnNames());

        List<Pair> pairs = new ArrayList<>(matchingRepository.getPairDataCollection());
        TableViewTools.fillTable(pairs, pairTableView, PairProperty::new, PairProperty.getColumnNames());

        checkMatchPairsButton();
    }

    public void closeMatchCostChooserWindow(MatchCosts matchCosts) {
        try {
            parent.createPairTab(this, matchCosts);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
