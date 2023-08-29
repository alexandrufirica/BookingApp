package com.BookingApp.Views.Manager;

import com.BookingApp.Data.Entity.Room;
import com.BookingApp.Service.RoomService;
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

import java.util.Collections;

@PageTitle(value = "Booking App - Room List")
@Route(value = "/roomlist")
@RolesAllowed(value = "ADMIN")
public class RoomList extends VerticalLayout {
    Grid<Room> grid = new Grid<>(Room.class);
    TextField filterText = new TextField();
    NavBar navBar = new NavBar();
    private RoomService service;
    public RoomList(RoomService service) {
        this.service = service;
        addClassName("roomList-view");
        configureGrid();
        add(
                navBar,
                getToolbar(),
                getContent()
        );

        updateList();
    }

    private void updateList() {
        grid.setItems(service.findAllRoom(filterText.getValue()));
    }

    private Component getContent(){
        HorizontalLayout content = new HorizontalLayout(grid);
        content.setFlexGrow(2,grid);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }

    private Component getToolbar() {
         filterText.setPlaceholder("Filter by type...");
         filterText.setClearButtonVisible(true);
         filterText.setValueChangeMode(ValueChangeMode.LAZY);
         filterText.addValueChangeListener(e -> updateList());

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
