package org.example;

import java.util.HashMap;

public class Driver {
    String id;
    String name;
    Location location;
    boolean is_available;
    HashMap<String, Order> orderHistory;

    public Driver(String id, String name, Location location) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.is_available = true;
        this.orderHistory = new HashMap<>();
    }
}
