package com.BookingApp.Views.User;

import com.BookingApp.Data.Entity.Accommodation;
import com.BookingApp.Data.Entity.Reservation;
import com.BookingApp.Data.Entity.Room;
import com.BookingApp.Data.Repository.AccommodationRepository;
import com.BookingApp.Data.Repository.RoomRepository;
import com.BookingApp.Service.ReservationService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@PageTitle("Reservation")
@Route("/reservation")
@RolesAllowed({"USER","ADMIN"})

public class ReservationView extends VerticalLayout {

    UserNavBar userNavBar = new UserNavBar();
    private final Reservation reservation;
    private final Accommodation accommodation;
    private final Room room;
    private final AccommodationRepository accommodationRepository;
    private final RoomRepository roomRepository;
    private final ReservationService reservationService;

    public ReservationView (Reservation reservation, AccommodationRepository accommodationRepository, RoomRepository roomRepository, ReservationService reservationService){
        this.reservation = reservation;
        this.accommodationRepository = accommodationRepository;
        this.roomRepository = roomRepository;
        this.reservationService = reservationService;

        this.accommodation = accommodationRepository.getAccommodationById(HomeView.accommodationId);
        this.room = roomRepository.getRoomById(AccommodationView.roomId);
        addClassName("reservation-view");
        add(
                userNavBar,
                getComponent()
        );

        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    }

    private Component getComponent() {
        H1 label = new H1(room.getRoomType() + " Room");

        DatePicker.DatePickerI18n singleFormat = new DatePicker.DatePickerI18n();
        singleFormat.setDateFormat("dd-MM-yyyy");

        DatePicker checkinPicker = new DatePicker("Check-in:");
        checkinPicker.setI18n(singleFormat);
        checkinPicker.setRequiredIndicatorVisible(true);

        DatePicker checkoutPicker = new DatePicker("Check-out:");
        checkoutPicker.setI18n(singleFormat);
        checkoutPicker.setRequiredIndicatorVisible(true);

        TextField reservationName = new TextField("Reservation name");
        reservationName.setRequiredIndicatorVisible(true);

        Button reserveButton = new Button("Reserve");
        reserveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        reserveButton.addClickListener( e -> {
            reservation.setCheckIn(checkinPicker.getValue());
            reservation.setCheckOut(checkoutPicker.getValue());
            reservation.setReservationName(reservationName.getValue());
            reservation.setAccommodation(accommodation);
            reservation.setRoomReserved(room.getRoomType());
            createReservation(reservation);
            Notification.show("Reservation created");
        });


        HorizontalLayout pickersLayout = new HorizontalLayout();
        pickersLayout.add(checkinPicker, checkoutPicker);
        pickersLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);

       VerticalLayout verticalLayout = new VerticalLayout();
       verticalLayout.add(label,pickersLayout, reservationName, reserveButton);
       verticalLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

       return verticalLayout;
    }

    public void createReservation (Reservation reservation){
        reservationService.createReservation(reservation);
    }
}
