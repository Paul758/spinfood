
package org.example;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import org.example.data.*;
import org.example.data.structures.Solo;
import org.example.logic.matchingalgorithms.MatchCosts;
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
import org.example.view.DataTabController;
import org.example.view.PairListTabController;
import org.example.view.TabController;
import org.example.view.UIAction;
import org.example.view.comparer.GroupComparer;
import org.example.view.comparer.PairComparer;
import org.example.view.controller.GroupListTabController;
import org.example.view.tools.*;

import javafx.stage.Popup;


import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main extends Application {

    @FXML
    private Button defaultButton;
    @FXML
    private TabPane tabPane;
    private final HashMap<Tab, TabController> tabControllerHashMap = new HashMap<>();
    private List<DataTabController> dataTabControllers;
    private List<PairListTabController> pairListTabControllers;
    private List<GroupListTabController> groupListTabControllers;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/Main.fxml"));
        Parent root = fxmlLoader.load();

        stage.setTitle("Project");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void initialize() {
        pairListTabControllers = new ArrayList<>();
        groupListTabControllers = new ArrayList<>();
        dataTabControllers = new ArrayList<>();
    }

    @FXML
    private void createDataTab() throws IOException {
        String tabName = "Data " + ViewTools.getTimeStamp();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/DataTabController.fxml"));
        fxmlLoader.setControllerFactory((Class<?> controllerClass) -> {
            return new DataTabController(null, this, tabName);
        });
        Parent root = fxmlLoader.load();

        DataTabController dataTabController = fxmlLoader.getController();
        this.addNewTabController(dataTabController, root, tabName);
    }

    public void createPairTab(TabController parent, MatchCosts matchCosts) throws IOException {
        String tabName = "Pair " + ViewTools.getTimeStamp();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/PairListTabController.fxml"));
        fxmlLoader.setControllerFactory((Class<?> controllerClass) -> {
            MatchingRepository matchingRepository = parent.getMatchingRepository().getCopy();
            matchingRepository.matchPairs(matchCosts);
            return new PairListTabController(matchingRepository, this, tabName);
        });
        Parent root = fxmlLoader.load();

        PairListTabController pairListTabController = fxmlLoader.getController();
        parent.addChild(pairListTabController);
        this.addNewTabController(pairListTabController, root, tabName);
    }

    public void createGroupTab(TabController parent, MatchCosts matchCosts) throws IOException {
        String tabName = "Group " + ViewTools.getTimeStamp();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/GroupListTabController.fxml"));
        fxmlLoader.setControllerFactory((Class<?> controllerClass) -> {
            MatchingRepository matchingRepository = parent.getMatchingRepository().getCopy() ;
            matchingRepository.matchGroups(matchCosts);
            return new GroupListTabController(matchingRepository, this, tabName);
        });
        Parent root = fxmlLoader.load();

        GroupListTabController groupListTabController = fxmlLoader.getController();
        parent.addChild(groupListTabController);
        addNewTabController(groupListTabController, root, tabName);
    }

    @FXML
    private void openPairComparer() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/PairComparer.fxml"));
        Parent root = fxmlLoader.load();

        PairComparer pairComparer = fxmlLoader.getController();
        pairComparer.update(pairListTabControllers);

        this.openNewWindow(root, "Pair Comparer");
    }

    @FXML
    private void openGroupComparer() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GroupComparer.fxml"));
        Parent root = fxmlLoader.load();

        GroupComparer groupComparer = fxmlLoader.getController();
        groupComparer.update(groupListTabControllers);

        this.openNewWindow(root, "Group Comparer");
    }

    private void openNewWindow(Parent root, String title) {
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void addNewTabController(TabController tabController, Parent root, String name) {
        Tab tab = new Tab(name);
        tab.setContent(root);
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
        tabControllerHashMap.put(tab, tabController);

        if (tabController instanceof DataTabController) {
            dataTabControllers.add((DataTabController) tabController);
        } else if (tabController instanceof PairListTabController) {
            pairListTabControllers.add((PairListTabController) tabController);
        } else if (tabController instanceof GroupListTabController) {
            groupListTabControllers.add((GroupListTabController) tabController);
        }
    }

    @FXML
    public void undo(){
        Tab tab = tabPane.getSelectionModel().getSelectedItem();
        TabController selectedTab = tabControllerHashMap.get(tab);
        selectedTab.undo();
    }

    @FXML
    public void redo(){
        Tab tab = tabPane.getSelectionModel().getSelectedItem();
        TabController selectedTab = tabControllerHashMap.get(tab);
        selectedTab.redo();
    }

}