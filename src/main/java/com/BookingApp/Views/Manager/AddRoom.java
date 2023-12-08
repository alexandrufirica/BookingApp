package com.BookingApp.Views.Manager;

import com.BookingApp.Data.Entity.Accommodation;
import com.BookingApp.Data.Entity.Room;
import com.BookingApp.Data.Entity.Status;
import com.BookingApp.Security.CustomUserDetailsService;
import com.BookingApp.Service.RoomService;
import com.BookingApp.Service.StatusService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.web.bind.annotation.PostMapping;

@PageTitle("Booking App")
@Route (value = "/addRoom")
@RolesAllowed({"ADMIN","MANAGER"})
public class AddRoom extends VerticalLayout {
    private final Button button;
    private final TextField roomType;
    private final Select<Integer> roomCapacity;
    private final IntegerField numberOfRooms;
    private final Select<Status> available;
    private final TextArea roomDescription;
    private final NumberField pricePerNight;
    private final RoomService roomService;
    private final StatusService statusService;
    private final Room room;
    private final Status status = new Status();
    private final Accommodation accommodation;
    private final CustomUserDetailsService customUserDetailsService;


    public AddRoom(RoomService roomService, StatusService statusService, Accommodation accommodation, CustomUserDetailsService customUserDetailsService){
        this.roomService = roomService;
        this.statusService = statusService;
        this.customUserDetailsService = customUserDetailsService;

        this.room= new Room();
        this.accommodation = accommodation;

        accommodation = customUserDetailsService.getAccommodation();

        room.setAccommodation(accommodation);

        ManagerNavBar navBar = new ManagerNavBar();

        H1 name = new H1(" Welcome " + accommodation.getName());

        roomType = new TextField("Room Type");
        roomType.setRequiredIndicatorVisible(true);

        roomDescription = new TextArea("Description");
        roomDescription.setMinWidth("500px");
        roomDescription.setMaxWidth("1000px");
        roomDescription.setMinHeight("200px");
        roomDescription.setMaxHeight("500px");

        roomCapacity = new Select<>();
        roomCapacity.setLabel("Capacity");
        roomCapacity.setItems(1,2,3,4,5,6,7,8,9,10);
        roomCapacity.setPlaceholder("Select No of Persons");
        roomCapacity.setRequiredIndicatorVisible(true);

        numberOfRooms = new IntegerField("Number of Rooms");
        numberOfRooms.setRequiredIndicatorVisible(true);

        pricePerNight = new NumberField("Price Per Night");
        pricePerNight.setRequiredIndicatorVisible(true);

        available = new Select<>();
        available.setLabel("Room Status");
        available.setPlaceholder("Select Room Status");
        available.setItems(statusService.findAllStatuses());
        available.setItemLabelGenerator(Status::getName);
        available.setRequiredIndicatorVisible(true);

        button = new Button("Post Room");

            button.addClickListener(e -> {
                try {
                    room.setRoomType(roomType.getValue());
                    room.setCapacity(roomCapacity.getValue());
                    room.setNumberOfRooms(numberOfRooms.getValue());
                    room.setStatus(available.getValue());
                    room.setRoomDescription((roomDescription.getValue()));
                    room.setPricePerNight(pricePerNight.getValue());
                    createRoom(room);
                    button.getUI().ifPresent(ui -> ui.navigate(RoomList.class));
                }catch (Exception ex){
                    Notification notification = new Notification();
                    notification.setPosition(Notification.Position.BOTTOM_CENTER);
                    notification.setText("Please enter all requierd fields.");
                    notification.open();
                    notification.setDuration(3000);


                }
            });


        button.addClickShortcut(Key.ENTER);


        setMargin(true);

        HorizontalLayout horizontalLayout1 = new HorizontalLayout();
        HorizontalLayout horizontalLayout2 = new HorizontalLayout();

        horizontalLayout1.add(roomType,roomCapacity);
        horizontalLayout1.setDefaultVerticalComponentAlignment(Alignment.BASELINE);

        horizontalLayout2.add(numberOfRooms,pricePerNight);
        horizontalLayout2.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        add(navBar);
        add(name);
        add(horizontalLayout1);
        add(horizontalLayout2);
        add(roomDescription);
        add(available,button);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    }

    public boolean getAvailability(){
        return available.isEnabled();
    }
    @PostMapping
    public void createRoom(Room room){
        roomService.createRoom(room);

    }

}
