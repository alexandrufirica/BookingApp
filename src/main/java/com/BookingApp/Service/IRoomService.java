package com.BookingApp.Service;

import com.BookingApp.Data.Entity.Room;

import java.util.List;

public interface IRoomService {
     List<Room> findAllRoom(String filterText);

     void createRoom(Room room);
}
