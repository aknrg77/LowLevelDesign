package org.example;

public interface PaymentStrategy {
    void payment(User user, Double amount, Order order);
}
