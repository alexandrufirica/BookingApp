package com.BookingApp.Service;

import com.BookingApp.Data.Entity.Reservation;

import java.util.List;

public interface IReservationService {

    List<Reservation> getAllReservations();

    void createReservation(Reservation reservation);

}
