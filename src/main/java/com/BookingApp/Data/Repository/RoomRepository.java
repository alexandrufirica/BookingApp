package com.BookingApp.Data.Repository;

import com.BookingApp.Data.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT c FROM Room c where" +
            "(c.accommodation.id) = ( :accommodationidTerm ) and " +
            "lower(c.roomType) like lower(concat('%', :searchTerm, '%'))" )
    List<Room> search(@Param("searchTerm") String searchTerm, @Param("accommodationidTerm") Long accommodationIdTerm);

//    @Query("SELECT c FROM Room c where" +
//            "(c.checkIn) = ( :checkInTerm ) and " +
//            "(c.checkOut) = ( :checkOutTerm) and " +
//            "(c.accommodatio.id) = ( :accommodationidTerm )" )
//    List<Room> search(@Param("checkInTerm") LocalDate checkIn, @Param("checkOut") LocalDate checkOutTerm, @Param("accommodationidTerm") Long accommodationIdTerm);
//
    List<Room> getRoomsByAccommodationId (Long id);

    Room getRoomById (long id);
}
