package com.BookingApp.Data.Repository;

import com.BookingApp.Data.Entity.Accommodation;
import com.BookingApp.Data.Entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository <Reservation, Long> {

    Reservation getReservationById (long id);
    Optional <Reservation> findByReservationName (String name);
    Reservation getAllByAccommodation(Accommodation accommodation);
    Boolean existsByReservationName(String name);
    List<Reservation> getReservationByAccommodationId (Long id);
    Boolean existsByRoomId(long id);
    List<Reservation> getAllByRoomId(long id);


}
