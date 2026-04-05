package org.example;

public class EmailNotification implements NotificationChannel {
    @Override
    public void send(String to, String message){
        System.out.println("Email Send " + to + " -> " + message);
    }
}
