package org.example.view.tools;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.data.structures.Pair;
import org.example.data.structures.Solo;
import org.example.logic.structures.PairMatched;
import org.example.view.Settings;
import org.example.view.properties.PairMatchedProperty;
import org.example.view.properties.PairProperty;
import org.example.view.properties.SoloProperty;

import java.util.*;

public class TableViewTools {
    public static void fillSoloTable(List<Solo> solos, TableView<SoloProperty> tableView) {
        List<SoloProperty> soloProperties = solos.stream()
                .map(SoloProperty::new)
                .toList();
        ObservableList<SoloProperty> data = FXCollections.observableArrayList(soloProperties);

        for (String columnName : SoloProperty.simpleData()) {
            TableColumn<SoloProperty, String> column = new TableColumn<>(columnName);
            column.setCellValueFactory(new PropertyValueFactory<>(columnName));
            tableView.getColumns().add(column);
        }

        tableView.setItems(data);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
    }

    public static void fillPairTable(List<Pair> pairs, TableView<PairProperty> tableView) {



        List<PairProperty> pairProperties = pairs.stream()
                .map(PairProperty::new)
                .toList();
        ObservableList<PairProperty> data = FXCollections.observableArrayList(pairProperties);

        LinkedHashMap<String, List<String>> map = PairProperty.getColumnNames();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            TableColumn<PairProperty, String> topColumn = new TableColumn<>(entry.getKey());

            for (String columnName : entry.getValue()) {
                TableColumn<PairProperty, String> column = new TableColumn<>(columnName);
                column.setCellValueFactory(new PropertyValueFactory<>(columnName));
                topColumn.getColumns().add(column);

            }


            tableView.getColumns().add(topColumn);
        }

        tableView.setItems(data);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
    }

    public static void fillPairMatchedTable(List<PairMatched> pairs, TableView<PairMatchedProperty> tableView) {
        //Specify locale resources
        ResourceBundle bundle = ResourceBundle.getBundle("PairTableView", Settings.getInstance().getLocale());

        List<PairMatchedProperty> pairProperties = pairs.stream()
                .map(PairMatchedProperty::new)
                .toList();
        ObservableList<PairMatchedProperty> data = FXCollections.observableArrayList(pairProperties);

        LinkedHashMap<String, List<String>> map = PairMatchedProperty.getColumnNames();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            TableColumn<PairMatchedProperty, String> topColumn = new TableColumn<>(entry.getKey());

            for (String columnName : entry.getValue()) {
                TableColumn<PairMatchedProperty, String> column = new TableColumn<>(columnName);
                column.setCellValueFactory(new PropertyValueFactory<>(columnName));
                topColumn.getColumns().add(column);

                //Change column name based on selected language
                try {
                    System.out.println("The column name is " + columnName);
                    String specifiedColumnName = bundle.getString(columnName);
                    column.setText(bundle.getString(columnName));
                } catch (Exception e) {
                    System.out.println("couldnt find key in resources bundle");
                    continue;
                }

            }

            tableView.getColumns().add(topColumn);
            //Change column name based on selected language
            try {
                System.out.println("The column name is " + entry.getKey());
                String specifiedColumnName = bundle.getString(entry.getKey());
                topColumn.setText(bundle.getString(entry.getKey()));
            } catch (Exception e) {
                System.out.println("couldnt find key in resources bundle");
                continue;
            }
        }

        tableView.setItems(data);
    }
}
