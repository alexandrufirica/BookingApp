package com.BookingApp.Service;

import com.BookingApp.Data.Entity.Status;
import com.BookingApp.Data.Repository.RoomRepository;
import com.BookingApp.Data.Entity.Room;
import com.BookingApp.Data.Repository.StatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService implements  IRoomService{

    private final RoomRepository roomRepository;
    private final StatusRepository statusRepository;

    public RoomService (RoomRepository roomRepository, StatusRepository statusRepository){
        this.roomRepository =roomRepository;
        this.statusRepository = statusRepository;
    }

    public List<Room> findAllRoom(String filterText){
        if(filterText == null || filterText.isEmpty()){
            return roomRepository.findAll();
        }else{
            return roomRepository.search(filterText);
        }

    }

    public long countRooms(){
        return roomRepository.count();
    }

    public void deleteRoom(Room room){
        roomRepository.delete(room);
    }

    public void saveRoom(Room room){
        if(room == null){
            System.err.println("Room is null");
            return;
        }

        roomRepository.save(room);
    }

    public List<Status> findAllStatuses(){
        return statusRepository.findAll();
    }


    @Override
    public void createRoom(Room room) {
        roomRepository.save(room);
    }
}

