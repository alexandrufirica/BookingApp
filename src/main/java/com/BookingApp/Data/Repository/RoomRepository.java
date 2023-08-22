package com.BookingApp.Data.Repository;

import com.BookingApp.Data.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("select r from Rooms r " +
            "where lower(r.roomType) like lower(concat('%', :searchTerm, '%')) " )
    List<Room> search(@Param("searchTerm") String searchTerm);
}
