package org.example.view.windows;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import org.example.logic.enums.Criteria;
import org.example.logic.matchingalgorithms.MatchCosts;
import org.example.view.controller.TabController;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Handles the functionality for the window where the user can choose the priorities of the different criteria
 * for a matching algorithm
 */
public class MatchCostChooser {

    @FXML
    private ComboBox<Integer> ageBox, genderBox, foodPreferenceBox, matchCountBox, pathLengthBox;
    @FXML
    private Button assignButton;

    private static final int CRITERIA_COUNT = 5;
    private static final ArrayList<Integer> BOX_VALUES = new ArrayList<>(List.of(1,2,3,4,5));
    private List<ComboBox<Integer>> comboBoxes;
    private final TabController tabController;
    private final Consumer<MatchCosts> consumer;

    public MatchCostChooser(TabController tabController, Consumer<MatchCosts> consumer) {
        this.tabController = tabController;
        this.consumer = consumer;
    }

    @FXML
    private void initialize() {
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

    /**
     * Activates the button when the assigned priorities are valid, deactivates the button if not
     */
    private void checkAssignButton() {
        boolean isValid = isValidAssignment();
        assignButton.setDisable(!isValid);
    }

    /**
     * Creates a MatchCost Object with the priorities the user has chosen, closes the window and
     * calls the given consumer function for the MatchCost Object
     */
    @FXML
    private void assign() {
        List<Criteria> criteria = new ArrayList<>();

        for (int i = 1; i <= CRITERIA_COUNT; i++) {
            criteria.add(getCriteria(i));
        }

        MatchCosts matchCosts = new MatchCosts(criteria);
        tabController.closePopupWindow();
        consumer.accept(matchCosts);
    }

    /**
     * Closes the window and calls the given consumer function with null for the MatchCost Objects
     */
    @FXML
    private void matchWithoutPriorities() {
        tabController.closePopupWindow();
        consumer.accept(null);
    }

    /**
     * gets the different criteria in the priority order that the user has selected
     * @param number the priority value
     * @return the Criteria for the given value
     */
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

    /**
     * checks if an assignment is valid, its valid when a value is selected in each combo box each value is
     * only selected one
     * @return true if the assignment is valid, otherwise false
     */
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
