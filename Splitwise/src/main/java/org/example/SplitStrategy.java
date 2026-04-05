package org.example;

import java.util.List;

public interface SplitStrategy {
    void calculateSplit(List<Split> splits, double amount);
}
