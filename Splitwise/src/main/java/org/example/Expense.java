package org.example;

import java.util.List;

public class Expense {
    double amount;
    User paidBy;
    List<Split> splits;
    SplitType type;
    Expense(SplitType type, User paidBy, List<Split> splits, double amount){
        this.type = type;
        this.paidBy = paidBy;
        this.splits = splits;
        this.amount = amount;
    }
}
