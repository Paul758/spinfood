
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

import java.time.LocalDateTime;
import java.util.*;

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
import org.example.view.*;
import org.example.view.tools.PairBuilder;
import org.example.view.tools.SoloTable;
import org.example.view.tools.SoloTableListener;

import javafx.stage.Popup;
import org.example.view.tools.ViewTools;


import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main extends Application implements SoloTableListener {

    HashMap<Tab, TabController> tabControllerHashMap = new HashMap<>();

    @FXML
    private MenuBar menuBar;

    @FXML
    private AnchorPane pane;
    @FXML
    private Button defaultButton;
    @FXML
    private TabPane tabPane;

    DataManagement dataManagement;
    Parent root;
    SoloTable soloTable;


    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Settings.getInstance().setPreferences();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/Main.fxml"));

        //Specify locale resources
        ResourceBundle bundle = ResourceBundle.getBundle("MenuBarItemsBundle", Settings.getInstance().getLocale());
        fxmlLoader.setResources(bundle);


        root = fxmlLoader.load();

        stage.setTitle("Project");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void initialize() {

    }

    @FXML
    private void createDataTab() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/DataTabController.fxml"));

        //Specify locale resources
        ResourceBundle bundle = ResourceBundle.getBundle("DataTabBundle", Settings.getInstance().getLocale());
        fxmlLoader.setResources(bundle);

        root = fxmlLoader.load();
        DataTabController dataTabController = fxmlLoader.getController();
        dataTabController.setup(this);

        LocalDateTime timeStamp = LocalDateTime.now();
        Tab tab = new Tab("Data " + ViewTools.getTimeStamp());
        tab.setContent(root);
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }

    public void createPairTab(DataTabController dataTabController) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/PairListTabController.fxml"));

        //Specify locale resources
        ResourceBundle bundle = ResourceBundle.getBundle("MenuBarItemsBundle", Settings.getInstance().getLocale());
        fxmlLoader.setResources(bundle);

        root = fxmlLoader.load();
        PairListTabController pairListTabController = fxmlLoader.getController();
        pairListTabController.setup(dataTabController);

        Tab tab = new Tab("Pair " + ViewTools.getTimeStamp());
        tab.setContent(root);
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
        tabControllerHashMap.put(tab, pairListTabController);
    }

    @FXML
    private void loadDefaultValues() {

    }

    @FXML
    private void clickPairBuilderButton() throws Exception {

    }

    @Override
    public void onSelectItemLeftClick(Solo solo, SoloTable soloTableView) {
        if (soloTableView.equals(soloTable)) {

        }
    }

    public void contextMenuClicked() {
        System.out.println("context menu clicked");
    }


    @FXML
    public void handleEnglishItemClicked() {
        Settings.getInstance().saveLanguage(Locale.US);

    }

    @FXML
    public void handleGermanItemClicked() {
        Settings.getInstance().saveLanguage(Locale.GERMAN);
    }



    @FXML
    public void undo(){
        Tab tab = tabPane.getSelectionModel().getSelectedItem();
        TabController selectedTab = tabControllerHashMap.get(tab);
        System.out.println("called");
        selectedTab.undo();

    }

    @FXML
    public void redo(){
        Tab tab = tabPane.getSelectionModel().getSelectedItem();
        TabController selectedTab = tabControllerHashMap.get(tab);
        System.out.println("called");
        selectedTab.redo();

    }

}