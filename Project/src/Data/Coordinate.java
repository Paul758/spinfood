package Data;

public record Coordinate(float longitude, float latitude) {

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        Coordinate coordinate = (Coordinate) obj;
        return this.longitude == coordinate.longitude && this.latitude == coordinate.latitude;
    }

    @Override
    public String toString() {
        return "(" + longitude + ", " + latitude + ")";
    }
}
