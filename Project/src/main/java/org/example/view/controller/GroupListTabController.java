package org.example.view.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableView;
import org.example.Main;
import org.example.logic.structures.GroupMatched;
import org.example.logic.structures.MatchingRepository;
import org.example.logic.structures.PairMatched;
import org.example.view.commands.DisbandGroupCommand;
import org.example.view.properties.GroupListProperty;
import org.example.view.properties.GroupMatchedProperty;
import org.example.view.properties.PairMatchedProperty;
import org.example.view.tools.Settings;
import org.example.view.windows.GroupBuilder;
import org.example.view.tools.TableViewTools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * The group-list TabController handles the following tasks:
 * 1. matches pairs to groups
 * 2. displays all groups
 * 3. displays the pair successors
 * 4. displays the group-list metrics
 * 5. disbandment of groups
 * 6. creation of groups
 */
public class GroupListTabController extends TabController {

    @FXML
    private TableView<PairMatchedProperty> pairTableView;
    @FXML
    private TableView<GroupMatchedProperty> groupTableView;
    @FXML
    private TableView<GroupListProperty> metricsTableView;

    public GroupListTabController(MatchingRepository matchingRepository, Main parent, String name) {
        super(matchingRepository, parent, name);
    }

    @FXML
    private void initialize() {
        updateUI();
    }

    /**
     * disbands a group that the user has selected
     */
    @FXML
    public void disbandGroup() {
        GroupMatchedProperty groupMatchedProperty = groupTableView.getSelectionModel().getSelectedItem();
        GroupMatched groupMatched = groupMatchedProperty.groupMatched();
        DisbandGroupCommand command = new DisbandGroupCommand(matchingRepository, groupMatched);
        run(command);
    }

    /**
     * opens the group builder window
     */
    @FXML
    public void openGroupBuilder() throws IOException {
        List<PairMatched> successors = new ArrayList<>(matchingRepository.pairSuccessors);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GroupBuilder.fxml"));
        fxmlLoader.setControllerFactory((Class<?> controllerClass) -> new GroupBuilder(successors, this));
        ResourceBundle bundle = ResourceBundle.getBundle("uiElements", Settings.getInstance().getLocale());
        fxmlLoader.setResources(bundle);
        Parent root = fxmlLoader.load();
        this.openPopupWindow(root, "groupBuilder");
    }

    /**
     * Updates the group-list, pair successor and metrics table view
     */
    @Override
    public void updateUI() {
        List<PairMatched> pairSuccessors = new ArrayList<>(matchingRepository.pairSuccessors);
        TableViewTools.fillTable(pairSuccessors, pairTableView, PairMatchedProperty::new, PairMatchedProperty.getSummaryViewColumns());

        List<GroupMatched> groups = new ArrayList<>(matchingRepository.getMatchedGroupsCollection());
        TableViewTools.fillTable(groups, groupTableView, GroupMatchedProperty::new, GroupMatchedProperty.getColumnNames());

        List<GroupListTabController> controllers = List.of(this);
        TableViewTools.fillTable(controllers, metricsTableView, GroupListProperty::new, GroupListProperty.getTabColumns());
    }
}
