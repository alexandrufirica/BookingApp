package com.BookingApp.Views.Manager;

import com.BookingApp.Data.Entity.Accommodation;
import com.BookingApp.Data.Entity.Room;
import com.BookingApp.Data.Entity.Status;
import com.BookingApp.Security.CustomUserDetailsService;
import com.BookingApp.Service.RoomService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.H1;
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
    private Button button;
    private TextField roomType;
    Select<Integer> roomCapacity;
    private IntegerField numberOfRooms;
    private Checkbox available;
    private TextArea roomDescription;
    private NumberField pricePerNight;
    private final RoomService roomService;
    private final Room room;
    private Status status;
    private Accommodation accommodation;

    public AddRoom(RoomService roomService, Status status, Accommodation accommodation){
        this.roomService = roomService;
        this.status = status;
        this.room= new Room();
        this.accommodation = accommodation;

        accommodation.setId(CustomUserDetailsService.accommodation.getId());

        room.setAccommodation(accommodation);
        room.setStatus(status);


        ManagerNavBar navBar = new ManagerNavBar();

        H1 name = new H1(" Welcome " + CustomUserDetailsService.accommodation.getName());

        roomType = new TextField("Room Type");

        roomDescription = new TextArea("Description");
        roomDescription.setMinWidth("500px");
        roomDescription.setMaxWidth("1000px");
        roomDescription.setMinHeight("200px");
        roomDescription.setMaxHeight("500px");

        roomCapacity = new Select<>();
        roomCapacity.setLabel("Capacity");
        roomCapacity.setItems(1,2,3,4,5,6,7,8,9,10);
        roomCapacity.setPlaceholder("Select No of Persons");

        numberOfRooms = new IntegerField("Number of Rooms");

        pricePerNight = new NumberField("Price Per Night");

        available = new Checkbox("Available");

        button = new Button("Post Room");
        button.addClickListener(e -> {
            room.setRoomType(roomType.getValue());
            room.setCapacity(roomCapacity.getValue());
            room.setNumberOfRooms(numberOfRooms.getValue());
            room.setAvailability(available.getValue());
            room.setRoomDescription((roomDescription.getValue()));
            room.setPricePerNight(pricePerNight.getValue());
            createRoom(room);
            button.getUI().ifPresent(ui -> ui.navigate(RoomList.class));
        });

        button.addClickShortcut(Key.ENTER);

        if(available.isEnabled()){
            status.setId(1L);
        }else {
            status.setId(0L);
        }

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
        if(available.isEnabled()){
            return true;
        }
        return false;
    }
    @PostMapping
    public void createRoom(Room room){
        roomService.createRoom(room);

    }

}
