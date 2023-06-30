package org.example;

import javafx.scene.control.*;
import org.example.data.json.Serializer;
import org.example.logic.matchingalgorithms.MatchCosts;
import org.example.logic.structures.MatchingRepository;

import java.util.*;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import org.example.view.tools.Settings;
import org.example.view.windows.GroupComparer;
import org.example.view.windows.PairComparer;
import org.example.view.controller.GroupListTabController;
import org.example.view.tools.ViewTools;
import org.example.view.controller.DataTabController;
import org.example.view.controller.PairListTabController;
import org.example.view.controller.TabController;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main extends Application {

    @FXML
    private TabPane tabPane;
    private final HashMap<Tab, TabController> tabControllerHashMap = new HashMap<>();
    private List<TabController> tabControllers;

    public static void main(String[] args) {
        launch();
    }
 
    @Override
    public void start(Stage stage) throws IOException {
        Settings.getInstance().setPreferences();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/Main.fxml"));

        //Specify locale resources
        ResourceBundle bundle = ResourceBundle.getBundle("uiElements",
                Settings.getInstance().getLocale());
        fxmlLoader.setResources(bundle);

        Parent root = fxmlLoader.load();

        stage.setTitle("Project");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void initialize() {
        tabControllers = new ArrayList<>();
    }

    @FXML
    private void createDataTab() throws IOException {
        String tabName = "Data " + ViewTools.getTimeStamp();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/DataTabController.fxml"));
        fxmlLoader.setControllerFactory((Class<?> controllerClass) -> {
            return new DataTabController(null, this, tabName);
        });
        ResourceBundle bundle = ResourceBundle.getBundle("uiElements", Settings.getInstance().getLocale());
        fxmlLoader.setResources(bundle);
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
        ResourceBundle bundle = ResourceBundle.getBundle("uiElements", Settings.getInstance().getLocale());
        fxmlLoader.setResources(bundle);
        Parent root = fxmlLoader.load();

        PairListTabController pairListTabController = fxmlLoader.getController();
        parent.addChild(pairListTabController);
        this.addNewTabController(pairListTabController, root, tabName);
    }

    public void createGroupTab(TabController parent, MatchCosts matchCosts) throws IOException {
        String tabName = "Group " + ViewTools.getTimeStamp();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GroupListTabController.fxml"));
        fxmlLoader.setControllerFactory((Class<?> controllerClass) -> {
            MatchingRepository matchingRepository = parent.getMatchingRepository().getCopy() ;
            matchingRepository.matchGroups(matchCosts);
            return new GroupListTabController(matchingRepository, this, tabName);
        });
        ResourceBundle bundle = ResourceBundle.getBundle("uiElements", Settings.getInstance().getLocale());
        fxmlLoader.setResources(bundle);
        Parent root = fxmlLoader.load();

        GroupListTabController groupListTabController = fxmlLoader.getController();
        parent.addChild(groupListTabController);
        addNewTabController(groupListTabController, root, tabName);
    }

    @FXML
    private void openPairComparer() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/PairComparer.fxml"));
        ResourceBundle bundle = ResourceBundle.getBundle("uiElements", Settings.getInstance().getLocale());
        fxmlLoader.setResources(bundle);
        Parent root = fxmlLoader.load();

        PairComparer pairComparer = fxmlLoader.getController();
        pairComparer.update(getPairListTabControllers());

        this.openNewWindow(root, "pairComparer");
    }

    @FXML
    private void openGroupComparer() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GroupComparer.fxml"));
        ResourceBundle bundle = ResourceBundle.getBundle("uiElements", Settings.getInstance().getLocale());
        fxmlLoader.setResources(bundle);
        Parent root = fxmlLoader.load();

        GroupComparer groupComparer = fxmlLoader.getController();
        groupComparer.update(getGroupListTabControllers());

        this.openNewWindow(root, "groupComparer");
    }

    private void openNewWindow(Parent root, String title) {
        ResourceBundle bundle = ResourceBundle.getBundle("uiElements",
                Settings.getInstance().getLocale());

        Stage stage = new Stage();
        stage.setTitle(bundle.getString(title));
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void addNewTabController(TabController tabController, Parent root, String name) {
        Tab tab = new Tab(name);
        tab.setContent(root);
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);

        tabControllerHashMap.put(tab, tabController);
        tabController.setTab(tab);
        tabControllers.add(tabController);
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
        selectedTab.undo();
    }

    @FXML
    public void redo(){
        Tab tab = tabPane.getSelectionModel().getSelectedItem();
        TabController selectedTab = tabControllerHashMap.get(tab);
        selectedTab.redo();
    }

    @FXML
    public void exportToJSON() {
        TabController tabController = tabControllerHashMap.get(tabPane.getSelectionModel().getSelectedItem());
        MatchingRepository repository = tabController.getMatchingRepository();

        try {
            String data = Serializer.serializeMatchingRepository(repository);
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON doc(*.json)", "*.json"));
            File file = fileChooser.showSaveDialog(new Stage());

            if(!file.getName().contains(".")) {
                file = new File(file.getAbsolutePath() + ".json");
            }

            Serializer.writeToFile(data, file.getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void closeTab() {
        Tab tab = tabPane.getSelectionModel().getSelectedItem();
        TabController tabController = tabControllerHashMap.get(tab);
        tabController.delete();
    }

    public void closeTab(TabController tabController) {
        Tab tab = tabController.getTab();
        tabControllers.remove(tabController);
        tabControllerHashMap.remove(tab);
        tabPane.getTabs().remove(tab);
    }

    private List<PairListTabController> getPairListTabControllers() {
        return tabControllers.stream()
                .filter(t -> t instanceof PairListTabController)
                .map(t -> (PairListTabController) t)
                .toList();
    }

    private List<GroupListTabController> getGroupListTabControllers() {
        return tabControllers.stream()
                .filter(t -> t instanceof GroupListTabController)
                .map(t -> (GroupListTabController) t)
                .toList();
    }
}