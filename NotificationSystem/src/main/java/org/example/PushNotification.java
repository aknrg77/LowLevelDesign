package org.example;

public class PushNotification implements NotificationChannel{
    @Override
    public void send(String to, String message){
        System.out.println("Push Send " + to + " -> " + message);
    }
}
