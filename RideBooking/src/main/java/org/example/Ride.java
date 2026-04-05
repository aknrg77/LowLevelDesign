package org.example;

public class Ride {
    String id;
    Rider rider;
    Driver driver;
    Location source;
    Location destination;
    double price;
    RideStatus rideStatus;

    public Ride(String id, Rider rider, Driver driver, Location source, Location destination, double price) {
        this.id = id;
        this.rider = rider;
        this.driver = driver;
        this.source = source;
        this.destination = destination;
        this.price = price;
        this.rideStatus = RideStatus.CREATED_AT;
    }
}
