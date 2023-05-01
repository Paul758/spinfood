package org.example.data.factory;
import org.example.data.Coordinate;
import org.example.data.enums.KitchenType;

public class Kitchen {
    public final KitchenType kitchenType;
    public final int story;
    public final Coordinate coordinate;

    public Kitchen(KitchenType kitchenType, int story, float longitude, float latitude) {
        this.kitchenType = kitchenType;
        this.story = story;
        this.coordinate = new Coordinate(longitude, latitude);
    }

    @Override
    public String toString() {
        return "[" + "kitchenType = " + kitchenType + ", story=" + story
                + ", coordinate=" + coordinate + "]";
    }
}
