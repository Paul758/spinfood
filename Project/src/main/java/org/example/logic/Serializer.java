package org.example.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.example.data.enums.KitchenType;
import org.example.data.factory.Kitchen;
import org.example.data.jsonwrapper.KitchenWrapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Serializer {

    public static void main(String[] args) throws IOException {
        Serializer serializer = new Serializer();
        serializer.serialize();
    }

    public void serialize() throws IOException {
        Kitchen kitchen = new Kitchen(KitchenType.NO, 1, 10d, 10d);
        Kitchen kitchen2 = new Kitchen(KitchenType.YES, 1, 10d, 10d);

        KitchenWrapper kitchenWrapper = new KitchenWrapper(kitchen);
        KitchenWrapper kitchenWrapper2 = new KitchenWrapper(kitchen2);


        List<KitchenWrapper> kitchenList = List.of(kitchenWrapper, kitchenWrapper2);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);

        String jsonString = objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(kitchenWrapper);
        System.out.println(jsonString);
        objectMapper.writeValue(new File("src/main/java/org/example/artifacts/jsonTest.json"), kitchenList);
    }
}
