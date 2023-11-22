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
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@PageTitle(value = "Home")
@Route(value = "/home")
@RolesAllowed({"USER","ADMIN"})
public class HomeView extends AppLayout {
    public static final Long STATUS_AVAILABLE = 1L;
    UserNavBar userNavBar = new UserNavBar();
    AccommodationService accommodationService;
    RoomService roomService;
    ReservationService reservationService;
    public static long accommodationId;
    List<Accommodation> accommodations = new ArrayList<>();
    DatePicker checkinPicker = new DatePicker("Check-in:");
    DatePicker checkoutPicker = new DatePicker("Check-out:");
    VerticalLayout verticalLayout = new VerticalLayout();
    VerticalLayout cardLayout = new VerticalLayout();
    List<Reservation>  reservations = new ArrayList<>();

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

        verticalLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        cardLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        updateList();

        verticalLayout.add(pickersLayout,cardLayout);

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
        cardLayout.removeAll();

        accommodations.clear();
        accommodations.addAll(accommodationService.getAccommodationByHaveAvailableRooms());

        List<Accommodation> accommodationList = new ArrayList<>();
        accommodationList.clear();
        accommodationList.addAll(accommodations);

        Iterator<Accommodation> iterator = accommodationList.iterator();
        while(iterator.hasNext()){
            Accommodation accommodation = iterator.next();
            List<Room> roomList = new ArrayList<>();
            roomList.addAll(roomService.findRoomByAccommodationAndStatus(accommodation.getId(), STATUS_AVAILABLE));

            for (Room room : roomList) {
                int numberOfRoomsAvailable = room.getNumberOfRooms();
                if (reservationService.existsByRoomId(room.getId())) {
                    List<Reservation> reservationsList = new ArrayList<>();
                    reservationsList.addAll(reservationService.getAllReservationsByRoomId(room.getId()));
                    for (Reservation reservation : reservationsList) {
                        if (checkinPicker.getValue() != null && checkoutPicker.getValue() != null) {
                            if (reservation.containsInterval(checkinPicker.getValue(), checkoutPicker.getValue())) {
                                numberOfRoomsAvailable = --numberOfRoomsAvailable;
                            }
                        }
                    }
                    if (numberOfRoomsAvailable <= 0) {
                        iterator.remove();
                        break;
                    }
                }
            }
        }
        for (Accommodation accommod: accommodationList) {
            cardLayout.add(createCard(accommod));
        }

    }
}
