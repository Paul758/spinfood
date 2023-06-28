package org.example.data.json;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.data.structures.Solo;
import org.example.logic.metrics.GroupListMetrics;
import org.example.logic.structures.GroupMatched;
import org.example.logic.structures.MatchingRepository;
import org.example.logic.structures.PairMatched;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({"groups", "pairs", "successorPairs", "successorParticipants"})
public class MatchingRepositoryWrapper {

    @JsonIgnore
    private final MatchingRepository repository;

    public MatchingRepositoryWrapper(MatchingRepository repository) {
        this.repository = repository;
    }

    @JsonGetter
    public JsonNode getGroups() throws JsonProcessingException {
        List<GroupMatched> groups = new ArrayList<>(repository.getMatchedGroupsCollection());
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(Serializer.serializeGroupList(groups));
    }

    @JsonGetter
    public JsonNode getPairs() throws JsonProcessingException {
        List<GroupMatched> groups = new ArrayList<>(repository.getMatchedGroupsCollection());
        List<PairMatched> pairs = GroupListMetrics.getPairsInGroups(groups);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(Serializer.serializePairList(pairs));
    }

    @JsonGetter
    public JsonNode getSuccessorPairs() throws JsonProcessingException {
        List<PairMatched> pair = new ArrayList<>(repository.getPairSuccessors());
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(Serializer.serializePairList(pair));
    }

    @JsonGetter
    public JsonNode getSuccessorParticipants() throws JsonProcessingException {
        List<Solo> solos = new ArrayList<>(repository.getSoloSuccessors());
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(Serializer.serializeSoloList(solos));
    }
}
