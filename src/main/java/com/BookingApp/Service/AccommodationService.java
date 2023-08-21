package com.BookingApp.Service;

import com.BookingApp.Data.Repository.AccommodationRepository;
import com.BookingApp.Data.Entity.Accommodation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccommodationService implements  IAccommodationService{

    private final AccommodationRepository accommodationRepository;

    public AccommodationService (AccommodationRepository accommodationRepository){
        this.accommodationRepository = accommodationRepository;
    }

    @Override
    public List<Accommodation> getAllAccommodations() {

        return accommodationRepository.findAll();
    }

    @Override
    public void createAccomodation(Accommodation accommodation) {

        accommodationRepository.save(accommodation);
    }
}
