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
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;

import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@PageTitle(value = "Home")
@Route(value = "/home")
@RolesAllowed({"USER","ADMIN"})
public class HomeView extends AppLayout implements AfterNavigationObserver {
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

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);
        grid.addComponentColumn(accommodation -> createCard(accommodation));
        grid.setAllRowsVisible(true);

        addToNavbar(userNavBar);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        verticalLayout.add(pickersLayout, grid);

        setContent(verticalLayout);

    }

    private Component createCard(Accommodation accommodation) {
        HorizontalLayout card = new HorizontalLayout();
        card.addClassName("card");
        card.setSpacing(false);
        card.getThemeList().add("spacing-s");

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
        card.add(decription);
        card.addClickListener(event -> {
            accommodationId = accommodation.getId();
            navigate();
        });
        return card;

    }

    private void navigate() {
        UI.getCurrent().getPage().setLocation("/accommodationPage");
        System.out.println("Accommodation have rooms? :" + roomService.existRoomByAccommodationId(accommodationId));


    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {

        accommodations.addAll(accommodationService.getAccommodationByHaveAvailableRooms());
        grid.setItems(accommodations);

        updateList();
    }

    public void updateList() {
//        List<Room> allRooms = new ArrayList<>();
//        allRooms.addAll(roomService.getAllRooms());
//
//        for (Room room : allRooms){
//            List<Room> roomsByAccommodation = new ArrayList<>();
//            roomsByAccommodation.addAll(roomService.findAllRoom("",room.getAccommodation().getId()));
//
//            int numberOfRoomsRemain = room.getNumberOfRooms();
//            if(reservationService.existsByRoomId(room.getId())){
//                List<Reservation> roomReservations = new ArrayList<>();
//                roomReservations.addAll(reservationService.getAllReservationsByRoomId(room.getId()));
//                for (Reservation reservation : roomReservations) {
//                    if(checkinPicker.getValue() != null && checkoutPicker.getValue() != null){
//                        if((reservation.getCheckIn().equals(checkinPicker.getValue()) ||
//                                checkinPicker.getValue().isAfter(reservation.getCheckIn()) && checkoutPicker.getValue().isBefore(reservation.getCheckOut()))) {
//                            numberOfRoomsRemain = --numberOfRoomsRemain;
//                            System.out.println(room.getRoomType() + " have available: " + numberOfRoomsRemain);
//                            if (numberOfRoomsRemain <= 0){
//                                roomsByAccommodation.remove(room);
//                                if(roomsByAccommodation.isEmpty()){
//                                    accommodations.remove(room.getAccommodation().getId());
//                                }
//                            }
//
//
//                        }
//
//                    } else {
//                        System.out.println(room.getRoomType() + " have available: " + numberOfRoomsRemain);
//                    }
//
//                }
//                }
//        }

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
