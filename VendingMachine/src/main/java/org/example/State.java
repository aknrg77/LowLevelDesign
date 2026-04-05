package org.example;

public interface State {
    void insertCoin(VendingMachine machine, Coin coin);
    void selectProduct(VendingMachine machine, Product product);
    void dispenseProduct(VendingMachine machine);
    void refund(VendingMachine machine);
}
