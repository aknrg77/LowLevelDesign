package org.example;
import java.util.*;

public class Rider {
    String id;
    List<Ride> ridehistory;

    public Rider(String id) {
        this.id = id;
        this.ridehistory = new ArrayList<>();
    }
}
