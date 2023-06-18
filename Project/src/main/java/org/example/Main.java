
package org.example;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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
import org.example.view.tools.PairBuilder;
import org.example.view.tools.SoloTable;
import org.example.view.tools.SoloTableListener;

import javafx.stage.Popup;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Main extends Application implements SoloTableListener {

    @FXML
    private AnchorPane pane;
    @FXML
    private Button pairBuilderButton;

    DataManagement dataManagement;
    Parent root;
    SoloTable soloTable;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/Main.fxml"));
        root = fxmlLoader.load();

        stage.setTitle("Project");
        stage.setScene(new Scene(root));
        stage.show();

        System.out.println(pairBuilderButton == null);
    }

    @FXML
    private void initialize() {
        String participants = "src/main/java/org/example/artifacts/teilnehmerliste.csv";
        String party = "src/main/java/org/example/artifacts/partylocation.csv";
        dataManagement = new DataManagement(participants, party);
        List<Solo> solos = dataManagement.soloParticipants;

        soloTable = new SoloTable(solos, pane);
        soloTable.addListener(this);

        System.out.println(pairBuilderButton == null);
    }

    @FXML
    private void clickPairBuilderButton() throws Exception {
        System.out.println("Open pair builder");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(PairBuilder.class.getResource("/PairBuilder.fxml"));
        Parent root = fxmlLoader.load();

        PairBuilder pairBuilder = fxmlLoader.getController();
        pairBuilder.setup(dataManagement.soloParticipants);

        Stage stage = new Stage();
        stage.setTitle("Pair Builder");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Override
    public void onSelectItemLeftClick(Solo solo, SoloTable soloTableView) {
        if (soloTableView.equals(soloTable)) {

        }
    }

    public void contextMenuClicked() {
        System.out.println("context menu clicked");
    }
}