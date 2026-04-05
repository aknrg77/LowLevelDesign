package org.example;

import java.util.*;

public class Main {
    static void main() {

        SplitService splitService = new SplitService();


        User u1 = new User("A");
        User u2 = new User("B");
        User u3 = new User("C");

        splitService.users.putIfAbsent("u1", u1);
        splitService.users.putIfAbsent("u2", u2);
        splitService.users.putIfAbsent("u3", u3);

        List<Split> splits = Arrays.asList(
            new Split(u1, 50),
            new Split(u2, 100),
            new Split(u3, 150)
        );

//        splitService.addExpense(u1, 300, SplitType.Exact, splits);

        splits = Arrays.asList(
                new Split(u1, 0),
                new Split(u2, 0),
                new Split(u3, 0)
        );

        splitService.addExpense(u1, 100, SplitType.Equal, splits);

        splitService.getBalanceSheet();

    }
}
