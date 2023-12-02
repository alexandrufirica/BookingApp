package com.BookingApp.Views.Manager;

import com.BookingApp.Data.Entity.Accommodation;
import com.BookingApp.Data.Entity.Room;
import com.BookingApp.Security.CustomUserDetailsService;
import com.BookingApp.Service.AccommodationService;
import com.BookingApp.Service.RoomService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import jakarta.annotation.security.RolesAllowed;

import java.io.ByteArrayInputStream;
import java.util.List;

@PageTitle(value = "RoomList")
@Route(value = "/roomlist")
@RolesAllowed({"ADMIN", "MANAGER"})
public class RoomList extends VerticalLayout {

    public static final Long STATUS_AVAILABLE = 1L;
    Grid<Room> grid = new Grid<>(Room.class);
    TextField filterText = new TextField();
    ManagerNavBar navBar = new ManagerNavBar();
    private final RoomService roomService;
    private final AccommodationService accommodationService;
    private Accommodation accommodation;

    RoomForm form;

    public RoomList(RoomService service, AccommodationService accommodationService) {
        this.roomService = service;
        this.accommodationService = accommodationService;
        this.accommodation = CustomUserDetailsService.accommodation;

        accommodation = accommodationService.getAccommodationById(CustomUserDetailsService.accommodation.getId());
        addClassName("roomList-view");

        configureGrid();
        configureForm();
        add(
                navBar,
                getLabel(),
                getToolbar(),
                getContent()
        );
        updateList();
        closeEditor();

        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    }

    private void configureForm() {
        form = new RoomForm(roomService.findAllStatuses());
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
        grid.setAllRowsVisible(true);

    }

    private HorizontalLayout getLabel(){
        byte[] picture = accommodation.getProfilePicture();
        StreamResource resource = new StreamResource("profile-picture.jpg", () -> new ByteArrayInputStream(picture));
        Image image = new Image(resource,"Profile picture");
        image.setHeight("100px");
        image.setWidth("100px");

        HorizontalLayout labelLayout = new HorizontalLayout();
        H1 label = new H1(accommodation.getName() + " Room List");
        labelLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        labelLayout.add(image,label);

        return  labelLayout;
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
        List<Room> rooms = roomService.findAllRoom(filterText.getValue(),accommodation.getId());

        List<Room> availableRooms = roomService.findRoomByAccommodationAndStatus(accommodation.getId(), STATUS_AVAILABLE);
        if (availableRooms.isEmpty()){
            accommodation.setHaveAvailableRooms(false);
            accommodationService.saveAccommodation(accommodation);
        }else{
            accommodation.setHaveAvailableRooms(true);
            accommodationService.saveAccommodation(accommodation);
        }
        grid.setItems(rooms);
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
