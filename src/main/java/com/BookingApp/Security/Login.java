package com.BookingApp.Security;

import com.BookingApp.Views.Manager.CreateAccomodation;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;


@PageTitle(value = "Booking Login")
@Route (value = "/login")
public class Login extends VerticalLayout implements BeforeEnterListener {

    LoginForm loginForm = new LoginForm();
    public Login(){
        getStyle().set("background-color", "var(--lumo-contrast-5pct)")
                .set("display", "flex").set("justify-content", "center")
                .set("padding", "var(--lumo-space-l)");

        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

//        Button createAccomodation = new Button("Create Accommodation Account");
//        createAccomodation.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
//
//        Button createUsers = new Button("Create User Account");
//        createUsers.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);


        loginForm.setAction("login");

        add(
                new H1("BookingApp Login"),
                loginForm);

        loginForm.getElement().setAttribute("no-autofocus", "");
//        add(createUsers,createAccomodation);
        add( createButton("Create User Account", CreateAccomodation.class),
                createButton("Create Accommodation Account", CreateAccomodation.class));

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
