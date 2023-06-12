package org.example.view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.data.DataManagement;
import org.example.data.structures.Solo;
import org.example.data.tools.CSVReader;
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
    private ListView<PairMatched> listViewPairs;

    Stage primaryStage;
    MatchingRepository matchingRepository;

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
