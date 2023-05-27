package org.example.logic.tools.algorithms;

import org.example.logic.enums.Criteria;

import java.util.ArrayList;
import java.util.List;

public class MatchCosts {

    private double foodPreferenceCosts = 1;
    private double ageCosts = 1;
    private double genderCosts = 1;
    private double pathLengthCosts = 1;
    private double mostMatchesCosts = 1;

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

    private void calculateValue(Criteria criteria, float weightMultiplier) {
        switch (criteria){
            case IDENTICAL_FOOD_PREFERENCE -> foodPreferenceCosts = weightMultiplier;
            case AGE_DIFFERENCE -> ageCosts = weightMultiplier;
            case GENDER_DIFFERENCE -> genderCosts = weightMultiplier;
            case PATH_LENGTH -> pathLengthCosts = weightMultiplier;
            case MATCH_COUNT -> mostMatchesCosts = weightMultiplier;
        }
    }

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

    public double getMostMatchesCosts() {
        return mostMatchesCosts;
    }
}
