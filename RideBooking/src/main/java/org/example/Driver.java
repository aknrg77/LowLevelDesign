package org.example;

public class Driver {
    String id;
    Location location;
    boolean is_available;

    public Driver(String id, Location location) {
        this.id = id;
        this.location = location;
        this.is_available = true;
    }
}
