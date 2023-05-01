package org.example.data.factory;

import org.example.data.enums.KitchenType;
import org.example.data.tools.Keywords;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KitchenFactoryTest {

    static Map<String, Integer> keywordMap = new HashMap<>();
    @BeforeAll
    public static void Setup() {
        keywordMap.put(Keywords.kitchen, 0);
        keywordMap.put(Keywords.kitchenStory, 1);
        keywordMap.put(Keywords.kitchenLongitude, 2);
        keywordMap.put(Keywords.kitchenLatitude, 3);
    }

    @Test
    public void storyStringIsEmptyTest()
    {
        String story = "";
        String kitchenType = "yes";
        String longitude = "1.0";
        String latitude = "2.0";

        List<String> values = Arrays.asList(kitchenType, story, longitude, latitude);

        Kitchen kitchenCreated = KitchenFactory.createKitchen(values, keywordMap);
        Kitchen kitchenTarget = new Kitchen(KitchenType.YES, 0, 1.0f, 2.0f);
        Assertions.assertEquals(kitchenCreated, kitchenTarget);
    }

    @Test
    public void kitchenTypeIsNoTest()
    {
        String story = "1.0";
        String kitchenType = "no";
        String longitude = "1.0";
        String latitude = "2.0";

        List<String> values = Arrays.asList(kitchenType, story, longitude, latitude);

        Kitchen kitchenCreated = KitchenFactory.createKitchen(values, keywordMap);
        Kitchen kitchenTarget = new Kitchen(KitchenType.NO, 1, 0, 0);
        Assertions.assertEquals(kitchenCreated, kitchenTarget);
    }
}