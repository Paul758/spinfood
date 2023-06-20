package org.example.view.tools;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.example.data.structures.Solo;
import org.example.view.properties.SoloProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SoloTable {

    List<SoloTableListener> listeners;
    private HashMap<Solo, SoloProperty> soloToPropertyMap;
    private HashMap<SoloProperty, Solo> propertyToSoloMap;

    private TableView<SoloProperty> soloTableView;
    public SoloTable(List<Solo> soloList, AnchorPane parent) {
        listeners = new ArrayList<>();
        soloTableView = new TableView<>();
        List<SoloProperty> soloProperties = createPropertyList(soloList);
        ObservableList<SoloProperty> data = FXCollections.observableArrayList(soloProperties);

        setUpColumns();
        soloTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        AnchorPane.setLeftAnchor(soloTableView, 0d);
        AnchorPane.setRightAnchor(soloTableView, 0d);
        AnchorPane.setBottomAnchor(soloTableView, 0d);
        AnchorPane.setTopAnchor(soloTableView, 0d);

        soloTableView.setItems(data);

        setupMouseEvents();

        parent.getChildren().add(soloTableView);
    }

    public void addListener(SoloTableListener listener) {
        listeners.add(listener);
    }

    public void addContextMenu(ContextMenu contextMenu) {
        soloTableView.setContextMenu(contextMenu);
    }

    private void setUpColumns() {
        TableColumn<SoloProperty, String> gender = new TableColumn<>("Gender");
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        TableColumn<SoloProperty, String> age = new TableColumn<>("Age");
        age.setCellValueFactory(new PropertyValueFactory<>("age"));
        TableColumn<SoloProperty, String> foodPreference = new TableColumn<>("foodPreference");
        foodPreference.setCellValueFactory(new PropertyValueFactory<>("foodPreference"));
        TableColumn<SoloProperty, String> kitchenType = new TableColumn<>("KitchenType");
        kitchenType.setCellValueFactory(new PropertyValueFactory<>("kitchenType"));

        soloTableView.getColumns().add(gender);
        soloTableView.getColumns().add(age);
        soloTableView.getColumns().add(foodPreference);
        soloTableView.getColumns().add(kitchenType);
    }

    private void setupMouseEvents() {
        soloTableView.setOnMouseClicked((MouseEvent e) -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                selectItemLeftClick();
            }
        });
    }

    private void selectItemLeftClick() {
        SoloProperty selectedProperty = soloTableView.getSelectionModel().getSelectedItem();
        Solo selectedSolo = propertyToSoloMap.get(selectedProperty);
        for (SoloTableListener listener : listeners) {
            listener.onSelectItemLeftClick(selectedSolo, this);
        }
    }

    private List<SoloProperty> createPropertyList(List<Solo> solos) {
        List<SoloProperty> soloProperties = new ArrayList<>();
        propertyToSoloMap = new HashMap<>();
        soloToPropertyMap = new HashMap<>();

        for (Solo solo : solos) {
            SoloProperty property = new SoloProperty(solo);
            propertyToSoloMap.put(property, solo);
            soloToPropertyMap.put(solo, property);
            soloProperties.add(property);
        }

        return soloProperties;
    }
}
