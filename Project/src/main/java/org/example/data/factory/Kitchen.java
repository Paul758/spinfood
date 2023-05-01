package org.example.data.factory;
import org.example.data.Coordinate;
import org.example.data.enums.KitchenType;

public class Kitchen {

    public final KitchenType kitchenType;
    public final int story;
    public final Coordinate coordinate;

    public Kitchen(KitchenType kitchenType, int story, float longitude, float latitude){
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
        return "[ " +
                "kitchenType = " + kitchenType +
                ", story=" + story +
                ", coordinate=" + coordinate +
                " ]";
    }
}
