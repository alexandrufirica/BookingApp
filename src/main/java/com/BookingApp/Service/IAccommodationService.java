package com.BookingApp.Service;

import com.BookingApp.Data.Accommodation;

import java.util.List;

public interface IAccommodationService {

    List<Accommodation> getAllAccommodations();

    void createAccomodation (Accommodation accommodation);
}
