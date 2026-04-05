package org.example;

public class VendingMachine {
    Inventory inventory;
    State state;
    int balance;
    Product selectedProduct;

    public VendingMachine(Inventory inventory) {
        this.inventory = inventory;
        this.state = new IdleState();
    }

    public void insertCoin(Coin coin){
        state.insertCoin(this, coin);
    }
    public void selectProduct(Product product){
        state.selectProduct(this, product);
    }

    public void dispense(){
        state.dispenseProduct(this);
    }

    public void refund(){
        state.refund(this);
    }

    public void setState(State state){
        this.state = state;
    }
    public void setSelectedProduct(Product product){
        this.selectedProduct = product;
    }

    public Product getSelectedProduct(){
        return this.selectedProduct;
    }
}
