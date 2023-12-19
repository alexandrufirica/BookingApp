package com.BookingApp.Views.Login;


import com.BookingApp.Data.Entity.Accommodation;
import com.BookingApp.Data.Entity.User;
import com.BookingApp.Security.CustomUserDetailsService;
import com.BookingApp.Views.Manager.CreateAccomodation;
import com.BookingApp.Views.Manager.RoomList;
import com.BookingApp.Views.User.CreateUser;
import com.BookingApp.Views.User.HomeView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.security.core.context.SecurityContextHolder;


@PageTitle(value = "Booking Login")
@Route (value = "/login")
@RouteAlias(value = "")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final Accommodation accommodation;
    private final User user;
    private final CustomUserDetailsService customUserDetailsService;

    public LoginView(CustomUserDetailsService customUserDetailsService){
        this.customUserDetailsService = customUserDetailsService;

        accommodation = customUserDetailsService.getAccommodation();
        user =customUserDetailsService.getUser();

        addClassName("login");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        LoginForm loginForm = new LoginForm();
        loginForm.setAction("login");

        loginForm.addLoginListener(event -> {
                    if (customUserDetailsService.loadUserByUsername(event.getUsername()) != null) {
                        // Login successful
                        VaadinSession.getCurrent().setAttribute("userEmail", event.getUsername());

                        // Redirect to the appropriate view based on user role
                        if (userIsUser()) {
                            getUI().ifPresent(ui -> ui.navigate(HomeView.class));
                        } else if (userIsManager()) {
                            getUI().ifPresent(ui -> ui.navigate(RoomList.class));
                        }
                    } else {
                        // Handle login failure
                        Notification.show("Login failed");
                    }
                });

        LoginI18n i18n = LoginI18n.createDefault();
        LoginI18n.Form i18nForm = i18n.getForm();
        i18nForm.setTitle("Booking Application");
        i18nForm.setUsername("Email");
        i18nForm.setPassword("Password");
        i18nForm.setSubmit("Log in");
        i18n.setForm(i18nForm);
        i18n.setAdditionalInformation("To login as user use credentials: email: user@user.com and password:1234 or create your user profile or login as accommodation user credential email: accommodation@accommodation.com and password: 1234 or create your accommodation profile" );

        loginForm.setI18n(i18n);
        addRememberMeCheckbox();
        loginForm.setForgotPasswordButtonVisible(false);

        Button createUser = new Button("Create User Account");
        createUser.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        createUser.addClickListener( e ->
                createUser.getUI().ifPresent(ui -> ui.navigate(CreateUser.class))
        );

        Button createAccommodation = new Button("Create Accommodation Account");
        createAccommodation.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        createAccommodation.addClickListener( e ->
                createAccommodation.getUI().ifPresent(ui -> ui.navigate(CreateAccomodation.class))
        );

        add(
                loginForm,
                createUser,
                createAccommodation
                );

        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")){

            Notification.show("Login error");
        }

        if(userIsUser()){
            beforeEnterEvent.rerouteTo(HomeView.class);
        } else if (userIsManager()) {
            beforeEnterEvent.rerouteTo(RoomList.class);
        }


    }
    private boolean userIsUser() {
        // Implement logic to check if the user is an admin
        // Example: return true if the user has user role
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER"));
    }

    private boolean userIsManager() {
        // Implement logic to check if the user is an admin
        // Example: return true if the user has manager role
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_MANAGER"));
    }

    public void addRememberMeCheckbox() {
        Checkbox rememberMe = new Checkbox("Remember me");
        rememberMe.getElement().setAttribute("name", "remember-me");
        Element loginFormElement = getElement();
        Element element = rememberMe.getElement();
        loginFormElement.appendChild(element);

        String executeJsForFieldString = "const field = document.getElementById($0);" +
                "if(field) {" +
                "   field.after($1)" +
                "} else {" +
                "   console.error('could not find field', $0);" +
                "}";
        getElement().executeJs(executeJsForFieldString, "vaadinLoginPassword", element);

    }

}
