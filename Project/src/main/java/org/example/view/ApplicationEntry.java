package org.example.view;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.data.DataManagement;
import org.example.data.enums.FoodPreference;
import org.example.data.structures.Solo;
import org.example.logic.metrics.PairMetrics;
import org.example.logic.structures.GroupMatched;
import org.example.logic.structures.MatchingRepository;
import javafx.event.ActionEvent;
import org.example.logic.structures.PairMatched;
import org.example.view.tools.DisbandPair;
import org.example.view.tools.FileSelectionManager;

import java.io.File;
import java.util.ArrayDeque;
import java.util.List;

public class ApplicationEntry extends Application {

    ArrayDeque<UIAction> history = new ArrayDeque<>();

    @FXML
    private Button buttonRemove;

    @FXML
    private Button buttonUndo;

    @FXML
    private Button buttonFile;
    @FXML
    private ListView<Solo> listViewSoloRegistration;

    //Matched pairs table
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
    //Pair Metrics
    @FXML
    private TableColumn<PairMatched, String> tableColumnAgeDifference;
    @FXML
    private TableColumn<PairMatched, String> tableColumnGenderDiversity;
    @FXML
    private TableColumn<PairMatched, String> tableColumnFoodPreferenceDeviation;

    //Table view for unmatched Solos
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

    //Table view for matchedGroups
    @FXML
    private TableView<GroupMatched> tableViewGroupMatched;
    @FXML
    private TableColumn<GroupMatched, String> tableColumnCookingPair;
    @FXML
    private TableColumn<GroupMatched, String> tableColumnCookingPairGender;
    @FXML
    private TableColumn<GroupMatched, String> tableColumnCookingPairAge;
    @FXML
    private TableColumn<GroupMatched, String> tableColumnCookingPairFoodPreference;
    @FXML
    private TableColumn<GroupMatched, String> tableColumnSecondPair;
    @FXML
    private TableColumn<GroupMatched, String> tableColumnSecondPairGender;
    @FXML
    private TableColumn<GroupMatched, String> tableColumnSecondPairAge;
    @FXML
    private TableColumn<GroupMatched, String> tableColumnSecondPairFoodPreference;
    @FXML
    private TableColumn<GroupMatched, String> tableColumnThirdPair;
    @FXML
    private TableColumn<GroupMatched, String> tableColumnThirdPairGender;
    @FXML
    private TableColumn<GroupMatched, String> tableColumnThirdPairAge;
    @FXML
    private TableColumn<GroupMatched, String> tableColumnThirdPairFoodPreference;


    @FXML
    private ListView<PairMatched> listViewPairs;
    Stage primaryStage;
    MatchingRepository matchingRepository;

    PairMatched pairToDisband;

    GroupMatched groupToDisband;

    public ApplicationEntry() {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ApplicationScene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Spinfood");
        primaryStage.setScene(scene);
        primaryStage.show();
        //buttonRemove.setManaged(false);
        //Test
    }

    private void testMethod() {
        System.out.println("Test");
    }

    @FXML
    private void handleButtonFileClick(ActionEvent event) {
        FileSelectionManager fileChooser = new FileSelectionManager(primaryStage);
        List<File> selectedFiles = fileChooser.selectFiles();
        matchingRepository = new MatchingRepository(new DataManagement(selectedFiles.get(0).getAbsolutePath(), selectedFiles.get(1).getAbsolutePath()));

        ShowParticipants();
        populatePairMatchedTable();
        populateUnmatchedSoloTable();
        populateGroupMatchedTable();
        setTableMouseEvents();

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


    private void populateGroupMatchedTable() {

        //first pair
        tableColumnCookingPairGender.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCook().getSoloA().person.sex().toString() + ", " +
                data.getValue().getCook().getSoloB().person.sex().toString()));

