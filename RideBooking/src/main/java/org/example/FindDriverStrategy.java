package org.example;

import java.util.List;

public interface FindDriverStrategy {
    public Driver findDriver(Location source, List<Driver> driverList, double maxDist);
}
