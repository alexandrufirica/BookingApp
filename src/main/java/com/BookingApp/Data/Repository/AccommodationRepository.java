package com.BookingApp.Data.Repository;

import com.BookingApp.Data.Entity.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

    Accommodation getAccommodationById(long id);

    Optional<Accommodation> findByEmail(String email);

    Accommodation getAccommodationByActivationCode (String activationCode);

    Boolean existsByEmail(String email);

    List<Accommodation> getAccommodationByHaveAvailableRooms (boolean haveRooms);


}
