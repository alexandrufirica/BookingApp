package com.BookingApp.Data.Repository;

import com.BookingApp.Data.Entity.Accommodation;
import com.BookingApp.Data.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

    Accommodation getAccommodationById(long id);

    Accommodation findByEmail(String email);

    Accommodation getAccommodationByActivationCode (String activationCode);

    Boolean existsByEmail(String email);

    Boolean existsByName(String name);

    List<Accommodation> getAccommodationByHaveAvailableRooms (boolean haveRooms);

    List<Accommodation> getAllAccommodationByCityOrCountryAndHaveAvailableRooms (String city, String country, boolean haveRooms);

    Accommodation getAccommodationByEmail(String email);
}
