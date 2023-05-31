package org.example.logic.tools.algorithms;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class KMeansSameSizeTest {

    @Test
    void getCLuster() {
        List<Point> points = new ArrayList<>();
        Random random = new Random(1234);

        for (int i = 0; i < 100; i++) {
            double x = random.nextDouble(0, 100);
            double y = random.nextDouble(0, 100);
            points.add(new Point(null,x,y));
        }

        List<List<Point>> result = KMeansSameSize.getCluster(points, 10);

        for (int i = 0; i < result.size(); i++) {
            for (Point point : result.get(i)) {
                System.out.println(point.x + ", " + point.y + ", " + i);
            }
        }
    }
}