package com.BookingApp.Views.User;

import com.BookingApp.Data.Entity.Accommodation;
import com.BookingApp.Data.Entity.Room;
import com.BookingApp.Data.Repository.AccommodationRepository;
import com.BookingApp.Service.AccommodationService;
import com.BookingApp.Service.RoomService;
import com.BookingApp.Views.NavBar;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

import java.util.ArrayList;
import java.util.List;

@PageTitle("Accommodation-view")
@Route("/accommodationPage")
@PermitAll
public class AccommodationView extends VerticalLayout {
    Grid<Room> grid = new Grid<>(Room.class);
    NavBar navBar = new NavBar();
    private final AccommodationRepository accommodationRepository;
    private RoomService roomService;
    private Accommodation accommodation;
    public static long roomId;

    public AccommodationView(AccommodationRepository accommodationRepository, RoomService roomService, Room room){
        this.accommodationRepository = accommodationRepository;
        this.roomService = roomService;

        this.accommodation = accommodationRepository.getAccommodationById(MainView.accommodationId);
        accommodation.setId(MainView.accommodationId);

        addClassName("accommodation-view");

        H1 label = new H1(accommodation.getName());

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);

        configureGrid();
        add(
                navBar,
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

        DatePicker checkinPicker = new DatePicker("Check-in:");
        checkinPicker.setI18n(singleFormat);

        DatePicker checkoutPicker = new DatePicker("Check-out:");
        checkoutPicker.setI18n(singleFormat);

        HorizontalLayout pickersLayout = new HorizontalLayout();
        pickersLayout.add(checkinPicker, checkoutPicker);

        return pickersLayout;
    }

    private void updateList() {

        grid.setItems(roomService.findRoomByAccommodation(accommodation.getId()));
    }


    private void navigate(Room room) {
        roomId = room.getId();
        UI.getCurrent().getPage().setLocation("/reservation");

    }

    public Accommodation getAccommodation() {
        return accommodation;
    }
}
