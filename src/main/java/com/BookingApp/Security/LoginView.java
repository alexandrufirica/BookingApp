package com.BookingApp.Security;

import com.BookingApp.Views.Manager.CreateAccomodation;
import com.BookingApp.Views.Manager.CreateUser;
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
public class LoginView extends VerticalLayout implements BeforeEnterListener, ComponentEventListener<AbstractLogin.LoginEvent> {

    public static final String LOGIN_SUCCESS_URL ="/myapp";
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

        add(new H1("BookingApp Login"), loginForm);

        loginForm.getElement().setAttribute("no-autofocus", "");

        add(
                createButton("Create User Account", CreateUser.class),
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

    public void onComponentEvent(AbstractLogin.LoginEvent loginEvent) {
        boolean authenticated = SecurityUtils.authenticate(
                loginEvent.getUsername(), loginEvent.getPassword());
        System.out.println(authenticated);
        System.out.println(loginEvent.getUsername() + "" +loginEvent.getPassword());
        if (authenticated) {
            UI.getCurrent().getPage().setLocation(LOGIN_SUCCESS_URL);
        } else {
            loginForm.setError(true);
        }
    }
}
