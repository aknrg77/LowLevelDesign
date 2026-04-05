package org.example;

public class Main {
    public static void main(String[] args) {
        User user = new User("Anurag", "a@gmail.com", "aknrg77", "7905172341", NotificationType.EMAIL);
        NotificationService service = new NotificationService();
        service.notify(user, "hello World");
    }
}

