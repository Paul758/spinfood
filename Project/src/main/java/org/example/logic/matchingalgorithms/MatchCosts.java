package org.example.logic.matchingalgorithms;

import org.example.logic.enums.Criteria;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that contains the data for the costs of matching solos and pairs
 */
public class MatchCosts {

    private double foodPreferenceCosts = 1;
    private double ageCosts = 1;
    private double genderCosts = 1;
    private double pathLengthCosts = 1;
    private double mostMatchesCosts = 1;
    private double kitchenCosts = 1;


    /**
     * Takes a list of criteria as a ranking. The most important criterion gets the highest weight multiplier
     * value. This influences potential matchings, as important criteria lead to more costs
     * @param important1 the most important criterion
     * @param important2 criterion
     * @param important3 criterion
     * @param important4 criterion
     * @param important5 the least important criterion
     */
    public MatchCosts(
            Criteria important1,
            Criteria important2,
            Criteria important3,
            Criteria important4,
            Criteria important5) {

        List<Criteria> ranking = new ArrayList<>(List.of(important1, important2, important3, important4, important5));

        for (int i = 0; i < ranking.size(); i++) {
            float weightMultiplier = 5 - i;
            Criteria criteria = ranking.get(i);
            calculateValue(criteria, weightMultiplier);
        }
    }



    /**
     * Assigns a value to the passed criterion, depending on its importance
     * @param criteria a soft criteria
     * @param weightMultiplier a float value between 1 and 5
     */
    private void calculateValue(Criteria criteria, float weightMultiplier) {
        switch (criteria){
            case IDENTICAL_FOOD_PREFERENCE -> foodPreferenceCosts = weightMultiplier;
            case AGE_DIFFERENCE -> ageCosts = weightMultiplier;
            case GENDER_DIFFERENCE -> genderCosts = weightMultiplier;
            case PATH_LENGTH -> pathLengthCosts = weightMultiplier;
            case MATCH_COUNT -> mostMatchesCosts = weightMultiplier;
        }
    }

    /**
     * Default constructor, every cost multiplier for each criterion is 1, meaning they are equally important
     */
    public MatchCosts() {

    }

    public static MatchCosts getDefault() {
        return new MatchCosts();
    }


    //Getter methods
    public double getFoodPreferenceCosts() {
        return foodPreferenceCosts;
    }

    public double getAgeCosts() {
        return ageCosts;
    }

    public double getGenderCosts() {
        return genderCosts;
    }

    public double getPathLengthCosts() {
        return pathLengthCosts;
    }

    public double getKitchenCosts() {
        return kitchenCosts;
    }
}
