package org.example.data;

public class Coordinate {

    public float longitude;
    public float latitude;

    public Coordinate(float longitude, float latitude){
        this.longitude = longitude;
        this.latitude = latitude;
    }

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
        return "(" + "longitude =" + longitude + ", latitude = " + latitude + ")";
    }
}
