package org.example.view.tools;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import org.example.logic.enums.Criteria;
import org.example.logic.matchingalgorithms.MatchCosts;
import org.example.view.TabController;

import java.util.ArrayList;
import java.util.List;

public class MatchCostChooser {

    @FXML
    Text ageText, genderText, foodPreferenceText, matchCountText, pathLengthText;
    @FXML
    ComboBox<Integer> ageBox, genderBox, foodPreferenceBox, matchCountBox, pathLengthBox;
    @FXML
    Button assignButton;
    private static final int CRITERIA_COUNT = 5;
    private static final ArrayList<Integer> BOX_VALUES = new ArrayList<>(List.of(1,2,3,4,5));
    private List<ComboBox<Integer>> comboBoxes;
    private TabController tabController;

    public void setup(TabController tabController) {
        this.tabController = tabController;
        comboBoxes = new ArrayList<>();
        comboBoxes.add(ageBox);
        comboBoxes.add(genderBox);
        comboBoxes.add(foodPreferenceBox);
        comboBoxes.add(matchCountBox);
        comboBoxes.add(pathLengthBox);

        for (ComboBox<Integer> box : comboBoxes) {
            box.setItems(FXCollections.observableList(BOX_VALUES));
            box.setOnAction(e -> checkAssignButton());
        }

        checkAssignButton();
    }

    private void checkAssignButton() {
        boolean isValid = isValidAssignment();
        assignButton.setDisable(!isValid);
    }

    @FXML
    private void assign() {
        List<Criteria> criteria = new ArrayList<>();

        for (int i = 1; i <= CRITERIA_COUNT; i++) {
            criteria.add(getCriteria(i));
        }

        MatchCosts matchCosts = new MatchCosts(criteria);
        tabController.closeMatchCostChooserWindow(matchCosts);
    }

    @FXML
    private void matchWithoutPriorities() {
        tabController.closeMatchCostChooserWindow(null);
    }

    private Criteria getCriteria(int number) {
        if (ageBox.getValue() == number) {
            return Criteria.AGE_DIFFERENCE;
        } else if (genderBox.getValue() == number) {
            return Criteria.GENDER_DIFFERENCE;
        } else if (foodPreferenceBox.getValue() == number) {
            return Criteria.IDENTICAL_FOOD_PREFERENCE;
        } else if (matchCountBox.getValue() == number) {
            return Criteria.MATCH_COUNT;
        } else if (pathLengthBox.getValue() == number) {
            return Criteria.PATH_LENGTH;
        } else {
            throw new RuntimeException("cant find criteria for value " + number);
        }
    }

    private boolean isValidAssignment() {
        int[] arr = new int[CRITERIA_COUNT];
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
