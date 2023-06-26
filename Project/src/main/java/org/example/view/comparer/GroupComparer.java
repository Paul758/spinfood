package org.example.view.comparer;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import org.example.view.PairListTabController;
import org.example.view.controller.GroupListTabController;
import org.example.view.properties.GroupListProperty;
import org.example.view.properties.PairListProperty;
import org.example.view.tools.TableViewTools;

import java.util.List;

public class GroupComparer {
    @FXML
    private TableView<GroupListProperty> tableView;

    public void update(List<GroupListTabController> controllers) {
        TableViewTools.fillTable(controllers, tableView, GroupListProperty::new, GroupListProperty.getColumnNames2());
    }
}
