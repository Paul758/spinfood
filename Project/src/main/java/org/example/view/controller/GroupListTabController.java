package org.example.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import org.example.logic.structures.GroupMatched;
import org.example.logic.structures.MatchingRepository;
import org.example.logic.structures.PairMatched;
import org.example.view.TabController;
import org.example.view.properties.GroupMatchedProperty;
import org.example.view.properties.PairMatchedProperty;
import org.example.view.tools.TableViewTools;

import java.util.List;

public class GroupListTabController extends TabController {

    @FXML
    private TableView<PairMatchedProperty> pairTableView;
    @FXML
    private TableView<GroupMatchedProperty> groupTableView;
    private MatchingRepository matchingRepository;

    public void setup(MatchingRepository matchingRepository) {
        this.matchingRepository = matchingRepository;
        updateTables();
    }

    @Override
    public void updateUI() {

    }

    public void updateTables() {
        pairTableView.getItems().clear();
        pairTableView.getColumns().clear();
        groupTableView.getItems().clear();
        groupTableView.getColumns().clear();

        pairTableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        groupTableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        List<PairMatched> pairSuccessors = (List<PairMatched>) matchingRepository.pairSuccessors;
        TableViewTools.fillTable(pairSuccessors, pairTableView, PairMatchedProperty::new, PairMatchedProperty.getColumnNames());
        List<GroupMatched> groups = (List<GroupMatched>) matchingRepository.getMatchedGroupsCollection();
        TableViewTools.fillTable(groups, groupTableView, GroupMatchedProperty::new, GroupMatchedProperty.getColumnNames());

        pairTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        groupTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
    }
}
