package com.BookingApp.Views.Manager;

import com.BookingApp.Data.Room;
import com.BookingApp.Service.RoomService;
import com.BookingApp.Views.NavBar;
import com.BookingApp.Security.SecurityService;
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
@Route (value = "/main")
@RolesAllowed("ADMIN")
public class AddRoom extends VerticalLayout {
    private Button button;
    private TextField roomType;
    Select<Integer> roomCapacity;
    private IntegerField numberOfRooms;
    private Checkbox available;
    private TextArea roomDescription;
    private NumberField pricePerNight;
    private final SecurityService securityService;
    private final RoomService roomService;
    public AddRoom(RoomService roomService, SecurityService securityService){
        this.roomService = roomService;
        this.securityService = securityService;

        NavBar navBar = new NavBar();

        Room room = new Room();

        H1 name = new H1("Your accomodation");

        roomType = new TextField("Room Type");

        roomDescription = new TextArea("Description");
        roomDescription.setMinWidth("500px");
        roomDescription.setMaxWidth("1000px");
        roomDescription.setMinHeight("200px");
        roomDescription.setMaxHeight("500px");

        roomCapacity = new Select<>();
        roomCapacity.setLabel("Capacity");
        roomCapacity.setItems(1,2,3,4,5,6,7,8,9,10);
        roomCapacity.setPlaceholder("Select no of Persons");

        numberOfRooms = new IntegerField("Number of Rooms");

        pricePerNight = new NumberField("Price Per Night");

        available = new Checkbox("Available");

        button = new Button("Post Room");
        button.addClickListener(e -> {
            room.setRoomType(roomType.getValue());
            room.setCapacity(roomCapacity.getValue());
            room.setNumberOfRooms(numberOfRooms.getValue());
            room.setAvailablility(available.isEnabled());
            room.setRoomDescription((roomDescription.getValue()));
            room.setPricePerNight(pricePerNight.getValue());
            createRoom(room);
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

    @PostMapping
    public void createRoom(Room room){
        roomService.createRoom(room);

    }
}
