package org.example.data.json;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.data.enums.KitchenType;
import org.example.data.structures.Solo;

@JsonPropertyOrder({"id", "name", "foodPreference", "age", "gender", "kitchen"})
public class SoloWrapper {

    @JsonIgnore
    Solo solo;

    public SoloWrapper(Solo solo) {
        this.solo = solo;
    }

    @JsonGetter
    public String getId() {
        return solo.getPerson().id();
    }

    @JsonGetter
    public String getName() {
        return solo.getPerson().name();
    }

    @JsonGetter
    public String getFoodPreference() {
        return switch (solo.getFoodPreference()) {
            case MEAT -> "meat";
            case NONE -> "none";
            case VEGGIE -> "veggie";
            case VEGAN -> "vegan";
        };
    }

    @JsonGetter
    public int getAge() {
        return solo.getPerson().age();
    }

    @JsonGetter
    public String getGender() {
        return switch (solo.getPerson().sex()) {
            case MALE -> "male";
            case FEMALE -> "female";
            case OTHER -> "other";
        };
    }

    @JsonGetter
    public JsonNode getKitchen() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        KitchenType kitchenType = solo.getKitchen().getKitchenType();

        if (kitchenType.equals(KitchenType.NO)) {
            return null;
        } else {
            return objectMapper.readTree(Serializer.serializeKitchen(solo.getKitchen()));
        }
    }
}
