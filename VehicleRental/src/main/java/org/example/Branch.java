package org.example;
import java.awt.print.Book;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Branch {
    private String id;
    private String name;
    public ConcurrentHashMap<VehicleType, Integer> inventory;
    public Map<VehicleType, Integer> price;
    public Map<VehicleType, List<Booking>> bookings;
    AtomicInteger bookingId = new AtomicInteger(0);

    public Branch(String id, String name) {
        this.id = id;
        this.name = name;
        this.inventory = new ConcurrentHashMap<>();
        this.price = new HashMap<>();
        this.bookings = new HashMap<>();
    }

    public void addVehicles(Vehicle vehicle, int count) {
        inventory.merge(vehicle.type, count, Integer::sum);
        price.putIfAbsent(vehicle.type, vehicle.price);
        bookings.putIfAbsent(vehicle.type, new ArrayList<>());
    }

    public Booking addBooking(VehicleType type, String start, String end, Branch branch){
        bookings.putIfAbsent(type, new ArrayList<>());
        int id = bookingId.incrementAndGet();
        Booking booking = new Booking(id, type, start, end, branch);
        bookings.get(type).add(booking);
        inventory.merge(type, -1, Integer::sum);
        return booking;
    }

    public void endBooking(Booking booking){
        if(!bookings.containsKey(booking.type)){
            System.out.println("Booking not Found");
            return;
        }
        List<Booking> bookingList = bookings.getOrDefault(booking.type, new ArrayList<>());
        Iterator<Booking> it = bookingList.iterator();
        while (it.hasNext()) {
            Booking b = it.next();
            if (b.id == booking.id) {
                inventory.merge(b.type, 1, Integer::sum);
                it.remove();
                break;
            }
        }
    }

    public boolean isAvailable(VehicleType type, String start, String end){
        List<Booking> list = bookings.getOrDefault(type, new ArrayList<>());

        int active = 0;

        for(var b : list){
            if(overlap(b.start, b.end, start, end)){
                active++;
            }
        }

        return active < inventory.getOrDefault(type, 0);
    }

    public String getId() {
        return id;
    }

    public String getName(){
        return this.name;
    }

    private boolean overlap(String s1, String e1, String s2, String e2){
        return !(e1.compareTo(s2) <= 0 || e2.compareTo(s1) <= 0);
    }
}
