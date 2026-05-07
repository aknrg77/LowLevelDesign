package org.example;

import java.util.UUID;

public class UpiPayment implements PaymentStrategy{
    public void payment(User user, Double amount, Order order){
        order.paymentId = UUID.randomUUID().toString();
        System.out.println("Paying with UPI");
    }
}
