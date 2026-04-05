package org.example;

import java.util.List;

public class HasMoneyState implements State{
    @Override
    public void insertCoin(VendingMachine machine, Coin coin){
        machine.balance += coin.getValue();
        System.out.println("Inserted Coin ->" + coin.getValue());
    }
    @Override
    public void selectProduct(VendingMachine machine, Product product){
        if(!machine.inventory.stock.containsKey(product)){
            System.out.println("Item not found");
            return;
        }
        if(machine.balance < product.price){
            System.out.println("Not available balance");
            return;
        }
        machine.setSelectedProduct(product);
        System.out.println("Selected Product ->" + product.name);
        machine.setState(new DispenseState());
    }
    @Override
    public void dispenseProduct(VendingMachine machine){
        System.out.println("Select Product First");
    }
    @Override
    public void refund(VendingMachine machine){
        System.out.println("Select Product First");
    }
}
