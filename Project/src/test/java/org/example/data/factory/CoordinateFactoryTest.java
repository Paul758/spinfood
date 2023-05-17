package org.example.data.factory;

import org.example.data.Coordinate;
import org.example.data.tools.Keywords;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CoordinateFactoryTest {

    static Map<String, Integer> keywordMap = new HashMap<>();

    @BeforeAll
    public static void Setup() {
        keywordMap.put(Keywords.kitchenLongitude, 0);
        keywordMap.put(Keywords.kitchenLatitude, 1);
    }

    @Test
    void createCoordinate() {

        String longitude = "1.0";
        String latitude = "2.0";

        List<String> values = Arrays.asList(longitude, latitude);

        Coordinate coordinateCreated = CoordinateFactory.createCoordinate(values, keywordMap);
        Coordinate targetCoordinate = new Coordinate(1.0,2.0);

        Assertions.assertEquals(targetCoordinate, coordinateCreated);
    }
}