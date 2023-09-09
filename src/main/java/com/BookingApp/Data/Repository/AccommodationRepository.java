package com.BookingApp.Data.Repository;

import com.BookingApp.Data.Entity.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

    Accommodation getAccommodationById(long id);
}
