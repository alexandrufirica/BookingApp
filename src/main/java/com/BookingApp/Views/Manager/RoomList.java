package com.BookingApp.Views.Manager;

import com.BookingApp.Data.Entity.Accommodation;
import com.BookingApp.Data.Entity.Room;
import com.BookingApp.Data.Entity.Status;
import com.BookingApp.Security.CustomUserDetailsService;
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
import com.vaadin.flow.router.RouteAlias;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;

import java.util.Collections;

@PageTitle(value = "RoomList")
@Route(value = "/roomlist")
@RolesAllowed({"ADMIN", "MANAGER"})
public class RoomList extends VerticalLayout {
    Grid<Room> grid = new Grid<>(Room.class);
    TextField filterText = new TextField();
    NavBar navBar = new NavBar();
    private RoomService roomService;
    private Accommodation accommodation;
    RoomForm form;

    public RoomList(RoomService service) {
        this.roomService = service;
        this.accommodation = CustomUserDetailsService.accommodation;

        accommodation.setId(CustomUserDetailsService.accommodation.getId());
        addClassName("roomList-view");
        configureGrid();
        configureForm();
        add(
                navBar,
                getToolbar(),
                getContent()
        );
        updateList();
        closeEditor();
    }

    private void configureForm() {
        form = new RoomForm(roomService.findAllAccommodations(), roomService.findAllStatuses());
        form.setWidth("5em");
        form.addSaveListener(this::saveRoom);
        form.addDeleteListener(this::deleteRoom);
        form.addCloseListener(e -> closeEditor());
    }

    private void configureGrid() {
        grid.addClassName("room-grid");
        grid.setColumns("roomType","numberOfRooms","capacity","pricePerNight");
        grid.addColumn(Room::getStatusName).setHeader("Availbility");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event -> editRoom( event.getValue()));
    }

    private HorizontalLayout getContent(){
        HorizontalLayout content = new HorizontalLayout(grid,form);
        content.setFlexGrow(2,grid);
        content.setFlexGrow(1,form);
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
        addRoomButton.addClickListener( e -> addRoom());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addRoomButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void deleteRoom(RoomForm.DeleteEvent event) {
        roomService.deleteRoom(event.getRoom());
        updateList();
        closeEditor();
    }

    private void saveRoom(RoomForm.SaveEvent event) {
        roomService.saveRoom(event.getRoom());
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setRoom(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(roomService.findRoomByAccommodation(accommodation.getId()));
    }

    private void editRoom(Room room) {
        if(room == null){
            closeEditor();
        }else {
            room.setAccommodation(accommodation);
            form.setRoom(room);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void addRoom(){
        grid.asSingleSelect().clear();
        editRoom(new Room());
    }
}
