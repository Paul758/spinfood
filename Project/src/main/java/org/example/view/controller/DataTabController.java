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

/**
 * The DataTabController handles the following tasks:
 * 1. import of the csv files
 * 2. displays all participants
 * 3. deletion of participants
 * 4. creation of  pair-list tabs
 */
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

    /**
     * Loads the default participants and party location
     */
    @FXML
    public void loadDefaultValues() {
        String participants = "Project/src/main/java/org/example/artifacts/teilnehmerliste.csv";
        String party = "Project/src/main/java/org/example/artifacts/partylocation.csv";
        DataManagement dataManagement = new DataManagement(participants, party);
        this.matchingRepository = new MatchingRepository(dataManagement);
        updateUI();
    }

    /**
     * Deletes a selected solo object from the matching repository
     */
    @FXML
    private void deleteSolo() {
        SoloProperty soloProperty = soloTableView.getSelectionModel().getSelectedItem();
        Solo soloToDelete = soloProperty.solo();
        this.removeSolo(soloToDelete);
    }

    /**
     * Deletes a selected pair object from the matching repository
     */
    @FXML
    private void deletePair() {
        PairProperty pairProperty = pairTableView.getSelectionModel().getSelectedItem();
        Pair pairToDelete = pairProperty.pair();
        PairMatched pairMatchedToDelete = new PairMatched(pairToDelete);
        this.removePair(pairMatchedToDelete, pairToDelete);
    }

    /**
     * Opens the Match Cost Chooser Window
     */
    @FXML
    public void showMatchCostChooserWindow() throws IOException {
        this.openMatchCostChooserWindow(this::closeMatchCostChooserWindow);
    }

    /**
     * Checks if all conditions are met so that a pair-list tab can be created
     */
    private void checkMatchPairsButton() {
        boolean notActive = matchingRepository == null
                || matchingRepository.dataManagement == null
                || matchingRepository.dataManagement.partyLocation == null;

        matchPairsButton.setDisable(notActive);
    }

    /**
     * Shows the window to select and open a participant list csv file
     */
    @FXML
    private void loadParticipants() {
        participantFilePath = getFilePath();
        participantPathText.setText(participantFilePath);
        checkFilesReady();
    }

    /**
     * Shows the window to select and open a party location csv file
     */
    @FXML
    private void loadPartyLocation() {
        partyLocationFilePath = getFilePath();
        partyLocationPathText.setText(partyLocationFilePath);
        checkFilesReady();
    }

    /**
     * Opens the file chooser and gets the file path of a selected csv file
     * @return the absolut path to a csv file
     */
    private String getFilePath() {
        Stage fileChooserStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showOpenDialog(fileChooserStage);
        return file.getAbsolutePath();
    }

    /**
     * Checks if a participant list csv file and party location csv file were loaded into this controller.
     * If that's the case, a DataManagement and MatchingRepository Object with the data from the files is created.
     */
    private void checkFilesReady() {
        if (participantFilePath != null && partyLocationFilePath != null) {
            DataManagement dataManagement = new DataManagement(participantFilePath, partyLocationFilePath);
            this.matchingRepository = new MatchingRepository(dataManagement);
            updateUI();
        }
    }

    /**
     * Updates the tables that display the solo and pair participants
     */
    @Override
    public void updateUI() {
        List<Solo> solos = new ArrayList<>(matchingRepository.getSoloDataCollection());
        TableViewTools.fillTable(solos, soloTableView, SoloProperty::new, SoloProperty.getDetailViewColumns());

        List<Pair> pairs = new ArrayList<>(matchingRepository.getPairDataCollection());
        TableViewTools.fillTable(pairs, pairTableView, PairProperty::new, PairProperty.getDetailViewColumns());

        checkMatchPairsButton();
    }

    /**
     * Creates a pair-list tab
     * @param matchCosts a MatchCost object
     */
    public void closeMatchCostChooserWindow(MatchCosts matchCosts) {
        try {
            parent.createPairTab(this, matchCosts);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
