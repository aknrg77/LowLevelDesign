package org.example;

public class Entry {
    Object value;
    long expiryTime;

    Entry(Object value, long expiryTime){
        this.expiryTime = expiryTime;
        this.value = value;
    }
}
