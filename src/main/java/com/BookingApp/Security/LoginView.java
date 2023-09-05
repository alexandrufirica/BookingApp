package com.BookingApp.Security;

import com.BookingApp.Views.Manager.CreateAccomodation;
import com.BookingApp.Views.Manager.CreateUser;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@PageTitle(value = "Booking Login")
@Route (value = "/login")
@AnonymousAllowed
public class LoginView extends VerticalLayout  {

    public static final String LOGIN_SUCCESS_URL ="/addRoom";

    public LoginView(AuthService authService){
        getStyle().set("background-color", "var(--lumo-contrast-5pct)")
                .set("display", "flex").set("justify-content", "center")
                .set("padding", "var(--lumo-space-l)");

        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        TextField email = new TextField("Email");
        PasswordField password = new PasswordField("Password");

        add(
                new H1("Welcome at Booking App"),
                email,
                password,
                new Button("Login", e -> {
                    try {
                        authService.authenticate(email.getValue(), password.getValue());
                        UI.getCurrent().navigate("MyApp");
                    } catch (AuthService.AuthException ex) {
                        Notification.show("Wrong Credentials");
                    }

                })
        );



        add(
                createButton("Create User Account", CreateUser.class),
                createButton("Create Accommodation Account", CreateAccomodation.class));

    }

    private Button createButton(String buttonName, Class cls) {
        RouterLink link = new RouterLink();
        link.add(buttonName);
        link.setRoute(cls);
        link.setTabIndex(-1);
        Button button = new Button(link);
        button.addThemeVariants(
                ButtonVariant.LUMO_TERTIARY
        );

        return button;
    }

}
