package com.BookingApp.Data.Repository;

import com.BookingApp.Data.Entity.Accommodation;
import com.BookingApp.Data.Entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository <Reservation, Long> {

    Reservation getReservationById (long id);
    Optional <Reservation> findByReservationName (String name);
    Reservation getAllByAccommodation(Accommodation accommodation);
    Boolean existsByReservationName(String name);
}
