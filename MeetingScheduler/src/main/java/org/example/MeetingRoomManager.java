package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MeetingRoomManager {
    List<MeetingRoom> meetingRooms;
    HashMap<MeetingRoom, MeetingRoomCalendar> meetingRoomTime;

    MeetingRoomManager(){
        meetingRooms = new ArrayList<>();
        meetingRoomTime = new HashMap<>();
    }

    public void addRoom(String id, int capacity){
        this.meetingRooms.add(new MeetingRoom(id, capacity));
    }

    public MeetingRoom getAvailableRoom(int capacity, int start, int end){
        for(var x: meetingRooms){
            if(capacity > x.capacity){
                continue;
            }

            if(meetingRoomTime.containsKey(x)){
                MeetingRoomCalendar time = meetingRoomTime.get(x);

                if(time.startDate > end || start > time.endDate){
                    return x;
                }
            }else{
                return x;
            }
        }
        return null;
    }

    public void book(MeetingRoom meetingRoom, List<User> users, int start, int end){
        MeetingRoomCalendar time = new MeetingRoomCalendar(start, end);
        meetingRoomTime.put(meetingRoom, time);
        System.out.println("Meeting Room " + meetingRoom.id + " Booked!!!");

    }
}
