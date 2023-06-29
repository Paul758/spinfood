package org.example.data.json;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.data.factory.Kitchen;
import org.example.logic.structures.GroupMatched;
import org.example.logic.structures.PairMatched;


@JsonPropertyOrder({"course", "foodType", "kitchen", "cookingPair", "secondPair", "thirdPair"})
public class GroupWrapper {

    @JsonIgnore
    private final GroupMatched group;

    public GroupWrapper(GroupMatched group) {
        this.group = group;
    }

    @JsonGetter
    public String getCourse() {
        return switch (group.getMealType()) {
            case NONE -> throw new IllegalArgumentException();
            case STARTER -> "first";
            case MAIN -> "main";
            case DESSERT -> "dessert";
        };
    }

    @JsonGetter
    public String getFoodType() {
        return switch (group.getFoodPreference()) {
            case NONE, MEAT -> "meat";
            case VEGGIE -> "veggie";
            case VEGAN -> "vegan";
        };
    }

    @JsonGetter
    public JsonNode getKitchen() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Kitchen kitchen = group.getCook().getKitchen();
        return mapper.readTree(Serializer.serializeKitchen(kitchen));
    }

    @JsonGetter
    public JsonNode getCookingPair() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        PairMatched firstPair = group.getPairList().get(0);
        return mapper.readTree(Serializer.serializePair(firstPair));
    }

    @JsonGetter
    public JsonNode getSecondPair() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        PairMatched secondPair = group.getPairList().get(1);
        return mapper.readTree(Serializer.serializePair(secondPair));
    }

    @JsonGetter
    public JsonNode getThirdPair() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        PairMatched thirdPair = group.getPairList().get(2);
        return mapper.readTree(Serializer.serializePair(thirdPair));
    }
}
