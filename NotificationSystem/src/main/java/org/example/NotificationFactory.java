package org.example;

public class NotificationFactory {

    NotificationChannel getChannel(NotificationType type){
        switch (type){
            case NotificationType.EMAIL:
                return new EmailNotification();
            case NotificationType.PUSH:
                return new PushNotification();
            case NotificationType.SMS:
                return new SmsNotification();
            default:
                throw new IllegalArgumentException();
        }
    }
}
