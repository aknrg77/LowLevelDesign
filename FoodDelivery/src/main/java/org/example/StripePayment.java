package org.example;

import java.util.UUID;

public class StripePayment implements PaymentStrategy{
    public void payment(User user, Double amount, Order order){
        order.paymentId = UUID.randomUUID().toString();
        System.out.println("Paying with Stripe");
    }
}
