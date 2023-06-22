package org.example.view.tools;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import org.example.data.structures.Pair;
import org.example.data.structures.Solo;
import org.example.logic.structures.PairMatched;
import org.example.view.properties.*;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class TableViewTools {

    public static <T, R> List<R> map(List<T> toMap, Function<T, R> mapper) {
        return toMap.stream()
                .map(mapper)
                .toList();
    }

    /*
    public static void fillSoloTable(List<Solo> solos, TableView<SoloProperty> tableView) {
        List<SoloProperty> soloProperties = solos.stream()
                .map(SoloProperty::new)
                .toList();
        ObservableList<SoloProperty> data = FXCollections.observableArrayList(soloProperties);

        for (String columnName : SoloProperty.getColumnNames()) {
            TableColumn<SoloProperty, String> column = new TableColumn<>(columnName);
            column.setCellValueFactory(new PropertyValueFactory<>(columnName));
            tableView.getColumns().add(column);
        }

        tableView.setItems(data);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
    }

     */

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
        List<PairMatchedProperty> pairProperties = pairs.stream()
                .map(PairMatchedProperty::new)
                .toList();
        ObservableList<PairMatchedProperty> data = FXCollections.observableArrayList(pairProperties);

        LinkedHashMap<String, List<Entry>> map = PairMatchedProperty.getColumnNames();
        for (Map.Entry<String, List<Entry>> entry : map.entrySet()) {
            TableColumn<PairMatchedProperty, String> topColumn = new TableColumn<>(entry.getKey());

            for (Entry listEntry : entry.getValue()) {
                if (listEntry.entryType.equals(EntryType.STRING)) {
                    String name = listEntry.name;
                    TableColumn<PairMatchedProperty, String> column = new TableColumn<>(name);
                    column.setCellValueFactory(new PropertyValueFactory<>(name));
                    topColumn.getColumns().add(column);
                } else if (listEntry.entryType.equals(EntryType.IMAGE)) {
                    String name = listEntry.name;
                    TableColumn<PairMatchedProperty, ImageView> column = new TableColumn<>(name);
                    column.setCellValueFactory(new PropertyValueFactory<>(name));
                    topColumn.getColumns().add(column);
                }
            }

            tableView.getColumns().add(topColumn);
        }

        tableView.setItems(data);
    }

    public static <T, R> void fillTable(List<T> objects, TableView<R> tableView,
                                        Function<T,R> mapper, LinkedHashMap<String, List<Entry>> columnsMap) {
        List<R> properties = map(objects,mapper);
        ObservableList<R> data = FXCollections.observableArrayList(properties);

        for (Map.Entry<String, List<Entry>> entry : columnsMap.entrySet()) {
            TableColumn<R, String> topColumn = new TableColumn<>(entry.getKey());

            for (Entry listEntry : entry.getValue()) {
                if (listEntry.entryType.equals(EntryType.STRING)) {
                    String name = listEntry.name;
                    TableColumn<R, String> column = new TableColumn<>(name);
                    column.setCellValueFactory(new PropertyValueFactory<>(name));
                    topColumn.getColumns().add(column);
                } else if (listEntry.entryType.equals(EntryType.IMAGE)) {
                    String name = listEntry.name;
                    TableColumn<R, ImageView> column = new TableColumn<>(name);
                    column.setCellValueFactory(new PropertyValueFactory<>(name));
                    column.setComparator(Comparator.comparing(imageView -> imageView.getUserData().toString()));
                    topColumn.getColumns().add(column);
                } else if (listEntry.entryType.equals(EntryType.INDEX)) {
                    String name = listEntry.name;
                    TableColumn<R, Integer> column = new TableColumn<>(name);
                    column.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(tableView.getItems().indexOf(c.getValue()) + 1));
                    topColumn.getColumns().add(column);
                }
            }

            tableView.getColumns().add(topColumn);
        }

        tableView.setItems(data);
    }
}
