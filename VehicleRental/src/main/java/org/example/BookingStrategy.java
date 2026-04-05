package org.example;

import java.util.List;

public interface BookingStrategy {
    Branch select(List<Branch> branches, VehicleType type, String start, String end);
}