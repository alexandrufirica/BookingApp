package com.BookingApp.Service;

import com.BookingApp.Data.Room;

import java.util.List;

public interface IRoomService {
     List<Room> getAllRoom();

     void createRoom(Room room);
}
