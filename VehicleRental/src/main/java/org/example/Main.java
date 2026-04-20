package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(){
        RentalService service = new RentalService();
        Branch b1 = new Branch("1", "Bellandur");
        Branch b2 = new Branch("2", "HSR");

        Vehicle hatch1 = new HatchBack(VehicleType.HATCH_BACK, 100);
        Vehicle hatch2 = new HatchBack(VehicleType.HATCH_BACK, 80);
        Vehicle sedan1 = new Sedan(VehicleType.SEDAN, 500);

        b1.addVehicles(hatch1, 2);
        b1.addVehicles(sedan1, 1);
        b2.addVehicles(hatch2, 1);

        service.addBranch(b1);
        service.addBranch(b2);

        service.book(
                VehicleType.HATCH_BACK,
                Strategy.LOWEST_PRICE,
                "2026-03-24 10:00",
                "2026-03-24 12:00"
        );

        Booking booking1 = service.book(
                VehicleType.SEDAN,
                Strategy.LOWEST_PRICE,
                "2026-03-24 11:00",
                "2026-03-24 12:00"
        );

        service.book(
                VehicleType.HATCH_BACK,
                Strategy.LOWEST_PRICE,
                "2026-03-24 10:00",
                "2026-03-24 12:00"
        );

        service.book(
                VehicleType.HATCH_BACK,
                Strategy.LOWEST_PRICE,
                "2026-03-24 10:00",
                "2026-03-24 12:00"
        );

        service.book(
                VehicleType.HATCH_BACK,
                Strategy.LOWEST_PRICE,
                "2026-03-24 10:00",
                "2026-03-24 12:00"
        );

        service.endBooking(booking1);

        service.book(
                VehicleType.HATCH_BACK,
                Strategy.LOWEST_PRICE,
                "2026-03-24 10:00",
                "2026-03-24 12:00"
        );

        //this should say no vehicle 
        service.book(
                VehicleType.SEDAN,
                Strategy.LOWEST_PRICE,
                "2026-03-24 11:00",
                "2026-03-24 12:00"
        );

    }
}
