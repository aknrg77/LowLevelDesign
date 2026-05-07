package org.example;

import java.util.*;

public class DeliveryService {
    HashMap<String, Restaurant> restaurants;
    HashMap<String, Driver> drivers;
    HashMap<PaymentMethod, PaymentStrategy> paymentStrategies;

    DeliveryService(){
        restaurants = new HashMap<>();
        drivers = new HashMap<>();
        paymentStrategies = new HashMap<>();
        paymentStrategies.put(PaymentMethod.Stripe, new StripePayment());
        paymentStrategies.put(PaymentMethod.Paypal, new PaypalPayment());
        paymentStrategies.put(PaymentMethod.UPI, new UpiPayment());

    }

    public void addDriver(Driver driver){
        this.drivers.put(driver.id, driver);
    }

    public void addRestaurant(Restaurant restaurant){
        this.restaurants.put(restaurant.id, restaurant);
    }

    public Order orderFood(User user, List<FoodItems> foodItems, Restaurant restaurant, PaymentMethod method){
        //check restaurant
       if(!restaurants.containsKey(restaurant.id)){
           System.out.println("Restaurant not found");
           return null;
       }

       //check if food is available in this restaurant
       double totalAmount = 0;
       for(var x: foodItems){
           if(!restaurant.menu.containsKey(x)){
               System.out.println("Item not available in restaurant");
               return null;
           }
           totalAmount+=x.price;
       }
       Driver driver = findNearestDriver(restaurant);
       if(driver == null){
           System.out.println("No Drivers Available");
           return null;
       }

       //Payment
       Order order = new Order(UUID.randomUUID().toString(), restaurant, totalAmount, user, foodItems, OrderStatus.Created);
       paymentStrategies.get(method).payment(user, totalAmount, order);
       order.status = OrderStatus.Accepted;


       //Assign to delivery Partner;
       driver.is_available = false;
       driver.orderHistory.put(order.id, order);
       order.status = OrderStatus.OutForDelivery;
       order.driver = driver;
       return order;
    }

    Order deliverOrder(Order order){
        order.driver.is_available = true;
        System.out.println("Delivery Complete");
        order.driver.location = order.userId.location;
        order.status = OrderStatus.Delivered;
        return order;
    }


    Driver findNearestDriver(Restaurant restaurants){
        double dist = Integer.MAX_VALUE;
        Driver currDriver = null;
        for(var x: drivers.values()){
           if(!x.is_available) continue;
           double curr_dist =  restaurants.location.distance(x.location);
           if(dist > curr_dist) {
               dist = curr_dist;
               currDriver = x;
           }
        }
        return currDriver;
    }
}
