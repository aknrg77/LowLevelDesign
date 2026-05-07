package org.example;

import java.util.HashMap;

public class User {
    String name;
    String id;
    Location location;
    HashMap<String, Order> orderHistory;

    public User(String name, String id, Location location) {
        this.name = name;
        this.id = id;
        this.location = location;
    }
}
