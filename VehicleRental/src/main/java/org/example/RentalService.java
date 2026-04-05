package org.example;

import java.util.*;

public class RentalService {
    List<Branch> branches;
    Map<Strategy, BookingStrategy> strategies;

    public RentalService(){
        branches = new ArrayList<>();
        strategies = new HashMap<>();

        strategies.put(Strategy.LOWEST_PRICE, new LowestPriceStrategy());
    }

    void addBranch(Branch branch){
        branches.add(branch);
    }

    public synchronized Booking book(VehicleType type, Strategy strategy, String start, String end){
        BookingStrategy strat = strategies.get(strategy);
        Branch selected = strat.select(branches, type, start, end);
        if(selected == null){
            System.out.println("No vehicle available");
            return null;
        }
        Booking booking = selected.addBooking(type, start, end, selected);

        System.out.println("Booked at " + selected.getName() + " with price: " + selected.price.get(type));
        return booking;
    }

    public synchronized void endBooking(Booking booking) {
        if (booking == null || booking.branch == null) {
            return;
        }
        booking.branch.endBooking(booking);
    }
}
