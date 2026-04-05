package org.example;
import java.util.*;

public class ExactSplit implements SplitStrategy{
    public void calculateSplit(List<Split> splits, double amount){
        double sum = 0;
        for(Split x: splits){
            sum+=x.amount;
        }
        if(sum != amount){
            throw new RuntimeException("Sum is not Equal");
        }
    }
}
