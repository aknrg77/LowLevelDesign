package org.example;

import java.util.concurrent.ConcurrentHashMap;

public class InMemoryKeyValueStore implements KeyValueStore {
    public final ConcurrentHashMap<String, Entry> map;

    InMemoryKeyValueStore(){
        map = new ConcurrentHashMap<>();
        Cleaner cleaner = new Cleaner(map);
        cleaner.start();

    }

    @Override
    public void put(String key, Object value){
        long expiryTime = System.currentTimeMillis() + 100000;
        map.put(key, new Entry(value, expiryTime));
    }


    @Override
    public Entry get(String key) {
        Entry entry = map.get(key);

        if (entry == null) {
            System.out.println("Value Expired!!!");
            return null;
        }

        if (checkExpiry(entry)) {
            map.remove(key);
            System.out.println("Value Expired!!!");
            return null;
        }

        return entry;
    }


    private boolean checkExpiry(Entry entry){
        if (entry != null){
            return System.currentTimeMillis() > entry.expiryTime;
        }
        return false;
    }
}
