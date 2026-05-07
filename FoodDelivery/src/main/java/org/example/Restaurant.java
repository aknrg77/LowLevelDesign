package org.example;

import java.util.HashMap;

public class Restaurant {
    String id;
    String name;
    Location location;
    HashMap<FoodItems, Boolean> menu;

    public Restaurant(String id, String name, Location location, HashMap<FoodItems, Boolean> menu) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.menu = menu;
    }


}
