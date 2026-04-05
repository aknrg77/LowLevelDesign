package org.example;

import java.util.*;

public class EqualSplit implements SplitStrategy {
    public void calculateSplit(List<Split> splits, double amount){
        int count = splits.size();
        double share = Math.round((amount / count) * 100.0) / 100.0;;

        for(int i = 0; i<count-1; i++){
            splits.get(i).amount = share;
        }

        double reminder = amount - share * (count - 1);
        reminder = (double) Math.round(reminder * 100) /100;
        splits.get(count-1).amount = reminder;
    }
}

