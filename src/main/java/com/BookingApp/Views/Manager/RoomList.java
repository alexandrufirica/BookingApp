package com.BookingApp.Views.Manager;

import com.BookingApp.Data.Room;
import com.BookingApp.Security.SecurityService;
import com.BookingApp.Views.NavBar;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@PageTitle(value = "Booking App - Room List")
@Route(value = "/roomlist")
@RolesAllowed(value = "ADMIN")
public class RoomList extends VerticalLayout {
    Grid<Room> grid = new Grid<>(Room.class);
    TextField filterText = new TextField();
    NavBar navBar = new NavBar();

    public RoomList() {
        addClassName("roomList-view");
        configureGrid();

        add(
                navBar,
                getToolbar(),
                grid
        );
    }

    private Component getToolbar() {
         filterText.setPlaceholder("Filter by type...");
         filterText.setClearButtonVisible(true);
         filterText.setValueChangeMode(ValueChangeMode.LAZY);

        Button addRoomButton = new Button("Add Room");

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addRoomButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void configureGrid() {
        grid.addClassName("room-grid");
        grid.setColumns("roomType","numberOfRooms","capacity","pricePerNight","availablility");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }
}
