package com.BookingApp.Views.User;

import com.BookingApp.Data.Entity.Accommodation;
import com.BookingApp.Data.Entity.Reservation;
import com.BookingApp.Data.Entity.Room;
import com.BookingApp.Data.Repository.AccommodationRepository;
import com.BookingApp.Data.Repository.RoomRepository;
import com.BookingApp.Service.AccommodationService;
import com.BookingApp.Service.ReservationService;
import com.BookingApp.Service.RoomService;
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
    private final AccommodationService accommodationService;
    private final RoomService roomService;
    private final ReservationService reservationService;

    public ReservationView (Reservation reservation, AccommodationService accommodationService, RoomService roomService, ReservationService reservationService){
        this.reservation = reservation;
        this.accommodationService = accommodationService;
        this.roomService = roomService;
        this.reservationService = reservationService;

        this.accommodation = accommodationService.getAccommodationById(HomeView.accommodationId);
        this.room = roomService.getRoomById(AccommodationView.roomId);
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
            if(checkoutPicker.getValue().isBefore(checkinPicker.getValue()) ){
                Notification.show("Check-out date can't be before Check-in date");
            } else if (checkoutPicker.getValue().equals(checkinPicker.getValue())) {
                Notification.show("Check-out date can't be in the same as Check-in date");
            } else if (reservationName.isEmpty() || reservationName == null) {
                Notification.show("Reservation needs a name");
            } else{
                createReservation(reservation);
                Notification.show("Reservation created");
            }

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
