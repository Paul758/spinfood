package org.example.view.tools;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import org.example.view.properties.*;

import java.util.*;
import java.util.function.Function;

/**
 * Handles the process of creating the column structure for a table view and adding data into the table view
 */
public class TableViewTools {

    /**
     * Maps a list of Solo, Pair, PairMatched or GroupMatched object to a list of the corresponding property objects
     * @param toMap a List of Objects that should be mapped
     * @param mapper the Constructor function of the Property Object
     * @return a List of Property Objects
     * @param <T> a Solo, Pair, PairMatched or GroupMatched object
     * @param <R> the corresponding property object, for example SoloProperty for Solo
     */
    public static <T, R> List<R> map(List<T> toMap, Function<T, R> mapper) {
        return toMap.stream()
                .map(mapper)
                .toList();
    }

    /**
     * Creates the column structure of the given table view and fill it with the property objects of the given objects
     * @param objects the objects whose properties should be displayed in the table view
     * @param tableView the tableview that should display the properties
     * @param mapper the Constructor function of the Property Object
     * @param columnsMap a map that represents the blueprint for the column structure of the table view
     * @param <T> a Solo, Pair, PairMatched or GroupMatched object
     * @param <R> the corresponding property object, for example SoloProperty for Solo
     */
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

    /**
     * Decides which type of column should be created
     * @param listEntry contains the column type
     * @return a column of the given type
     * @param <R> the property object
     */
    private static <R> TableColumn<R, ?> createColumn(Entry listEntry) {
        return switch (listEntry.entryType) {
            case STRING -> createStringColumn(listEntry.name);
            case IMAGE -> createImageViewColumn(listEntry.name);
            case INDEX -> throw new RuntimeException();
        };
    }

    /**
     * Creates a column that displays strings
     * @param name the name of the column
     * @return a column
     * @param <R> the property object
     */
    private static <R> TableColumn<R, String> createStringColumn(String name) {
        TableColumn<R, String> column = new TableColumn<>(name);
        column.setCellValueFactory(new PropertyValueFactory<>(name));
        return column;
    }

    /**
     * Creates a column that displays images
     * @param name the name of the column
     * @return a column
     * @param <R> the property objects
     */
    private static <R> TableColumn<R, ImageView> createImageViewColumn(String name) {
        TableColumn<R, ImageView> column = new TableColumn<>(name);
        column.setCellValueFactory(new PropertyValueFactory<>(name));
        column.setComparator(Comparator.comparing(imageView -> imageView.getUserData().toString()));
        return column;
    }

    /**
     * Removes all items and all columns from a table view
     * @param tableView a table view
     * @param <T> the object that is displayed in the table view
     */
    private static <T> void clearTable(TableView<T> tableView) {
        tableView.getItems().clear();
        tableView.getColumns().clear();
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    }
}
