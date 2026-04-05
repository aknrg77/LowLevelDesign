package org.example;

public class DispenseState implements State{
    @Override
    public void insertCoin(VendingMachine machine, Coin coin){
        System.out.println("Please Wait Dispensing");
    }
    @Override
    public void selectProduct(VendingMachine machine, Product product){
        System.out.println("Please Wait Dispensing");
    }
    @Override
    public void dispenseProduct(VendingMachine machine){

        machine.inventory.dispense(machine.selectedProduct);
        machine.balance -= machine.selectedProduct.price;

        System.out.println("Item Dispensed " + machine.selectedProduct.name);
        machine.selectedProduct = null;
        machine.setState(new RefundState());
    }
    @Override
    public void refund(VendingMachine machine){
        System.out.println("Please Wait Dispensing");
    }
}
