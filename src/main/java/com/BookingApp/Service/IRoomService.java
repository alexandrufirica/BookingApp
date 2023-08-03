package com.BookingApp.Service;

import com.BookingApp.Views.Manager.Room;

import java.util.List;

public interface IRoomService {
     List<Room> getRoom();

     void createRoom(Room room);
}
