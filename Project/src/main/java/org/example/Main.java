
package org.example;


import org.example.data.*;
import org.example.data.structures.Solo;
import org.example.logic.matchingalgorithms.RandomGroupMatching;
import org.example.logic.metrics.GroupListMetrics;
import org.example.logic.structures.GroupMatched;
import org.example.logic.structures.MatchingRepository;
import org.example.logic.structures.PairMatched;

import java.util.List;
import java.util.Random;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import org.example.data.DataManagement;
import org.example.data.enums.Sex;
import org.example.data.factory.Person;
import org.example.data.structures.EventParticipant;
import org.example.data.structures.Pair;
import org.example.data.structures.Solo;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Main extends Application {

    @FXML
    private Text fileName;
    @FXML
    private ListView<EventParticipant> listView;
    @FXML
    private PieChart pieChart;
    @FXML
    private BarChart<String, Integer> barChart;

    public static void main(String[] args) {

        String fileToRead = "src/main/java/org/example/artifacts/teilnehmerliste.csv";
        String partyLocation = "src/main/java/org/example/artifacts/partylocation.csv";
        DataManagement dataManagement = new DataManagement(fileToRead, partyLocation);
        MatchingRepository matchingRepository = new MatchingRepository(dataManagement);

        //print results
        //pairs
        System.out.println("pair count");
        System.out.println(matchingRepository.getMatchedPairsCollection().size());

        //groups
        System.out.println("groups count");
        System.out.println(matchingRepository.getMatchedGroupsCollection().size());

        //unmatched solos
        System.out.println("unmatched solos size");
        System.out.println(matchingRepository.soloSuccessors.size());

        //unmatched pairs
        System.out.println("unmatched pairs size");
        System.out.println(matchingRepository.pairSuccessors.size());

        System.out.println("Matched groups total " + matchingRepository.getMatchedGroupsCollection().size());

        //Print food preferences of pair successors
        matchingRepository.printPairSuccessorList();

        System.out.println("Graph Group Match");
        GroupListMetrics.printAllMetrics((List<GroupMatched>) matchingRepository.getMatchedGroupsCollection(), dataManagement.partyLocation);
        System.out.println();

        System.out.println("Random Group Match");
        List<GroupMatched> randomGroups = RandomGroupMatching.match((List<PairMatched>) matchingRepository.getMatchedPairsCollection());
        GroupListMetrics.printAllMetrics(randomGroups, dataManagement.partyLocation);

        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Main.fxml")));
        stage.setTitle("Project");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void openFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Project");
        ExtensionFilter csvFilter = new ExtensionFilter("CSV Files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(csvFilter);

        File file = fileChooser.showOpenDialog(null);
        if (file == null) {
            event.consume();
        } else {
            String filePath = file.getAbsolutePath();

            fileName.setText(file.getName());

            DataManagement dataManagement = new DataManagement(filePath,"src/main/java/org/example/artifacts/partylocation.csv");
            displayList(dataManagement.participantList);
            makePieChart(dataManagement);
            ageBarChart(dataManagement);
        }
    }

    public void displayList(List<EventParticipant> participantList) {
        listView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(EventParticipant participant, boolean empty) {
                super.updateItem(participant, empty);

                if (empty || participant == null) {
                    setText(null);
                } else if (participant instanceof Solo solo) {
                    setText(solo.person.name());
                } else if (participant instanceof Pair pair) {
                    setText(pair.soloA.person.name() + ", " + pair.soloB.person.name());
                }
            }
        });


        for (EventParticipant participant : participantList) {
            listView.getItems().add(participant);
        }
    }

    public void makePieChart(DataManagement dataManagement) {
        int[] values = new int[3];

        for (EventParticipant eventParticipant : dataManagement.participantList) {
            if (eventParticipant instanceof Solo solo) {
                values[parseSexEnum(solo.person.sex())] += 1;
            } else if (eventParticipant instanceof  Pair pair) {
                values[parseSexEnum(pair.soloA.person.sex())] += 1;
                values[parseSexEnum(pair.soloB.person.sex())] += 1;
            }
        }

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        pieChartData.add(new PieChart.Data("male", values[0]));
        pieChartData.add(new PieChart.Data("female", values[1]));
        pieChartData.add(new PieChart.Data("other", values[2]));
        pieChart.setData(pieChartData);

    }

    private int parseSexEnum(Sex sex) {
        return switch (sex) {
            case MALE -> 0;
            case FEMALE -> 1;
            case OTHER -> 2;
        };
    }

    public void ageBarChart(DataManagement dataManagement) {

        int[] values = getAges(dataManagement, 5);

        XYChart.Series<String, Integer> series = new XYChart.Series<>();


        for (int i = 0; i < values.length; i++) {
            series.getData().add(new XYChart.Data<>(i + "", values[i]));
        }

        barChart.getData().add(series);
    }

    public int[] getAges (DataManagement dataManagement, int steps) {
        int[] ages = new int[100 / steps];

        for (Solo solo : dataManagement.soloParticipants) {
            ages[calcIndex(solo.person, steps)] += 1;
        }

        for (Pair pair : dataManagement.pairParticipants) {
            ages[calcIndex(pair.soloA.person, steps)] += 1;
            ages[calcIndex(pair.soloB.person, steps)] += 1;
        }

        return ages;
    }

    private int calcIndex (Person person, int steps) {
        double age = person.age();
        return (int) Math.floor(age / steps);
    }


}