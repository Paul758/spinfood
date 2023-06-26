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

import java.util.*;
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

    public static <T, R> void fillTable(List<T> objects, TableView<R> tableView,
                                        Function<T,R> mapper, LinkedHashMap<String, List<Entry>> columnsMap) {

        clearTable(tableView);
        List<R> properties = map(objects,mapper);
        ObservableList<R> data = FXCollections.observableArrayList(properties);

        for (Map.Entry<String, List<Entry>> entry : columnsMap.entrySet()) {
            List<TableColumn<R, ?>> columns = new ArrayList<>();

            for (Entry listEntry : entry.getValue()) {
                TableColumn<R, ?> column = createColumn(listEntry);
                columns.add(column);
            }

            if (entry.getKey().equals("")) {
                tableView.getColumns().addAll(columns);
            } else {
                TableColumn<R, String> topColumn = new TableColumn<>(entry.getKey());
                topColumn.getColumns().addAll(columns);
                tableView.getColumns().add(topColumn);
            }
        }

        tableView.setItems(data);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
    }

    private static <R> TableColumn<R, ?> createColumn(Entry listEntry) {
        return switch (listEntry.entryType) {
            case STRING -> createStringColumn(listEntry.name);
            case IMAGE -> createImageViewColumn(listEntry.name);
            case INDEX -> throw new RuntimeException();
        };
    }

    private static <R> TableColumn<R, String> createStringColumn(String name) {
        TableColumn<R, String> column = new TableColumn<>(name);
        column.setCellValueFactory(new PropertyValueFactory<>(name));
        return column;
    }

    private static <R> TableColumn<R, ImageView> createImageViewColumn(String name) {
        TableColumn<R, ImageView> column = new TableColumn<>(name);
        column.setCellValueFactory(new PropertyValueFactory<>(name));
        column.setComparator(Comparator.comparing(imageView -> imageView.getUserData().toString()));
        return column;
    }

    private static <T> void clearTable(TableView<T> tableView) {
        tableView.getItems().clear();
        tableView.getColumns().clear();
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    }
}
