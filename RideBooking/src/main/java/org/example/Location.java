package org.example;

public class Location {
    int x,y;

    public Location(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public double distance(Location other){
        return Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2));
    }
}
