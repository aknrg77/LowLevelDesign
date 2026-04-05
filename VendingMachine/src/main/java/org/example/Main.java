package org.example;

import java.util.Collection;
import java.util.HashMap;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {

        Product product1 = new Product("1", "coke", 20);
        Product product2 = new Product("1", "coke", 20);
        Product product3 = new Product("2", "lays", 10);

        Inventory inventory = new Inventory();
        inventory.addProduct(product1);
        inventory.addProduct(product2);
        inventory.addProduct(product3);

        VendingMachine machine = new VendingMachine(inventory);

        Coin ten = Coin.TEN;
        Coin five = Coin.FIVE;

        machine.insertCoin(ten);
        machine.insertCoin(ten);
        machine.insertCoin(five);
        machine.selectProduct(product1);
        machine.dispense();
        machine.refund();


    }
}
