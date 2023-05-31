package org.example.data.factory;
import org.example.data.Coordinate;
import org.example.data.enums.KitchenType;

/** Data class to hold the kitchen values from the .csv file
 * Has a coordinate object to hold the longitude and latitude values
 * Has a kitchenType object to hold the kitchen type
 * Has a story value to hold the story of the kitchen
 * @author Felix Groß
 * @version 1.0
 */
public class Kitchen {
    public final KitchenType kitchenType;
    public final int story;
    public final Coordinate coordinate;

    public Kitchen(KitchenType kitchenType, int story, double longitude, double latitude) {
        this.kitchenType = kitchenType;
        this.story = story;
        this.coordinate = new Coordinate(longitude, latitude);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        Kitchen kitchen = (Kitchen) obj;
        return this.story == kitchen.story && this.coordinate.equals(kitchen.coordinate);
    }

    @Override
    public String toString() {
        return "[" + "kitchenType = " + kitchenType + ", story=" + story
                + ", coordinate=" + coordinate + "]";
    }
}
