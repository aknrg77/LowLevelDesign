package org.example;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        MeetingScheduler meetingScheduler = new MeetingScheduler();
        meetingScheduler.addMeetingRoom("1", 5);
        meetingScheduler.addMeetingRoom("2", 3);
        meetingScheduler.addMeetingRoom("3", 6);
        meetingScheduler.addMeetingRoom("4", 3);


        List<User> users = new ArrayList<>();
        for(int i = 0; i<5; i++){
            users.add(new User(UUID.randomUUID().toString()));
        }

        meetingScheduler.bookRoom(users, 1, 3, 5);
        meetingScheduler.bookRoom(users, 2, 4, 3);
    }
}