        tableColumnCookingPairAge.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getCook().getSoloA().person.age() + ", " +
                data.getValue().getCook().getSoloB().person.age())));

        tableColumnCookingPairFoodPreference.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getCook().getSoloA().foodPreference + ", " +
                data.getValue().getCook().getSoloB().foodPreference)));

        //second pair
        tableColumnSecondPairGender.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPairList().get(1).getSoloA().person.sex().toString() + ", " +
                data.getValue().getPairList().get(1).getSoloB().person.sex().toString()));

        tableColumnSecondPairAge.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getPairList().get(1).getSoloA().person.age() + ", " +
                data.getValue().getPairList().get(1).getSoloB().person.age())));

        tableColumnSecondPairFoodPreference.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getPairList().get(1).getSoloA().foodPreference + ", " +
                data.getValue().getPairList().get(1).getSoloB().foodPreference)));

        //third pair
        tableColumnThirdPairGender.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPairList().get(2).getSoloA().person.sex().toString() + ", " +
                data.getValue().getPairList().get(2).getSoloB().person.sex().toString()));

        tableColumnThirdPairAge.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getPairList().get(2).getSoloA().person.age() + ", " +
                data.getValue().getPairList().get(2).getSoloB().person.age())));

        tableColumnThirdPairFoodPreference.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getPairList().get(2).getSoloA().foodPreference + ", " +
                data.getValue().getPairList().get(2).getSoloB().foodPreference)));

        ObservableList<GroupMatched> pairMatchedObservableList = FXCollections.observableArrayList();
        pairMatchedObservableList.addAll(matchingRepository.getMatchedGroupsCollection());

        tableViewGroupMatched.setItems(pairMatchedObservableList);
    }



    private void populateUnmatchedSoloTable() {
        tableColumnUnmatchedPersonGender.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().person.sex().toString()));
        tableColumnUnmatchedPersonAge.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().person.age())));
        tableColumnUnmatchedPersonFoodPreference.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().foodPreference.toString()));
        tableColumnUnmatchedPersonFoodPreference.setCellFactory(column ->
                getTableCellImage()
        );
        ObservableList<Solo> soloObservableList = FXCollections.observableArrayList();
        soloObservableList.addAll(matchingRepository.soloSuccessors);
        System.out.println("Count of solo successors now is: " + matchingRepository.soloSuccessors.size());
        tableViewUnmatchedSolos.setItems(soloObservableList);
    }

    private void populatePairMatchedTable() {
        tableColumnFirstPersonGender.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSoloA().person.sex().toString()));
        tableColumnFirstPersonAge.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getSoloA().person.age())));
        tableColumnFirstPersonFoodPreference.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSoloA().foodPreference.toString()));
        tableColumnFirstPersonFoodPreference.setCellFactory(column ->
            getTableCellImage()
        );

        tableColumnSecondPersonGender.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSoloB().person.sex().toString()));
        tableColumnSecondPersonAge.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getSoloB().person.age())));
        tableColumnSecondPersonFoodPreference.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSoloB().foodPreference.toString()));
        tableColumnSecondPersonFoodPreference.setCellFactory(column ->
            getTableCellImage()
        );


        tableColumnAgeDifference.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(PairMetrics.calcAgeDifference(data.getValue()))));
        tableColumnGenderDiversity.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(PairMetrics.calcGenderDiversity(data.getValue()))));
        tableColumnFoodPreferenceDeviation.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(PairMetrics.calcPreferenceDeviation(data.getValue()))));

        ObservableList<PairMatched> pairMatchedObservableList = FXCollections.observableArrayList();
        pairMatchedObservableList.addAll(matchingRepository.getMatchedPairsCollection());

        tableViewMatchedPairs.setItems(pairMatchedObservableList);

    }


    private <T,S> TableCell<T, String> getTableCellImage() {
        return new TableCell<T, String>() {
            private final ImageView imageView = new ImageView();
            private final double imageWidth = 38; // Adjust the width as needed
            private final double imageHeight = 38; // Adjust the height as needed

            @Override
            protected void updateItem(String foodPreference, boolean empty) {
                super.updateItem(foodPreference, empty);

                if (foodPreference == null || empty) {
                    setGraphic(null);
                } else {
                    imageView.setImage(getFoodIcon(foodPreference));
                    imageView.setFitWidth(imageWidth);
                    imageView.setFitHeight(imageHeight);
                    setGraphic(imageView);
                }
            }
        };
    }

    private Image getFoodIcon(String foodPreference) {
        System.out.println("food preference " + foodPreference);
        //this.getClass().getResource("/images/meat.png");
        Image image = null;
        switch (foodPreference) {
            case "MEAT" -> image = new Image("meat.png");
            case "VEGGIE" -> image = new Image("veggie.png");
            case "VEGAN" -> image = new Image("vegan.png");
            case "NONE" -> image = new Image("none.png");
        }
        System.out.println(image.toString());
        return image;
    }


    private void setTableMouseEvents() {
        tableViewMatchedPairs.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                int index = tableViewMatchedPairs.getSelectionModel().getSelectedIndex();
                this.pairToDisband = tableViewMatchedPairs.getItems().get(index);
                buttonRemove.setVisible(true);
            }

        });

        tableViewGroupMatched.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                int index = tableViewGroupMatched.getSelectionModel().getSelectedIndex();
                this.groupToDisband = tableViewGroupMatched.getItems().get(index);
            }
        });
    }

    @FXML
    private void handleButtonUndoClick(ActionEvent event) {
        undo();
        populateUnmatchedSoloTable();
        populatePairMatchedTable();
    }

    @FXML
    private void handleButtonRemoveClick(ActionEvent event) {
        run(new DisbandPair(matchingRepository, pairToDisband));
        populateUnmatchedSoloTable();
        populatePairMatchedTable();
    }


    private void run(UIAction action) {
        history.addLast(action);
        action.run();
    }

    private void undo() {
        if(history.isEmpty()) {
            return;
        }
        UIAction lastAction = history.removeLast();
        lastAction.undo();
    }



    private void disbandPair() {
        matchingRepository.disbandPair(pairToDisband);

        populatePairMatchedTable();
        populateUnmatchedSoloTable();
        //tableViewMatchedPairs.refresh();
        //tableViewUnmatchedSolos.refresh();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
