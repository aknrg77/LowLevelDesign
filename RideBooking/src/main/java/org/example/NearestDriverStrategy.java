package org.example;

import java.util.List;

public class NearestDriverStrategy implements  FindDriverStrategy{
    public Driver findDriver(Location source, List<Driver> driverList, double maxDist){
            Driver bestDriver = null;
            double dist = Double.MAX_VALUE;
            for(var x: driverList){
                if(!x.is_available) continue;
                double new_dist = source.distance(x.location);
                if(dist > new_dist && new_dist <= maxDist){
                    dist = new_dist;
                    bestDriver = x;
                }
            }
            return bestDriver;
    }
}
