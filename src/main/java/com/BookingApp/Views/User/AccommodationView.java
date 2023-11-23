package com.BookingApp.Views.User;

import com.BookingApp.Data.Entity.Accommodation;
import com.BookingApp.Data.Entity.Room;
import com.BookingApp.Data.Repository.AccommodationRepository;
import com.BookingApp.Service.AccommodationService;
import com.BookingApp.Service.RoomService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

import java.util.ArrayList;
import java.util.List;

@PageTitle("Accommodation-view")
@Route("/accommodationPage")
@RolesAllowed({"USER","ADMIN"})
public class AccommodationView extends VerticalLayout {

    public static final Long STATUS_AVAILABLE = 1L;

    Grid<Room> grid = new Grid<>(Room.class);
    UserNavBar userNavBar = new UserNavBar();
    private final AccommodationService accommodationService;
    private RoomService roomService;
    private Accommodation accommodation;
    public static long roomId;
    DatePicker checkinPicker = new DatePicker("Check-in:");
    DatePicker checkoutPicker = new DatePicker("Check-out:");


    public AccommodationView(AccommodationService accommodationService, RoomService roomService, Room room){
        this.accommodationService = accommodationService;
        this.roomService = roomService;

        this.accommodation = accommodationService.getAccommodationById(HomeView.accommodationId);
        accommodation.setId(HomeView.accommodationId);

        addClassName("accommodation-view");

        H1 label = new H1(accommodation.getName());

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);

        configureGrid();
        add(
                userNavBar,
                label,
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
        DatePicker.DatePickerI18n singleFormat = new DatePicker.DatePickerI18n();
        singleFormat.setDateFormat("dd-MM-yyyy");

        checkinPicker.setI18n(singleFormat);
        checkoutPicker.setI18n(singleFormat);

        Button searchButton = new Button("Search");
        searchButton.addClickListener(e -> updateList());
        searchButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        HorizontalLayout pickersLayout = new HorizontalLayout();
        pickersLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);

        pickersLayout.add(checkinPicker, checkoutPicker, searchButton);

        return pickersLayout;
    }

    private void updateList() {
        List<Room> rooms = new ArrayList<>();
        rooms.addAll(roomService.findRoomByAccommodationAndStatus(accommodation.getId(), STATUS_AVAILABLE));
        grid.setItems(rooms);

    }


    private void navigate(Room room) {
        roomId = room.getId();
        UI.getCurrent().getPage().setLocation("/reservation");

    }

    public Accommodation getAccommodation() {
        return accommodation;
    }
}
