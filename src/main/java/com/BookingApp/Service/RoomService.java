package com.BookingApp.Service;

import com.BookingApp.Data.Repository.RoomRepository;
import com.BookingApp.Data.Entity.Room;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService implements  IRoomService{

    private final RoomRepository roomRepository;

    public RoomService (RoomRepository roomRepository){
        this.roomRepository =roomRepository;
    }


    @Override
    public List<Room> getAllRoom(){
        return roomRepository.findAll();
    }

    @Override
    public void createRoom(Room room) {
        roomRepository.save(room);
    }
}

