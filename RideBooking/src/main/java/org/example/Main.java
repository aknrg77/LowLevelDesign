package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        RideBookingService service = new RideBookingService();

        service.addRider("r1");

        service.addDriver("d1", 0, 0);
        service.addDriver("d2", 5, 5);

        Ride ride = service.book(
                "r1",
                new Location(1, 1),
                new Location(10, 10)
        );

        System.out.println("Ride booked with driver: " + ride.driver.id);
        System.out.println("Fare: " + ride.price);

        service.endRide(ride.id);

        System.out.println("Ride completed");

        System.out.println("Ride history size: " +
                service.getRideHistory("r1").size());
    }
}
