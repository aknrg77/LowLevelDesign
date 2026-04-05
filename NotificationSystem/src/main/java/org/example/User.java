package org.example;

public class User {
    String name;
    String email;
    String userName;
    String mobileNumber;
    NotificationType type;

    public User(String name, String email, String userName, String mobileNumber, NotificationType type) {
        this.name = name;
        this.email = email;
        this.userName = userName;
        this.mobileNumber = mobileNumber;
        this.type = type;
    }
}
