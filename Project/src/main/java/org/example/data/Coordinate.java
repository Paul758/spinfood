package org.example.data;

/**
 * This class represents a coordinate with longitude and latitude.
 * Contains a constructor, equals and toString method.
 * @version 1.0
 *
 */
public class Coordinate {

    public double longitude;
    public double latitude;

    public Coordinate(double longitude, double latitude){
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public static double getDistance(Coordinate c1, Coordinate c2) {
        return Math.sqrt(Math.pow(c2.longitude - c1.longitude, 2) + Math.pow(c2.latitude - c1.latitude, 2));
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
