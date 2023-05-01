package org.example.data.factory;

import org.example.data.enums.KitchenType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class KitchenFactoryTest {

    @Test
    public void storyStringIsEmptyTest()
    {
        String story = "";
        String kitchenType = "yes";
        String longitude = "1.0";
        String latitude = "2.0";

        List<String> values = Arrays.asList(kitchenType, story, longitude, latitude);

        Kitchen kitchenCreated = KitchenFactory.createKitchen(values);
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

        Kitchen kitchenCreated = KitchenFactory.createKitchen(values);
        Kitchen kitchenTarget = new Kitchen(KitchenType.NO, 1, 0, 0);
        Assertions.assertEquals(kitchenCreated, kitchenTarget);
    }
}