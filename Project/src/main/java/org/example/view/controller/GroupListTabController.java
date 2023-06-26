package org.example.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import org.example.logic.matchingalgorithms.MatchCosts;
import org.example.logic.structures.GroupMatched;
import org.example.logic.structures.MatchingRepository;
import org.example.logic.structures.PairMatched;
import org.example.view.TabController;
import org.example.view.commands.DisbandGroupCommand;
import org.example.view.commands.DisbandPair;
import org.example.view.properties.GroupListProperty;
import org.example.view.properties.GroupMatchedProperty;
import org.example.view.properties.PairMatchedProperty;
import org.example.view.tools.TableViewTools;

import java.util.ArrayList;
import java.util.List;

public class GroupListTabController extends TabController {

    @FXML
    private TableView<PairMatchedProperty> pairTableView;
    @FXML
    private TableView<GroupMatchedProperty> groupTableView;
    @FXML
    private TableView<GroupListProperty> metricsTableView;

    public void setup(MatchingRepository matchingRepository) {
        this.matchingRepository = matchingRepository;
        updateUI();
    }

    @FXML
    public void disbandGroup() {
        System.out.println("called");
        GroupMatchedProperty groupMatchedProperty = groupTableView.getSelectionModel().getSelectedItem();
        GroupMatched groupMatched = groupMatchedProperty.groupMatched();
        DisbandGroupCommand command = new DisbandGroupCommand(matchingRepository, groupMatched);
        run(command);
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

    @Override
    public void closeMatchCost(MatchCosts matchCosts) {

    }
}
