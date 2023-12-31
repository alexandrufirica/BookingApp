package com.BookingApp.Views.User;

import com.BookingApp.Data.Entity.Accommodation;
import com.BookingApp.Data.Entity.Reservation;
import com.BookingApp.Data.Entity.Room;
import com.BookingApp.Service.AccommodationService;
import com.BookingApp.Service.ReservationService;
import com.BookingApp.Service.RoomService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import jakarta.annotation.security.RolesAllowed;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

@PageTitle("Accommodation-view")
@Route("/accommodationPage")
@RolesAllowed({"USER","ADMIN"})
public class AccommodationView extends VerticalLayout {

    public static final Long STATUS_AVAILABLE = 1L;

    Grid<Room> grid = new Grid<>(Room.class);
    UserNavBar userNavBar = new UserNavBar();
    private final AccommodationService accommodationService;
    private final RoomService roomService;
    private final Accommodation accommodation;
    private final ReservationService reservationService;
    public static long roomId;
    public static LocalDate dateIn;
    public static LocalDate dateOut;
    DatePicker checkinPicker = new DatePicker("Check-in:");
    DatePicker checkoutPicker = new DatePicker("Check-out:");


    public AccommodationView(AccommodationService accommodationService, RoomService roomService,ReservationService reservationService){
        this.accommodationService = accommodationService;
        this.roomService = roomService;
        this.reservationService = reservationService;

        this.accommodation = accommodationService.getAccommodationById(HomeView.accommodationId);
        accommodation.setId(HomeView.accommodationId);

        addClassName("accommodation-view");

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);

        configureGrid();
        add(
                userNavBar,
                getToolbar(),
                getContent()
        );
        updateList();

        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    }

    private void configureGrid() {
        grid.addClassName("room-grid");
        grid.setColumns("roomType","numberOfRooms","capacity","pricePerNight");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event -> navigate(grid.asSingleSelect().getValue()));
    }

    private HorizontalLayout getContent(){
        HorizontalLayout content = new HorizontalLayout(grid);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }

    private Component getToolbar() {
        H1 accommodationNameLabel = new H1(accommodation.getName());

        byte[] picture = accommodation.getProfilePicture();
        StreamResource resource = new StreamResource("profile-picture.jpg", () -> new ByteArrayInputStream(picture));
        Image image = new Image(resource,"Profile picture");
        image.setHeight("100px");
        image.setWidth("100px");

        HorizontalLayout label = new HorizontalLayout();
        label.add(image,accommodationNameLabel);
        label.setDefaultVerticalComponentAlignment(Alignment.CENTER);


        DatePicker.DatePickerI18n singleFormat = new DatePicker.DatePickerI18n();
        singleFormat.setDateFormat("dd-MM-yyyy");

        checkinPicker.setI18n(singleFormat);
        checkoutPicker.setI18n(singleFormat);

        dateIn = HomeView.dateIn;
        dateOut = HomeView.dateOut;

        checkinPicker.setValue(HomeView.dateIn);
        checkoutPicker.setValue(HomeView.dateOut);


        Button searchButton = new Button("Search");
        searchButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        searchButton.addClickShortcut(Key.ENTER);
        searchButton.addClickListener(e ->{
            if(checkinPicker.getValue() != null && checkoutPicker.getValue() != null) {
                if (checkoutPicker.getValue().isBefore(checkinPicker.getValue())) {
                    Notification.show("Check-out date can't be before Check-in date");
                } else if (checkoutPicker.getValue().equals(checkinPicker.getValue())) {
                    Notification.show("Check-out date can't be the same as Check-in date");
                } else {
                    dateIn = checkinPicker.getValue();
                    dateOut = checkoutPicker.getValue();
                    updateList();
                }
            }
        });


        FormLayout pickersLayout = new FormLayout();
        pickersLayout.add(checkinPicker, checkoutPicker, searchButton);
        pickersLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px",2)
        );
        pickersLayout.setColspan(searchButton,2);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(label,pickersLayout);
        verticalLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        return verticalLayout;
    }

    private void updateList() {
        List<Room> roomList = roomService.findRoomByAccommodationAndStatus(accommodation.getId(), STATUS_AVAILABLE);
        Iterator<Room> roomIterator = roomList.iterator();

        while (roomIterator.hasNext()) {
            Room room = roomIterator.next();
            int numberOfRoomsAvailable = room.getNumberOfRooms();
            if (reservationService.existsByRoomId(room.getId())) {

                List<Reservation> reservationsList = reservationService.getAllReservationsByRoomId(room.getId());
                Iterator<Reservation> reservationIterator =reservationsList.iterator();
                while (reservationIterator.hasNext()){
                    Reservation reservation = reservationIterator.next();
                    LocalDate checkIn = reservation.getCheckIn();
                    LocalDate checkOut = reservation.getCheckOut();
                    if (checkinPicker.getValue() != null && checkoutPicker.getValue() != null) {
                         dateIn = checkinPicker.getValue();
                         dateOut = checkoutPicker.getValue();
                        if (checkIn.isBefore(dateOut) && checkOut.isAfter(dateIn)) {
                            numberOfRoomsAvailable--;
                        }
                    }
                    if (numberOfRoomsAvailable <= 0) {
                        System.out.println("Before removing " + room.getRoomType());
                        roomIterator.remove();
                        System.out.println("After removing " + room.getRoomType());
                        break;
                    }
                }
            }

        }

        grid.setItems(roomList);

    }


    private void navigate(Room room) {
        roomId = room.getId();
        UI.getCurrent().getPage().setLocation("/reservation");

    }

    public Accommodation getAccommodation() {
        return accommodation;
    }
}
