package org.example;

public class Location {
    int x;
    int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    double distance(Location other){
        return Math.sqrt(Math.pow(this.x - other.x,2) + Math.pow(this.y - other.y,2));
    }
}
