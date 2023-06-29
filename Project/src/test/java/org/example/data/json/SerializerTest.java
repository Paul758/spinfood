package org.example.data.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import org.example.data.enums.FoodPreference;
import org.example.data.enums.KitchenType;
import org.example.data.enums.Sex;
import org.example.data.factory.Kitchen;
import org.example.data.factory.Person;
import org.example.data.structures.Solo;
import org.example.logic.enums.MealType;
import org.example.logic.structures.GroupMatched;
import org.example.logic.structures.MatchingRepository;
import org.example.logic.structures.PairMatched;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Set;

class SerializerTest {

    static Kitchen kitchen;
    static Solo soloA;
    static Solo soloB;
    static List<Solo> solos;
    static PairMatched pair;
    static List<PairMatched> pairs;
    static GroupMatched group;
    static List<GroupMatched> groups;

    @BeforeAll
    static void setup() {
        Person personA = new Person("1234", "name", 20, Sex.MALE);
        kitchen = new Kitchen(KitchenType.MAYBE, 1, 5.0, 10.0);
        soloA = new Solo(personA, FoodPreference.MEAT, kitchen);
        Person personB = new Person("5678", "nameB", 20, Sex.FEMALE);
        Kitchen kitchenB = new Kitchen(KitchenType.NO, -1, 0.0, 0.0);
        soloB = new Solo(personB, FoodPreference.MEAT, kitchenB);
        solos = List.of(soloA, soloB);

        pair = new PairMatched(soloA, soloB);
        pairs = List.of(pair);

        group = new GroupMatched(pair, pair, pair, MealType.MAIN);
        groups = List.of(group);
    }

    private static boolean validate(String output, String pathToSchema) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode objectNode = mapper.readTree(output);

        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
        JsonSchema jsonSchema = factory.getSchema(SerializerTest.class.getResourceAsStream(pathToSchema));
        Set<ValidationMessage> errors = jsonSchema.validate(objectNode);

        return errors.isEmpty();
    }

    @Test
    void serializeKitchen() throws Exception {
        String string = Serializer.serializeKitchen(kitchen);
        String pathToSchema = "/json/kitchen_schema.json";

        boolean isValid = validate(string, pathToSchema);
        Assertions.assertTrue(isValid);
    }

    @Test
    void serializeSolo_hasKitchen() throws Exception {
        String output = Serializer.serializeSolo(soloA);
        String pathToSchema = "/json/participant_schema.json";

        boolean isValid = validate(output, pathToSchema);
        Assertions.assertTrue(isValid);
    }

    @Test
    void serializeSolo_hasNoKitchen() throws Exception {
        String output = Serializer.serializeSolo(soloB);
        String pathToSchema = "/json/participant_schema.json";

        boolean isValid = validate(output, pathToSchema);
        Assertions.assertTrue(isValid);
    }

    @Test
    void serializePair() throws JsonProcessingException {
        String output = Serializer.serializePair(pair);
        String pathToSchema = "/json/pair_schema.json";

        boolean isValid = validate(output, pathToSchema);
        Assertions.assertTrue(isValid);
    }

    @Test
    void serializeGroup() throws JsonProcessingException {
        String output = Serializer.serializeGroup(group);
        String pathToSchema = "/json/group_schema.json";

        boolean isValid = validate(output, pathToSchema);
        Assertions.assertTrue(isValid);
    }

    @Test
    void serializeGroupList() throws JsonProcessingException {
        String output = Serializer.serializeGroupList(groups);
        String pathToSchema = "/json/group_list_schema.json";

        boolean isValid = validate(output, pathToSchema);
        Assertions.assertTrue(isValid);
    }

    @Test
    void serializePairList() throws JsonProcessingException {
        String output = Serializer.serializePairList(pairs);
        String pathToSchema = "/json/pair_list_schema.json";

        boolean isValid = validate(output, pathToSchema);
        Assertions.assertTrue(isValid);
    }

    @Test
    void serializeParticipantList() throws JsonProcessingException {
        String output = Serializer.serializeSoloList(solos);
        String pathToSchema = "/json/participant_list_schema.json";

        boolean isValid = validate(output, pathToSchema);
        Assertions.assertTrue(isValid);
    }

    @Test
    void serializeRepository() throws JsonProcessingException {
        MatchingRepository mockRepository = Mockito.mock(MatchingRepository.class);
        Mockito.when(mockRepository.getMatchedGroupsCollection()).thenReturn(groups);
        Mockito.when(mockRepository.getPairSuccessors()).thenReturn(pairs);
        Mockito.when(mockRepository.getSoloSuccessors()).thenReturn(solos);

        String output = Serializer.serializeMatchingRepository(mockRepository);
        String pathToSchema = "/json/result_schema.json";

        boolean isValid = validate(output, pathToSchema);
        Assertions.assertTrue(isValid);
    }
}