package org.example;
import java.util.*;
public class SplitService {
    public Map<User, Map<User, Double>> balance;
    public Map<SplitType, SplitStrategy> strategies;
    public Map<String, User> users;
    public Map<String, Expense> expenses;
    SplitService(){
        users = new HashMap<>();
        strategies = new HashMap<>();
        strategies.put(SplitType.Equal, new EqualSplit());
        strategies.put(SplitType.Exact, new ExactSplit());
        balance = new HashMap<>();
    }


    Expense addExpense(User paidBy, double amount, SplitType type, List<Split> splits){
        //if user does not exist
        if(users.containsKey(paidBy.name)){
            throw new Error("User Not Found");
        }


        //get SplitType
        strategies.get(type).calculateSplit(splits, amount);

        //update balance sheet
        updateBalanceSheet(paidBy, splits);


        //return Expense
        return new Expense(type, paidBy, splits, amount);
    }

    private void updateBalanceSheet(User paidBy, List<Split> splits){
        for(Split x: splits){
            if(paidBy == x.user) continue;
            balance.putIfAbsent(paidBy, new HashMap<>());
            balance.putIfAbsent(x.user, new HashMap<>());

            balance.get(paidBy).put(x.user, balance.get(paidBy).getOrDefault(x.user, 0.0) - x.amount);
            balance.get(x.user).put(paidBy, balance.get(x.user).getOrDefault(paidBy, 0.0) + x.amount);
        }
    }

    public  void getBalanceSheet(){
        for(User u1: balance.keySet()){
            for(User u2: balance.get(u1).keySet()){
                double amt = balance.get(u1).get(u2);
                if (amt > 0) {
                    System.out.println(u1.name + " owes " + u2.name + ": " + amt);
                }
            }
        }


    }


}
