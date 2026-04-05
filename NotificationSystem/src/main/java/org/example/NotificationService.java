package org.example;

public class NotificationService {

    public void notify(User user, String message){
        NotificationChannel channel = new NotificationFactory().getChannel(user.type);

        RetryNotification newChannel = new RetryNotification(channel, 3);

        String to = getReciever(user);
        newChannel.send(to, message);
    }

    private String getReciever(User user){
        switch (user.type){
            case NotificationType.EMAIL:
                return user.email;
            case NotificationType.PUSH:
                return user.userName;
            case NotificationType.SMS:
                return user.mobileNumber;
            default:
                throw new IllegalArgumentException();
        }
    }

}
