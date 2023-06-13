package org.example.view;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.data.DataManagement;
import org.example.data.structures.Solo;
import org.example.data.tools.CSVReader;
import org.example.logic.metrics.PairMetrics;
import org.example.logic.structures.MatchingRepository;
import javafx.event.ActionEvent;
import org.example.logic.structures.PairMatched;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ApplicationEntry extends Application {

    @FXML
    private Button buttonFile;

    @FXML
    private ListView<Solo> listViewSoloRegistration;


    @FXML
    private TableView<PairMatched> tableViewMatchedPairs;

    @FXML
    private TableColumn<PairMatched, String> tableColumnFirstPerson;
    @FXML
    private TableColumn<PairMatched, String> tableColumnFirstPersonGender;
    @FXML
    private TableColumn<PairMatched, String> tableColumnFirstPersonAge;
    @FXML
    private TableColumn<PairMatched, String> tableColumnFirstPersonFoodPreference;

    @FXML
    private TableColumn<PairMatched, String> tableColumnSecondPerson;
    @FXML
    private TableColumn<PairMatched, String> tableColumnSecondPersonGender;
    @FXML
    private TableColumn<PairMatched, String> tableColumnSecondPersonAge;
    @FXML
    private TableColumn<PairMatched, String> tableColumnSecondPersonFoodPreference;

    @FXML
    private TableColumn<PairMatched, String> tableColumnAgeDifference;
    @FXML
    private TableColumn<PairMatched, String> tableColumnGenderDiversity;
    @FXML
    private TableColumn<PairMatched, String> tableColumnFoodPreferenceDeviation;


    @FXML
    private TableView<Solo> tableViewUnmatchedSolos;

    @FXML
    private TableColumn<Solo, String> tableColumnUnmatchedPerson;
    @FXML
    private TableColumn<Solo, String> tableColumnUnmatchedPersonGender;
    @FXML
    private TableColumn<Solo, String> tableColumnUnmatchedPersonAge;
    @FXML
    private TableColumn<Solo, String> tableColumnUnmatchedPersonFoodPreference;




    @FXML
    private ListView<PairMatched> listViewPairs;

    Stage primaryStage;
    MatchingRepository matchingRepository;

    public ApplicationEntry() {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ApplicationScene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("FoodSpin");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    @FXML
    private void handleButtonFileClick(ActionEvent event) {
        List<File> selectedFiles = selectFiles(primaryStage);
        matchingRepository = new MatchingRepository(new DataManagement(selectedFiles.get(0).getAbsolutePath(), selectedFiles.get(1).getAbsolutePath()));
        System.out.println(matchingRepository.getMatchedPairsCollection().toString());


        ShowParticipants();
        populatePairMatchedTable();
        populateUnmatchedSoloTable();
    }

    private void ShowParticipants() {
        ObservableList<Solo> participantObservableList = FXCollections.observableArrayList(matchingRepository.getSoloDataCollection());
        listViewSoloRegistration.setItems(participantObservableList);

        // Set a custom CellFactory for the ListView
        listViewSoloRegistration.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Solo> call(ListView<Solo> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Solo eventParticipant, boolean empty) {
                        super.updateItem(eventParticipant, empty);
                        if (eventParticipant != null) {
                            setText(eventParticipant.person.name());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
    }


    private void populatePairMatchedTable() {
        tableColumnFirstPersonGender.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSoloA().person.sex().toString()));
        tableColumnFirstPersonAge.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getSoloA().person.age())));
        tableColumnFirstPersonFoodPreference.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSoloA().foodPreference.toString()));

        tableColumnSecondPersonGender.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSoloB().person.sex().toString()));
        tableColumnSecondPersonAge.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getSoloB().person.age())));
        tableColumnSecondPersonFoodPreference.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSoloB().foodPreference.toString()));

        tableColumnAgeDifference.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(PairMetrics.calcAgeDifference(data.getValue()))));
        tableColumnGenderDiversity.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(PairMetrics.calcGenderDiversity(data.getValue()))));
        tableColumnFoodPreferenceDeviation.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(PairMetrics.calcPreferenceDeviation(data.getValue()))));
        ObservableList<PairMatched> pairMatchedObservableList = FXCollections.observableArrayList();
        pairMatchedObservableList.addAll(matchingRepository.getMatchedPairsCollection());

        tableViewMatchedPairs.setItems(pairMatchedObservableList);
    }

    private void populateUnmatchedSoloTable() {
        tableColumnUnmatchedPersonGender.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().person.sex().toString()));
        tableColumnUnmatchedPersonAge.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().person.age())));
        tableColumnUnmatchedPersonFoodPreference.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().foodPreference.toString()));
        ObservableList<Solo> soloObservableList = FXCollections.observableArrayList();
        soloObservableList.addAll(matchingRepository.soloSuccessors);

        tableViewUnmatchedSolos.setItems(soloObservableList);
    }


    private List<File> selectFiles(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        List<File> dataFiles = new ArrayList<>();
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(primaryStage);
        File partyLocation = new File("-1");
        File participantList = new File("-1");
        if (selectedFiles != null) {
            for (File file : selectedFiles) {
                if(CSVReader.isPartyLocation(file)) {
                    partyLocation = file;
                } else {
                    participantList = file;
                }
            }
        } else {
            System.out.println("no files selected");
        }
        System.out.println("The party location is " + partyLocation.getAbsolutePath());
        System.out.println("The participant list is " + participantList.getAbsolutePath());
        dataFiles.add(participantList);
        dataFiles.add(partyLocation);
        return dataFiles;

    }

    public static void main(String[] args) {
        launch(args);
    }
}
