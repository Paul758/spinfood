package org.example.view.windows;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import org.example.logic.enums.MealType;
import org.example.logic.structures.GroupMatched;
import org.example.logic.structures.PairMatched;
import org.example.view.commands.CreateGroupCommand;
import org.example.view.controller.GroupListTabController;
import org.example.view.properties.PairMatchedProperty;
import org.example.view.tools.TableViewTools;

import java.util.ArrayList;
import java.util.List;

public class GroupBuilder {

    @FXML
    TableView<PairMatchedProperty> cookTableView, pairATableView, pairBTableView;
    @FXML
    ComboBox<MealType> mealTypeComboBox;
    @FXML
    Button createGroupButton;
    private final GroupListTabController controller;
    private final List<PairMatched> successors;
    private PairMatched selectedCook, selectedPairA, selectedPairB;

    public GroupBuilder(List<PairMatched> successors, GroupListTabController controller) {
        this.controller = controller;
        this.successors = successors;
    }

    @FXML
    private void initialize() {
        TableViewTools.fillTable(successors, cookTableView, PairMatchedProperty::new, PairMatchedProperty.getSummaryViewColumns());
        TableViewTools.fillTable(successors, pairATableView, PairMatchedProperty::new, PairMatchedProperty.getSummaryViewColumns());
        TableViewTools.fillTable(successors, pairBTableView, PairMatchedProperty::new, PairMatchedProperty.getSummaryViewColumns());

        ArrayList<MealType> mealTypes = new ArrayList<>(List.of(MealType.STARTER, MealType.MAIN, MealType.DESSERT));
        ObservableList<MealType> boxList = FXCollections.observableArrayList(mealTypes);
        mealTypeComboBox.setItems(boxList);
        mealTypeComboBox.getSelectionModel().select(0);

        checkAssignButton();
    }

    private void checkAssignButton() {
        boolean isActive = selectedCook != null && selectedPairA != null && selectedPairB != null
                && selectedCook != selectedPairA && selectedCook != selectedPairB && selectedPairA != selectedPairB
                && mealTypeComboBox.getValue() != null;
        createGroupButton.setDisable(!isActive);
    }

    @FXML
    private void createGroup() {
        MealType mealType = mealTypeComboBox.getValue();
        GroupMatched groupMatched = new GroupMatched(selectedCook, selectedPairA, selectedPairB, mealType);
        CreateGroupCommand command = new CreateGroupCommand(controller.getMatchingRepository(), groupMatched);
        controller.run(command);
        controller.closePopupWindow();
    }

    @FXML
    private void selectCook() {
        PairMatchedProperty property = cookTableView.getSelectionModel().getSelectedItem();
        selectedCook = property.pairMatched();
        checkAssignButton();
    }

    @FXML
    private void selectPairA() {
        PairMatchedProperty property = pairATableView.getSelectionModel().getSelectedItem();
        selectedPairA = property.pairMatched();
        checkAssignButton();
    }

    @FXML
    private void selectPairB() {
        PairMatchedProperty property = pairBTableView.getSelectionModel().getSelectedItem();
        selectedPairB = property.pairMatched();
        checkAssignButton();
    }
}
