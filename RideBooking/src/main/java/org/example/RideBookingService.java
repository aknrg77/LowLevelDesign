package org.example;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class RideBookingService {
    ConcurrentHashMap<String, Rider> riders;
    ConcurrentHashMap<String, Driver> drivers;
    ConcurrentHashMap<String, Ride> rides;
    FindDriverStrategy nearestDriverStrategy;
    PricingStrategy pricingStrategy;
    double MaxDist = 50.00;

    public RideBookingService() {
        this.riders = new ConcurrentHashMap<>();
        this.pricingStrategy = new PerKiloMeter();
        this.nearestDriverStrategy = new NearestDriverStrategy();
        this.rides = new ConcurrentHashMap<>();
        this.drivers = new ConcurrentHashMap<>();
    }

    public void addRider(String id){
        riders.put(id, new Rider(id));
    }

    public void addDriver(String id, int x, int y){
        drivers.put(id, new Driver(id, new Location(x, y)));
    }

    public Ride book(String riderId, Location source, Location dest){
        Rider rider = riders.get(riderId);
        if(rider == null){
            throw new RuntimeException("Rider not Found");
        }

        //Find Driver
        Driver driver = nearestDriverStrategy.findDriver(source, new ArrayList<>(drivers.values()), MaxDist);
        if(driver == null){
            throw new RuntimeException("No driver Found at this moment");
        }

        double fare = pricingStrategy.calculatePrice(source, dest);
        String rideId = UUID.randomUUID().toString();
        Ride ride = new Ride(rideId, rider, driver, source, dest, fare);
        ride.rideStatus = RideStatus.ONGOING;
        driver.is_available = false;
        rider.ridehistory.add(ride);
        rides.put(rideId, ride);
        return ride;
    }

    public void endRide(String rideId){
        Ride currentRide = rides.get(rideId);
        if(currentRide == null) return;

        currentRide.rideStatus = RideStatus.COMPLETED;
        currentRide.driver.is_available = true;
    }

    public List<Ride> getRideHistory(String riderId) {
        return riders.get(riderId).ridehistory;
    }

}
