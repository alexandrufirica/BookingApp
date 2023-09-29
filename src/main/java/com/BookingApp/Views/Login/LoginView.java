package com.BookingApp.Views.Login;

import com.BookingApp.Data.Entity.Accommodation;
import com.BookingApp.Data.Entity.User;
import com.BookingApp.Security.CustomUserDetailsService;
import com.BookingApp.Security.SecurityUtils;
import com.BookingApp.Views.Manager.CreateAccomodation;
import com.BookingApp.Views.User.CreateUser;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;


@PageTitle(value = "Booking Login")
@Route (value = "/login")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    public static final String LOGIN_SUCCESS_URL ="/roomlist";
    LoginForm loginForm = new LoginForm();

    public LoginView(){
        getStyle().set("background-color", "var(--lumo-contrast-5pct)")
                .set("display", "flex").set("justify-content", "center")
                .set("padding", "var(--lumo-space-l)");

        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        loginForm.setAction("login");

        add(
                new H1("BookingApp Login"),
                loginForm);

        loginForm.getElement().setAttribute("no-autofocus", "");


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

            loginForm.setError(true);
        }

    }

}
