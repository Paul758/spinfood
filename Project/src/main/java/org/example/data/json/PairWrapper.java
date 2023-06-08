package org.example.data.json;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.logic.structures.PairMatched;

@JsonPropertyOrder({"premade", "foodPreference", "firstParticipant", "secondParticipant"})
public class PairWrapper {

    @JsonIgnore
    PairMatched pair;

    public PairWrapper(PairMatched pair) {
        this.pair = pair;
    }

    @JsonGetter
    public boolean getPremade() {
        return pair.isPreMatched();
    }

    @JsonGetter
    public String getFoodPreference() {
        return switch (pair.getFoodPreference()) {
            case MEAT -> "meat";
            case NONE -> "none";
            case VEGGIE -> "veggie";
            case VEGAN -> "vegan";
        };
    }

    @JsonGetter
    public JsonNode getFirstParticipant() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(Serializer.serializeSolo(pair.getSoloA()));
    }

    @JsonGetter
    public JsonNode getSecondParticipant() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(Serializer.serializeSolo(pair.getSoloB()));
    }
}
