package com.BookingApp.Views.User;

import com.BookingApp.Data.Entity.Accommodation;
import com.BookingApp.Data.Entity.Reservation;
import com.BookingApp.Data.Entity.Room;
import com.BookingApp.Service.AccommodationService;
import com.BookingApp.Service.ReservationService;
import com.BookingApp.Service.RoomService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.shared.ThemeVariant;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;

import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@PageTitle(value = "Home")
@Route(value = "/home")
@RolesAllowed({"USER","ADMIN"})
public class HomeView extends AppLayout {
    public static final Long STATUS_AVAILABLE = 1L;
    Grid<Accommodation> grid = new Grid<>();
    UserNavBar userNavBar = new UserNavBar();
    AccommodationService accommodationService;
    RoomService roomService;
    ReservationService reservationService;
    public static long accommodationId;
    List<Accommodation> accommodations = new ArrayList<>();
    DatePicker checkinPicker = new DatePicker("Check-in:");
    DatePicker checkoutPicker = new DatePicker("Check-out:");
    VerticalLayout verticalLayout = new VerticalLayout();

    public HomeView(AccommodationService accommodationService, RoomService roomService, ReservationService reservationService) {
        this.accommodationService = accommodationService;
        this.roomService = roomService;
        this.reservationService = reservationService;
        addClassName("home-view");

        DatePicker.DatePickerI18n singleFormat = new DatePicker.DatePickerI18n();
        singleFormat.setDateFormat("dd-MM-yyyy");

        checkinPicker.setI18n(singleFormat);
        checkoutPicker.setI18n(singleFormat);

        Button searchButton = new Button("Search");
        searchButton.addClickListener(e -> updateList());
        searchButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        HorizontalLayout pickersLayout = new HorizontalLayout();
        pickersLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.BASELINE);
        pickersLayout.add(checkinPicker, checkoutPicker, searchButton);

        addToNavbar(userNavBar);

        accommodations.addAll(accommodationService.getAccommodationByHaveAvailableRooms());

        verticalLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        verticalLayout.add(pickersLayout);
        for (Accommodation accommod: accommodations) {
            verticalLayout.add(createCard(accommod));
        }

        setContent(verticalLayout);

    }

    private Component createCard(Accommodation accommodation) {
        Button card = new Button();
        card.addClassName("card");

        VerticalLayout decription = new VerticalLayout();
        decription.addClassName("decription");
        decription.setSpacing(false);
        decription.setPadding(false);

        HorizontalLayout header = new HorizontalLayout();
        header.addClassName("header");
        header.getThemeList().add("spacing-s");

        Span name = new Span(accommodation.getName());
        name.addClassName("name");
        Span country = new Span(accommodation.getCountry());
        country.addClassName("country");
        Span city = new Span(accommodation.getCity());
        city.addClassName("city");
        header.add(name, country, city);

        decription.add(header);
        card.setIcon(decription);
        card.addClickListener(event -> {
            accommodationId = accommodation.getId();
            navigate();
        });
        card.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        return card;

    }

    private void navigate() {
        UI.getCurrent().getPage().setLocation("/accommodationPage");
        System.out.println("Accommodation have rooms? :" + roomService.existRoomByAccommodationId(accommodationId));


    }


    public void updateList() {
        try {
            List<Accommodation> accommodationList = accommodations;
            for (Accommodation accommodation : accommodationList) {
                List<Room> roomList = new ArrayList<>();
                List<Room> removedRooms = new ArrayList<>();
                roomList.addAll(roomService.findRoomByAccommodationAndStatus(accommodation.getId(), STATUS_AVAILABLE));
                System.out.println("Available Rooms are : " + roomList);
                for (Room room : roomList) {
                    int numberOfRoomsAvailable = room.getNumberOfRooms();
                    if (reservationService.existsByRoomId(room.getId())) {
                        List<Reservation> reservationsList = new ArrayList<>();
                        reservationsList.addAll(reservationService.getAllReservationsByRoomId(room.getId()));
                        for (Reservation reservation : reservationsList) {
                            if (checkinPicker.getValue() != null && checkoutPicker.getValue() != null) {
                                if ((reservation.getCheckIn().equals(checkinPicker.getValue()) ||
                                        checkinPicker.getValue().isAfter(reservation.getCheckIn()) && checkoutPicker.getValue().isBefore(reservation.getCheckOut()))) {
                                    numberOfRoomsAvailable = --numberOfRoomsAvailable;
                                }
                            }
                        }
                    }
                    if (numberOfRoomsAvailable <= 0) {
                        removedRooms.add(room);
                    }
                    if (room == null || roomList.isEmpty()) {
                        accommodationList.remove(accommodation);
                    }
                }
                if (removedRooms.isEmpty()) {
                    grid.setItems(accommodationList);
                } else {
                    roomList.removeAll(removedRooms);
                    accommodations.remove(accommodation);
                    grid.setItems(accommodations);
                }
            }

        }catch (Exception ex){
            grid.setItems(accommodations);
        }
    }
}
