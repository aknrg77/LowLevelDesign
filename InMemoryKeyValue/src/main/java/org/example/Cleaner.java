package org.example;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

public class Cleaner extends Thread {
    public final ConcurrentHashMap<String, Entry> keyStore;

    Cleaner(ConcurrentHashMap<String, Entry> keyStore){
        this.keyStore = keyStore;
    }

    @Override
    public void run() {
        while(true){
            for(var x: keyStore.entrySet()){
                if(checkExpiry(x.getValue())){
                    keyStore.remove(x.getKey());
                }
            }

            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }

    private boolean checkExpiry(Entry entry){
        if (entry != null){
                return System.currentTimeMillis() > entry.expiryTime;
        }
        return false;
    }
}
