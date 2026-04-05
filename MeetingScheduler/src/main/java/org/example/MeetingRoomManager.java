package org.example;

import java.util.HashMap;
import java.util.List;

public class MeetingRoomManager {
    List<MeetingRoom> meetingRooms;
    HashMap<MeetingRoom, MeetingRoomCalendar> meetingRoomTime;

    void MeetingRoomCalendar(List<MeetingRoom> meetingRooms){
        this.meetingRooms = meetingRooms;

        this.meetingRoomTime = new HashMap<>();
    }


    public MeetingRoom getAvailableRoom(int capacity, int start, int end){
        for(var x: meetingRooms){
            if(x.capacity < capacity){
                continue;
            }

            if(meetingRoomTime.containsKey(x)){
                MeetingRoomCalendar time = meetingRoomTime.get(x);

                if(time.startDate > end || start > time.endDate){
                    return x;
                }
            }
        }
        return null;
    }

    public void book(MeetingRoom meetingRoom, List<User> users, int start, int end){
        MeetingRoomCalendar time = new MeetingRoomCalendar(start, end);
        meetingRoomTime.put(meetingRoom, time);
        System.out.println("Meeting Room " + meetingRoom.id + "Booked!!!");

    }
}
