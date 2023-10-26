package com.BookingApp.Service;

import com.BookingApp.Data.Entity.Reservation;
import com.BookingApp.Data.Entity.Room;
import com.BookingApp.Views.User.AccommodationView;
import com.BookingApp.Views.User.HomeView;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvailableRooms {

    public static final Long STATUS_AVAILABLE = 1L;

    private final RoomService roomService;
    private final ReservationService reservationService;
    private Room room;

    private int roomsRemain;
    public AvailableRooms(RoomService roomService, ReservationService reservationService){
        this.roomService = roomService;
        this.reservationService = reservationService;

        room = roomService.getRoomById(AccommodationView.roomId);


     }

     public void remainRooms(){
         roomsRemain =room.getNumberOfRooms();

         List<Reservation> reservations = reservationService.findReservationByAccommodation(HomeView.accommodationId);
         if(reservations.contains(room.getRoomType())){
             roomsRemain--;
         }
     }
}
