package org.example.data;

public class Coordinate {

    public float longitude;
    public float latitude;

    public Coordinate(float longitude, float latitude){
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "(" + "longitude =" + longitude + ", latitude = " + latitude + ")";
    }
}
