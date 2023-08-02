package com.BookingApp;

import com.BookingApp.SecurityConfig.SecurityService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import jakarta.annotation.security.RolesAllowed;

@PageTitle("Booking App")
@Route (value = "/main")
@RolesAllowed("ADMIN")
public class AddRoom extends VerticalLayout {
    private Button button;
    private TextField roomType;
    Select<Integer> roomCapacity;

    private TextField numberOfRooms;
    private Checkbox available;


    private SecurityService securityService = new SecurityService();
    public AddRoom(){
        NavBar navBar = new NavBar(securityService);
        Room room = new Room();

        H1 name = new H1("Your accomodation");

        roomType = new TextField("Room Type");
        roomCapacity = new Select<>();
        roomCapacity.setLabel("Person capacity");
        roomCapacity.setItems(1,2,3,4,5,6,7,8,9,10);
        roomCapacity.setPlaceholder("Select size");
        numberOfRooms = new TextField("Number of Rooms");
        available = new Checkbox("Available");

        button = new Button("Post room");

        button.addClickListener(e -> {
            room.setRoomType(roomType.getValue());
            room.setCapacity(roomCapacity.getValue());
            room.setNumberOfRooms(numberOfRooms.getValue());
            room.setAvailablility(available.isEnabled());
        });

        button.addClickListener(e -> {
            RouterLink routerLink = new RouterLink();
            routerLink.setRoute(BookingAppController.class);
        });
        button.addClickShortcut(Key.ENTER);

        setMargin(true);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(roomType,roomCapacity,numberOfRooms);
        horizontalLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        add(navBar);
        add(name);
        add(horizontalLayout);
        add(available,button);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    }



}
