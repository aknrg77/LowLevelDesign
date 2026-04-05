package org.example;

public interface KeyValueStore {
    void put(String key, Object value);
    Entry get(String key);
}
