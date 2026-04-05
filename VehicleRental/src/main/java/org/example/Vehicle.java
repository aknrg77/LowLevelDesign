package org.example;

public abstract class Vehicle {
    public VehicleType type;
    public int price;
    Vehicle(VehicleType type, int price){
        this.type = type;
        this.price = price;
    }
}
