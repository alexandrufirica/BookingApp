package com.BookingApp.Data.Repository;

import com.BookingApp.Data.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
//    @Query("select c from Room c " +
//            "where lower(c.roomType) like lower(concat('%', :searchTerm, '%')) " +
//            "and (c.accommodation) like (concat('%', :accommodationid, '%')) ")
    @Query("SELECT c FROM Room c where" +
            "(c.accommodation.id) = ( :accommodationidTerm ) and " +
            "lower(c.roomType) like lower(concat('%', :searchTerm, '%'))" )
    List<Room> search(@Param("searchTerm") String searchTerm, @Param("accommodationidTerm") Long accommodationIdTerm);

    List<Room> getRoomsByAccommodationId (Long id);

    Room getRoomById (long id);
}
