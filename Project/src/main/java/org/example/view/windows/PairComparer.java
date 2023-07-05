package org.example.view.windows;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import org.example.view.controller.PairListTabController;
import org.example.view.properties.PairListProperty;
import org.example.view.tools.TableViewTools;

import java.util.List;

/**
 * The functionality for the window where the user can see the metrics of all open pair-list TabController
 */
public class PairComparer {
    @FXML
    private TableView<PairListProperty> tableView;

    /**
     * fills the table view with the metrics of the pair-lists of all open pair-list TabController
     * @param controllers all open pair-list TabControllers
     */
    public void update(List<PairListTabController> controllers) {
        TableViewTools.fillTable(controllers, tableView, PairListProperty::new, PairListProperty.getColumnNames2());
    }
}
