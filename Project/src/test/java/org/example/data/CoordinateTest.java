package org.example.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinateTest {

    // got expected values with https://www.calculator.net/distance-calculator.html
    @Test
    void getDistance() {
        Coordinate c1 = new Coordinate(1,1);
        Coordinate c2 = new Coordinate(4,5);
        Assertions.assertEquals(5, Coordinate.getDistance(c1, c2));

        Coordinate c3 = new Coordinate(9.5428, -1223.4567);
        Coordinate c4 = new Coordinate(-50.432, 3.7823);
        Assertions.assertEquals(1228.7036012627457, Coordinate.getDistance(c3, c4));
    }
}