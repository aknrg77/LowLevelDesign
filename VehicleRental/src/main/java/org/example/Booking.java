package org.example;

public class Booking {
    final int id;
    VehicleType type;
    String start;
    String end;
    Branch branch;

    public Booking(int id, VehicleType type, String start, String end, Branch branch) {
        this.id = id;
        this.type = type;
        this.start = start;
        this.end = end;
        this.branch = branch;
    }
}
