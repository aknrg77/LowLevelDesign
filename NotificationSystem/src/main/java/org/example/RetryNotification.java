package org.example;

public class RetryNotification implements NotificationChannel{
    NotificationChannel channel;
    int maxRetries;

    RetryNotification(NotificationChannel channel, int maxRetries){
        this.channel = channel;
        this.maxRetries = maxRetries;
    }

    @Override
    public void send(String to, String message){
        int attempts = 0;
        while(attempts < maxRetries){
            try{
                channel.send(to, message);
                System.out.println("Success after " + (attempts + 1) + " attempt(s)");
                return;
            }catch (Exception e){
                attempts++;
                System.out.println("Retry " + attempts + " failed: " + e.getMessage());
                try {
                    long delay = (long) Math.pow(2, attempts) * 500;
                    Thread.sleep(delay);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        throw new RuntimeException("Failed after " + maxRetries + " retries");
    }
}
