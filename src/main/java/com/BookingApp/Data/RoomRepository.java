package com.BookingApp.Data;

import com.BookingApp.Views.Manager.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
