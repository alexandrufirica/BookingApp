package com.BookingApp.Views.Login;

import com.BookingApp.Security.SecurityUtils;
import com.BookingApp.Service.AccommodationService;
import com.BookingApp.Service.PasswordResetService;
import com.BookingApp.Service.UserService;
import com.BookingApp.Views.Manager.CreateAccomodation;
import com.BookingApp.Views.Manager.RoomList;
import com.BookingApp.Views.User.CreateUser;
import com.BookingApp.Views.User.HomeView;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;


@PageTitle(value = "Booking Login")
@Route (value = "/login")
@RouteAlias(value = "")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    public final String LOGIN_ACCOMMODATION_URL ="/roomlist";
    public final String LOGIN_USER_URL ="/home";
    public final AccommodationService accommodationService;
    public final UserService userService;
    public final PasswordResetService passwordResetService;

    public LoginView(AccommodationService accommodationService, UserService userService, PasswordResetService passwordResetService){
        this.accommodationService = accommodationService;
        this.userService = userService;
        this.passwordResetService = passwordResetService;

        getStyle().set("background-color", "var(--lumo-contrast-5pct)")
                .set("display", "flex").set("justify-content", "center")
                .set("padding", "var(--lumo-space-l)");

        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        TextField email = new TextField("Email");
        email.setRequired(true);
        PasswordField password = new PasswordField("Password");
        password.setRequired(true);

        Button loginButton = new Button("Login", e -> {

            try {
                if(SecurityUtils.authenticate(email.getValue(),password.getValue())){
                    System.out.println("Authentication successful!");

                    VaadinSession.getCurrent().setAttribute("userEmail", email.getValue());

                    if(accommodationService.existsByEmail(email.getValue())){
                        UI.getCurrent().navigate(LOGIN_ACCOMMODATION_URL);
                    }else if( userService.existsByEmail(email.getValue())){
                        UI.getCurrent().navigate(LOGIN_USER_URL);
                    }
                }else {
                    System.out.println("Authentication failed!");
                    Notification.show("Wrong Credentials");
                }
            } catch (Exception ex) {
                System.out.println("Exception during authentication: " + ex.getMessage());
                Notification.show("Wrong Credentials");
            }
        });
        loginButton.addClickShortcut(Key.ENTER);
        loginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button forgotPassword = new Button("Forgot password", e ->{
            String userEmail = email.getValue();
            if(StringUtils.isNotBlank(userEmail)){
                passwordResetService.generatePasswordResetToken(userEmail);
                Notification.show("Password reset instructions sent to your email");
            }else{
                Notification.show("Please enter your email first");
            }
        });
        forgotPassword.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        add(
                new H1("BookingApp Login"),
                email,
                password,
                loginButton,
                forgotPassword
        );

        Button createUser = new Button("Create User Account");
        createUser.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        createUser.addClickListener( e ->
                createUser.getUI().ifPresent(ui -> ui.navigate(CreateUser.class))
        );


        Button createAccommodation = new Button("Create Accommodation Account");
        createAccommodation.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
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
        if(userIsUser()){
            beforeEnterEvent.forwardTo(HomeView.class);
        } else if (userIsManager()) {
            beforeEnterEvent.forwardTo(RoomList.class);
        }


    }
    private boolean userIsUser() {
        // Implement logic to check if the user is an admin
        // Example: return true if the user has admin role
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER"));
    }

    private boolean userIsManager() {
        // Implement logic to check if the user is an admin
        // Example: return true if the user has admin role
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_MANAGER"));
    }

}
