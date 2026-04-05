package org.example;
import java.util.List;
public class LowestPriceStrategy implements BookingStrategy {

    @Override
    public Branch select(List<Branch> branches, VehicleType type, String start, String end) {
        Branch ans = null;
        int minPrice = Integer.MAX_VALUE;
        for(Branch b : branches){
            if(b.price.containsKey(type) && b.isAvailable(type, start, end)){
                int p = b.price.get(type);

                if(p < minPrice){
                    minPrice = p;
                    ans = b;
                }
            }
        }
        return ans;
    }
}