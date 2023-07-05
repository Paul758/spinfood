package org.example.view.windows;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import org.example.view.controller.GroupListTabController;
import org.example.view.properties.GroupListProperty;
import org.example.view.tools.TableViewTools;

import java.util.List;

/**
 * The functionality for the window where the user can see the metrics of all open group-list TabController
 */
public class GroupComparer {
    @FXML
    private TableView<GroupListProperty> tableView;

    /**
     * fills the table view with the metrics of the group-lists of all open group-list TabController
     * @param controllers all open group-list TabControllers
     */
    public void update(List<GroupListTabController> controllers) {
        TableViewTools.fillTable(controllers, tableView, GroupListProperty::new, GroupListProperty.getComparerColumns());
    }
}
