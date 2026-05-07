package org.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DeliveryService deliveryService = new DeliveryService();

        FoodItems burger = new FoodItems("f1", "Burger", 120);
        FoodItems fries = new FoodItems("f2", "Fries", 60);
        FoodItems sushi = new FoodItems("f3", "Sushi", 220);

        HashMap<FoodItems, Boolean> menu = new HashMap<>();
        menu.put(burger, true);
        menu.put(fries, true);

        Restaurant spiceHub = new Restaurant("r1", "SpiceHub", new Location(2, 2), menu);
        deliveryService.addRestaurant(spiceHub);

        Driver driver1 = new Driver("d1", "Ravi", new Location(1, 1));
        Driver driver2 = new Driver("d2", "Neha", new Location(6, 6));
        deliveryService.addDriver(driver1);
        deliveryService.addDriver(driver2);

        User alice = new User("Alice", "u1", new Location(4, 4));
        alice.orderHistory = new HashMap<>();

        System.out.println("=== Test Case 1: Successful order and delivery ===");
        Order order1 = deliveryService.orderFood(
                alice,
                Arrays.asList(burger, fries),
                spiceHub,
                PaymentMethod.UPI
        );
        if (order1 != null) {
            System.out.println("Order created: " + order1.id + ", status: " + order1.status);
            deliveryService.deliverOrder(order1);
            System.out.println("Final status: " + order1.status);
        }

        System.out.println("\n=== Test Case 2: Item unavailable in restaurant ===");
        Order order2 = deliveryService.orderFood(
                alice,
                List.of(sushi),
                spiceHub,
                PaymentMethod.Stripe
        );
        System.out.println("Order expected null -> " + order2);

        System.out.println("\n=== Test Case 3: Restaurant not registered in system ===");
        Restaurant ghostKitchen = new Restaurant("r2", "GhostKitchen", new Location(9, 9), menu);
        Order order3 = deliveryService.orderFood(
                alice,
                List.of(burger),
                ghostKitchen,
                PaymentMethod.Paypal
        );
        System.out.println("Order expected null -> " + order3);

        System.out.println("\n=== Test Case 4: No drivers available ===");
        driver1.is_available = false;
        driver2.is_available = false;
        Order order4 = deliveryService.orderFood(
                alice,
                List.of(burger),
                spiceHub,
                PaymentMethod.UPI
        );
        System.out.println("Order expected null -> " + order4);
    }
}
