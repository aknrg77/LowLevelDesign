package org.example;

import java.util.List;

public class RefundState implements State{
    @Override
    public void insertCoin(VendingMachine machine, Coin coin){
        System.out.println("Refund In process");
    }
    @Override
    public void selectProduct(VendingMachine machine, Product product){
        System.out.println("Refund In process");
    }
    @Override
    public void dispenseProduct(VendingMachine machine){
        System.out.println("Refund In process");
    }
    @Override
    public void refund(VendingMachine machine){
        System.out.println("Remaining Amount -> " + machine.balance);
        machine.balance = 0;
        machine.setState(new IdleState());
    }
}
