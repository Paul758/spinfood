package org.example;

import javafx.scene.control.*;
import javafx.scene.image.Image;
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

/**
 * The main window of the application that contains the menu bar and the tab pane that displays all TabController
 */
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

        stage.setTitle("FoodSpin-Matching Software V1.0");
        stage.getIcons().add(new Image("spinfood_icon.jpg"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void initialize() {
        tabControllers = new ArrayList<>();
    }

    /**
     * Creates a new DataTabController
     */
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

    /**
     * Creates a new PairTabController. Gets the matching repository from a parent TabController and runs the pair
     * matching algorithm on this matching repository.
     * @param parent a DataTabController object
     * @param matchCosts a MatchCost Object that contains the priorities for the pair matching algorithm
     */
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

    /**
     * Creates a new GroupListTabController. Gets the matching repository from a parent TabController and runs the group
     * matching algorithm on this matching repository.
     * @param parent a PairListTabController Object
     * @param matchCosts a MatchCost Object that contains the priorities for the group matching algorithm
     */
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

    /**
     * opens the pair comparer in a new window
     */
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

    /**
     * opens the group comparer in a new window
     */
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

    /**
     * opens a new window
     * @param root the content of the new window
     * @param title the title of the new window
     */
    private void openNewWindow(Parent root, String title) {
        ResourceBundle bundle = ResourceBundle.getBundle("uiElements",
                Settings.getInstance().getLocale());

        Stage stage = new Stage();
        stage.setTitle(bundle.getString(title));
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Creates a new tab for a TabController, sets the content of the tab to the content of the TabController and
     * add the TabController to the list and hashmap
     * @param tabController a TabController Object
     * @param root the content of the tab
     * @param name the name of the tab
     */
    public void addNewTabController(TabController tabController, Parent root, String name) {
        Tab tab = new Tab(name);
        tab.setContent(root);
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);

        tabControllerHashMap.put(tab, tabController);
        tabController.setTab(tab);
        tabControllers.add(tabController);
    }


    /**
     * Changes the selected language to english
     */
    @FXML
    public void handleEnglishItemClicked() {
        Settings.getInstance().saveLanguage(Locale.US);
    }

    /**
     * Changes the selected language to german
     */
    @FXML
    public void handleGermanItemClicked() {
        Settings.getInstance().saveLanguage(Locale.GERMAN);
    }


    /**
     * Undoes the last command of the selected TabController
     */
    @FXML
    public void undo(){
        Tab tab = tabPane.getSelectionModel().getSelectedItem();
        TabController selectedTab = tabControllerHashMap.get(tab);
        selectedTab.undo();
    }

    /**
     * Redoes the last command of the selected TabController
     */
    @FXML
    public void redo(){
        Tab tab = tabPane.getSelectionModel().getSelectedItem();
        TabController selectedTab = tabControllerHashMap.get(tab);
        selectedTab.redo();
    }

    /**
     * Exports the matching repository of the selected TabController as json file
     */
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

    /**
     * Calls the delete function of the currently selected TabController
     */
    @FXML
    public void closeTab() {
        Tab tab = tabPane.getSelectionModel().getSelectedItem();
        TabController tabController = tabControllerHashMap.get(tab);
        tabController.delete();
    }

    /**
     * Removes the tab of a given TabController from the tab pane, the list that contains all tap panes and the
     * hashmap that maps tabs to TabController
     * @param tabController a TabController object that should be removed
     */
    public void closeTab(TabController tabController) {
        Tab tab = tabController.getTab();
        tabControllers.remove(tabController);
        tabControllerHashMap.remove(tab);
        tabPane.getTabs().remove(tab);
    }

    /**
     * Filters all pair-list TabControllers from the TabController list
     * @return all pair-list TabController
     */
    private List<PairListTabController> getPairListTabControllers() {
        return tabControllers.stream()
                .filter(t -> t instanceof PairListTabController)
                .map(t -> (PairListTabController) t)
                .toList();
    }

    /**
     * Filters all group-list TabControllers from the TabController list
     * @return all group-list TabController
     */
    private List<GroupListTabController> getGroupListTabControllers() {
        return tabControllers.stream()
                .filter(t -> t instanceof GroupListTabController)
                .map(t -> (GroupListTabController) t)
                .toList();
    }
}