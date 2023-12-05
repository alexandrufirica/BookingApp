package com.BookingApp.Views.Login;

import com.BookingApp.Security.SecurityUtils;
import com.BookingApp.Service.AccommodationService;
import com.BookingApp.Service.UserService;
import com.BookingApp.Views.Manager.CreateAccomodation;
import com.BookingApp.Views.User.CreateUser;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;


@PageTitle(value = "Booking Login")
@Route (value = "/login")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    public static final String LOGIN_ACCOMMODATION_URL ="/roomlist";
    public static final String LOGIN_USER_URL ="/home";
    public final AccommodationService accommodationService;
    public final UserService userService;

    public LoginView(AccommodationService accommodationService, UserService userService){
        this.accommodationService = accommodationService;
        this.userService = userService;

        getStyle().set("background-color", "var(--lumo-contrast-5pct)")
                .set("display", "flex").set("justify-content", "center")
                .set("padding", "var(--lumo-space-l)");

        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        TextField email = new TextField("Email");
        PasswordField password = new PasswordField("Password");

        Button loginButton = new Button("Login", e -> {

            try {
                if(SecurityUtils.authenticate(email.getValue(),password.getValue())){
                    if(accommodationService.existsByEmail(email.getValue())){
                        UI.getCurrent().navigate(LOGIN_ACCOMMODATION_URL);
                    }else if( userService.existsByEmail(email.getValue())){
                        UI.getCurrent().navigate(LOGIN_USER_URL);
                    }
                }else {
                    Notification.show("Wrong Credentials");
                }
            } catch (Exception ex) {
                Notification.show("Wrong Credentials");
            }
        });
        loginButton.addClickShortcut(Key.ENTER);

        add(
                new H1("BookingApp Login"),
                email,
                password,
                loginButton
        );

        Button createUser = new Button("Create User Account");
        createUser.addClickListener( e ->
                createUser.getUI().ifPresent(ui -> ui.navigate(CreateUser.class))
        );


        Button createAccommodation = new Button("Create Accommodation Account");
        createAccommodation.addClickListener( e ->
                createAccommodation.getUI().ifPresent(ui -> ui.navigate(CreateAccomodation.class))
        );



        add(
                createUser,
                createAccommodation
                );

    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")){

            Notification.show("Login error");
        }

    }

}
