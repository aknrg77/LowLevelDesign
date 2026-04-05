package org.example;

import java.util.List;

public class MeetingRoom {
    String id;
    int capacity;
    List<User> users;

    public MeetingRoom(String id, int capacity) {
        this.id = id;
        this.capacity = capacity;
    }
}
