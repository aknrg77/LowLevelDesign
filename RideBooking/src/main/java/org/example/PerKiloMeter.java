package org.example;

public class PerKiloMeter implements PricingStrategy{
    public double calculatePrice(Location source, Location dest) {
        return Math.ceil(source.distance(dest)*10);
    }
}
