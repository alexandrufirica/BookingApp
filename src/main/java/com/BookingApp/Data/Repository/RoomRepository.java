package com.BookingApp.Data.Repository;

import com.BookingApp.Data.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
