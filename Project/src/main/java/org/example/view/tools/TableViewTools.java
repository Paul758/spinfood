package org.example.view.tools;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import org.example.view.properties.*;

import java.util.*;
import java.util.function.Function;


public class TableViewTools {

    public static <T, R> List<R> map(List<T> toMap, Function<T, R> mapper) {
        return toMap.stream()
                .map(mapper)
                .toList();
    }

    public static <T, R> void fillTable(List<T> objects, TableView<R> tableView,
                                        Function<T,R> mapper, LinkedHashMap<String, List<Entry>> columnsMap) {

        clearTable(tableView);
        List<R> properties = map(objects,mapper);
        ObservableList<R> data = FXCollections.observableArrayList(properties);
        ResourceBundle bundle = ResourceBundle.getBundle("tableViewColumns", Settings.getInstance().getLocale());

        for (Map.Entry<String, List<Entry>> entry : columnsMap.entrySet()) {
            List<TableColumn<R, ?>> columns = new ArrayList<>();

            for (Entry listEntry : entry.getValue()) {
                TableColumn<R, ?> column = createColumn(listEntry);
                columns.add(column);
                column.setText(bundle.getString(listEntry.name));
            }

            if (entry.getKey().equals("")) {
                tableView.getColumns().addAll(columns);
            } else {
                TableColumn<R, String> topColumn = new TableColumn<>(entry.getKey());
                topColumn.getColumns().addAll(columns);
                tableView.getColumns().add(topColumn);
                topColumn.setText(bundle.getString(entry.getKey()));
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
