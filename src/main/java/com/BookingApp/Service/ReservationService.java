package com.BookingApp.Service;

import com.BookingApp.Data.Entity.Reservation;
import com.BookingApp.Data.Repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public class ReservationService implements IReservationService{

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository){
        this.reservationRepository = reservationRepository;
    }
    @Override
    public List<Reservation> getAllReservations() {

        return reservationRepository.findAll();
    }

    @Override
    public void createReservation(Reservation reservation) {

        reservationRepository.save(reservation);
    }

    public List<Reservation> findReservationByAccommodation(Long accommodationId){
        return reservationRepository.getReservationByAccommodationId(accommodationId);
    }

    public boolean existsByReservationName(String roomName){
        return reservationRepository.existsByReservationName(roomName);
    }

    public boolean existsByRoomId(long id){
        return reservationRepository.existsByRoomId(id);
    }

    public List<Reservation> getAllReservationsByRoomId(long id){
        return reservationRepository.getAllByRoomId(id);
    }


}
