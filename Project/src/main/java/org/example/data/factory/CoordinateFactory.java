package org.example.data.factory;

import org.example.data.Coordinate;
import org.example.data.tools.Keywords;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CoordinateFactory {

    public static Coordinate createCoordinate(final Collection<String> values, Map<String, Integer> keyWordMap) {
        ArrayList<String> data = new ArrayList<>(values);
        double longitude = Double.parseDouble(data.get(keyWordMap.get(Keywords.longitude)));
        double latitude = Double.parseDouble(data.get(keyWordMap.get(Keywords.latitude)));
        return new Coordinate(longitude, latitude);
    }
}
