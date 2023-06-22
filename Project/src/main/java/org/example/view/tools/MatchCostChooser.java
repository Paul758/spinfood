package org.example.view.tools;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.example.Main;
import org.example.logic.enums.Criteria;
import org.example.logic.matchingalgorithms.MatchCosts;
import org.example.view.DataTabController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MatchCostChooser {
    @FXML
    GridPane gridPane;
    @FXML
    Text ageText, genderText, foodPreferenceText;
    @FXML
    ComboBox<Integer> ageBox, genderBox, foodPreferenceBox;
    @FXML
    Button assignButton;
    private List<ComboBox<Integer>> comboBoxes;
    private DataTabController dataTabController;

    public void setup(DataTabController dataTabController) {
        this.dataTabController = dataTabController;
        comboBoxes = new ArrayList<>();
        comboBoxes.add(ageBox);
        comboBoxes.add(genderBox);
        comboBoxes.add(foodPreferenceBox);

        List<Integer> possibleValues = new ArrayList<>(List.of(1,2,3));
        for (ComboBox<Integer> box : comboBoxes) {
            box.setItems(FXCollections.observableList(possibleValues));
            box.setOnAction(e -> checkAssignButton());
        }

        checkAssignButton();
    }

    public void checkAssignButton() {
        boolean isValid = isValidAssignment();
        assignButton.setDisable(!isValid);
    }

    @FXML
    public void assign() throws IOException {
        List<Criteria> criteria = new ArrayList<>();
        criteria.add(getCriteria(1));
        criteria.add(getCriteria(2));
        criteria.add(getCriteria(3));

        MatchCosts matchCosts = new MatchCosts(criteria);
        dataTabController.closeMatchCostChooserWindow(matchCosts);
    }

    public Criteria getCriteria(int number) {
        if (ageBox.getValue() == number) {
            return Criteria.AGE_DIFFERENCE;
        } else if (genderBox.getValue() == number) {
            return Criteria.GENDER_DIFFERENCE;
        } else if (foodPreferenceBox.getValue() == number) {
            return Criteria.IDENTICAL_FOOD_PREFERENCE;
        } else {
            throw new RuntimeException("cant find criteria for value " + number);
        }
    }

    public boolean isValidAssignment() {
        int[] arr = new int[3];
        for (ComboBox<Integer> box : comboBoxes) {
            if (box.getValue() != null) {
                arr[box.getValue() - 1]++;
            }
        }
        for (int value : arr) {
            if (value != 1) {
                return false;
            }
        }
        return true;
    }
}
