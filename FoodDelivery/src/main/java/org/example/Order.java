package org.example;

import java.util.List;

public class Order {
    String id;
    Restaurant restaurantId;
    User userId;
    double amount;
    List<FoodItems> foodItemsId;
    OrderStatus status;
    String paymentId;
    Driver driver;

    public Order(String id, Restaurant restaurantId, double amount, User userId, List<FoodItems> foodItemsId, OrderStatus status) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.amount = amount;
        this.userId = userId;
        this.foodItemsId = foodItemsId;
        this.status = status;
        this.paymentId = null;
        this.driver = null;

    }
}
