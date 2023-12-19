package com.BookingApp.Views.User;

import com.BookingApp.Data.Entity.Accommodation;
import com.BookingApp.Data.Entity.Reservation;
import com.BookingApp.Data.Entity.Room;
import com.BookingApp.Data.Entity.User;
import com.BookingApp.Security.CustomUserDetailsService;
import com.BookingApp.Service.AccommodationService;
import com.BookingApp.Service.ReservationService;
import com.BookingApp.Service.RoomService;
import com.BookingApp.Service.UserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;

@PageTitle("Reservation")
@Route("/reservation")
@RolesAllowed({"USER","ADMIN"})
public class ReservationView extends VerticalLayout {

    UserNavBar userNavBar = new UserNavBar();
    private final Reservation reservation = new Reservation();
    private final Accommodation accommodation;
    private final Room room;
    public static LocalDate dateIn;
    public static LocalDate dateOut;
    private final AccommodationService accommodationService;
    private final RoomService roomService;
    private final ReservationService reservationService;
    private final User user;
    private final UserService userService;
    private String userEmail;


    public ReservationView (AccommodationService accommodationService,
                            RoomService roomService,
                            ReservationService reservationService,
                            UserService userService)
    {
        this.accommodationService = accommodationService;
        this.roomService = roomService;
        this.reservationService = reservationService;
        this.userService = userService;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Retrieve user and accommodation details from userDetails
            userEmail = userDetails.getUsername();

        }
        user = userService.getUserbyEmail(userEmail);
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
        checkinPicker.setReadOnly(true);

        DatePicker checkoutPicker = new DatePicker("Check-out:");
        checkoutPicker.setI18n(singleFormat);
        checkoutPicker.setReadOnly(true);

        dateIn = AccommodationView.dateIn;
        dateOut = AccommodationView.dateOut;

        checkinPicker.setValue(AccommodationView.dateIn);
        checkoutPicker.setValue(AccommodationView.dateOut);

        TextField reservationName = new TextField("Reservation name");
        reservationName.setRequiredIndicatorVisible(true);
        reservationName.setValue(user.getGivenName() + " " +user.getSurName());

        Button reserveButton = new Button("Reserve");
        reserveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        reserveButton.addClickListener( e -> {
            reservation.setCheckIn(dateIn);
            reservation.setCheckOut(dateOut);
            reservation.setReservationName(reservationName.getValue());
            reservation.setAccommodation(accommodation);
            reservation.setRoomReserved(room.getRoomType());
            reservation.setRoom(room);
            if(checkoutPicker.getValue().isBefore(checkinPicker.getValue()) ){
                Notification.show("Check-out date can't be before Check-in date");
            } else if (checkoutPicker.getValue().equals(checkinPicker.getValue())) {
                Notification.show("Check-out date can't be in the same as Check-in date");
            } else if (reservationName.isEmpty()) {
                Notification.show("Reservation needs a name");
            } else{
                createReservation(reservation);
                Notification.show("Reservation created");
            }

        });


        FormLayout pickersLayout = new FormLayout();
        pickersLayout.add(checkinPicker, checkoutPicker,reservationName);
        pickersLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px",2)
        );
        pickersLayout.setColspan(reservationName,2);



       VerticalLayout verticalLayout = new VerticalLayout();
       verticalLayout.add(label,pickersLayout,reserveButton);
       verticalLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

       return verticalLayout;
    }

    private void navigate() {
        UI.getCurrent().getPage().setLocation("/home");

    }

    public void createReservation (Reservation reservation){
        reservationService.createReservation(reservation);
    }
}
