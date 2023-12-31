package com.BookingApp.Service;

import com.BookingApp.Data.Entity.User;
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
    public void saveAccomodation(Accommodation accommodation) {

        accommodationRepository.save(accommodation);
    }
    public Accommodation getAccommodationById(long id){

         return  accommodationRepository.getAccommodationById(id);
    }

    public boolean existsByEmail(String email){
        return accommodationRepository.existsByEmail(email);
    }

    public List<Accommodation> getAccommodationByHaveAvailableRooms (){
        return accommodationRepository.getAccommodationByHaveAvailableRooms(true);
    }

    public void saveAccommodation(Accommodation accommodation){
        if(accommodation == null) {
            System.err.println("Room is null");
            return;
        }
        accommodationRepository.save(accommodation);

    }

    public List<Accommodation> getAllAccommodationByCityOrCountryAndHaveAvailableRooms (String location){
        return accommodationRepository.getAllAccommodationByCityOrCountryAndHaveAvailableRooms(location, location, true);
    }

    public boolean existsByName(String name){
        return  accommodationRepository.existsByName(name);
    }

    public Accommodation getAccommodationbyEmail(String email){
        return accommodationRepository.getAccommodationByEmail(email);
    }


}
