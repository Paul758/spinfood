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
import org.example.view.windows.GroupBuilder;
import org.example.view.tools.TableViewTools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public void setup(MatchingRepository matchingRepository) {
        this.matchingRepository = matchingRepository;
    }

    @FXML
    public void disbandGroup() {
        GroupMatchedProperty groupMatchedProperty = groupTableView.getSelectionModel().getSelectedItem();
        GroupMatched groupMatched = groupMatchedProperty.groupMatched();
        DisbandGroupCommand command = new DisbandGroupCommand(matchingRepository, groupMatched);
        run(command);
    }

    @FXML
    public void openGroupBuilder() throws IOException {
        List<PairMatched> successors = new ArrayList<>(matchingRepository.pairSuccessors);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GroupBuilder.fxml"));
        fxmlLoader.setControllerFactory((Class<?> controllerClass) -> new GroupBuilder(successors, this));
        Parent root = fxmlLoader.load();
        this.openPopupWindow(root, "Group Builder");
    }

    @Override
    public void updateUI() {
        List<PairMatched> pairSuccessors = new ArrayList<>(matchingRepository.pairSuccessors);
        TableViewTools.fillTable(pairSuccessors, pairTableView, PairMatchedProperty::new, PairMatchedProperty.getColumnNames());

        List<GroupMatched> groups = new ArrayList<>(matchingRepository.getMatchedGroupsCollection());
        TableViewTools.fillTable(groups, groupTableView, GroupMatchedProperty::new, GroupMatchedProperty.getColumnNames());

        List<GroupListTabController> controllers = List.of(this);
        TableViewTools.fillTable(controllers, metricsTableView, GroupListProperty::new, GroupListProperty.getColumnNames2());
    }
}
