package Data;

public record Kitchen(KitchenType kitchenType, int kitchenStory, Coordinate coordinate) {

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        Kitchen kitchen = (Kitchen) obj;
        return this.kitchenStory == kitchen.kitchenStory && this.coordinate.equals(kitchen.coordinate);
    }

    @Override
    public String toString() {
        return "("
                + kitchenType
                + ", " + kitchenStory
                + ", " + coordinate
                + ")";
    }
}
