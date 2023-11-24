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
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.BoxSizing;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;

import java.time.LocalDate;
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
    public DatePicker checkinPicker = new DatePicker("Check-in");
    public DatePicker checkoutPicker = new DatePicker("Check-out");
    TextField locationField = new TextField("Location");
    public static LocalDate dateIn;
    public static LocalDate dateOut;
    VerticalLayout verticalLayout = new VerticalLayout();
    VerticalLayout cardLayout = new VerticalLayout();


    public HomeView(AccommodationService accommodationService, RoomService roomService, ReservationService reservationService) {
        this.accommodationService = accommodationService;
        this.roomService = roomService;
        this.reservationService = reservationService;
        addClassName("home-view");

        addToNavbar(userNavBar);

        verticalLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        cardLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        verticalLayout.add(
                getToolbar()
                ,cardLayout
        );

        updateList();

        setContent(verticalLayout);
    }

    private Component getToolbar() {
        DatePicker.DatePickerI18n singleFormat = new DatePicker.DatePickerI18n();
        singleFormat.setDateFormat("dd-MM-yyyy");

        checkinPicker.setI18n(singleFormat);
        checkoutPicker.setI18n(singleFormat);

        dateIn = AccommodationView.dateIn;
        dateOut = AccommodationView.dateOut;

        if(dateIn != null || dateOut != null){
            checkinPicker.setValue(AccommodationView.dateIn);
            checkoutPicker.setValue(AccommodationView.dateOut);
        }else {
            checkinPicker.setValue(LocalDate.now());
            checkoutPicker.setValue(LocalDate.now().plusDays(1));
        }

        Button searchButton = new Button("Search");
        searchButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        searchButton.addClickListener(e ->{
            if(checkinPicker.getValue() != null && checkoutPicker.getValue() != null) {
                if (checkoutPicker.getValue().isBefore(checkinPicker.getValue())) {
                    Notification.show("Check-out date can't be before Check-in date");
                } else if (checkoutPicker.getValue().equals(checkinPicker.getValue())) {
                    Notification.show("Check-out date can't be the same as Check-in date");
                } else {
                    updateList();
                }
            }
        });




        HorizontalLayout pickersLayout = new HorizontalLayout();
        pickersLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.BASELINE);
        pickersLayout.setBoxSizing(BoxSizing.UNDEFINED);

        pickersLayout.add(checkinPicker, checkoutPicker,locationField, searchButton);

        return pickersLayout;
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

        List<Accommodation> accommodationList;
        if(locationField.isEmpty()){
            accommodationList = accommodationService.getAccommodationByHaveAvailableRooms();
        }else{
            accommodationList = accommodationService.getAllAccommodationByCityOrCountryAndHaveAvailableRooms(locationField.getValue());
        }

        Iterator<Accommodation> AccommodationIterator = accommodationList.iterator();
        while (AccommodationIterator.hasNext()) {
            Accommodation accommodation = AccommodationIterator.next();
            List<Room> roomList = roomService.findRoomByAccommodationAndStatus(accommodation.getId(), STATUS_AVAILABLE);

            Iterator<Room> roomIterator = roomList.iterator();
            while (roomIterator.hasNext()) {
                Room room = roomIterator.next();
                int numberOfRoomsAvailable = room.getNumberOfRooms();
                if (reservationService.existsByRoomId(room.getId())) {
                    List<Reservation> reservationsList = reservationService.getAllReservationsByRoomId(room.getId());
                    for (Reservation reservation : reservationsList) {
                        LocalDate checkIn = reservation.getCheckIn();
                        LocalDate checkOut = reservation.getCheckOut();
                        if (checkinPicker.getValue() != null && checkoutPicker.getValue() != null) {
                            dateIn = checkinPicker.getValue();
                            dateOut = checkoutPicker.getValue();
                            if (checkIn.isBefore(dateOut) && checkOut.isAfter(dateIn)) {
                                --numberOfRoomsAvailable;
                            }
                        }
                    }
                }
                if (numberOfRoomsAvailable <= 0) {
                    roomIterator.remove();
                    break;
                }
            }

            if (roomList.isEmpty()) {
                AccommodationIterator.remove();
                break;
            }
        }
        for (Accommodation accommodation : accommodationList) {
            cardLayout.add(createCard(accommodation));
        }
    }
}
