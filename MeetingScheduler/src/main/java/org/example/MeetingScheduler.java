package org.example;

import java.util.List;

public class MeetingScheduler {
    MeetingRoomManager meetingRoomManager;


    public void bookRoom(List<User> users, int start, int end, int capacity){

        MeetingRoom room = meetingRoomManager.getAvailableRoom(capacity, start, end);
        if(room == null){
            System.out.println("Meeting Room Not Available");
            return;
        }

        meetingRoomManager.book(room, users, start, end);

    }


}
