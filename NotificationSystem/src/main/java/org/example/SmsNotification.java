package org.example;

public class SmsNotification implements NotificationChannel{
    @Override
    public void send(String to, String message){
        System.out.println("SMS Send " + to + " -> " + message);
    }
}
