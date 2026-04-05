package org.example;

import java.util.List;

public class IdleState implements State {
    @Override
    public void insertCoin(VendingMachine machine, Coin coin){

        machine.balance += coin.getValue();
        System.out.println("Inserted Coin ->" + coin.getValue());
        machine.setState(new HasMoneyState());
    }
    @Override
    public void selectProduct(VendingMachine machine, Product product){
        System.out.println("Insert Coin First");
    }
    @Override
    public void dispenseProduct(VendingMachine machine){
        System.out.println("Select Product First");
    }
    @Override
    public void refund(VendingMachine machine){
        System.out.println("Dispense Product First");
    }

}
