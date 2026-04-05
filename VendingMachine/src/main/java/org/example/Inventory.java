package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Inventory {
    HashMap<Product, Integer> stock = new HashMap<>();

    public Inventory() {
    }

    void addProduct(Product product){
        stock.put(product, stock.getOrDefault(product, 0) + 1);
    }

    void dispense(Product product){
        stock.put(product, stock.getOrDefault(product, 0) - 1);
    }

}
