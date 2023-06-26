package org.example.view.comparer;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.view.PairListTabController;
import org.example.view.properties.PairListProperty;
import org.example.view.tools.TableViewTools;

import java.util.List;

public class PairComparer {
    @FXML
    private TableView<PairListProperty> tableView;

    public void update(List<PairListTabController> controllers) {
        TableViewTools.fillTable(controllers, tableView, PairListProperty::new, PairListProperty.getColumnNames2());
    }
}
