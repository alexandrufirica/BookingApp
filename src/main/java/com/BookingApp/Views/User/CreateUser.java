package com.BookingApp.Views.User;

import com.BookingApp.Data.Entity.Role;
import com.BookingApp.Data.Entity.User;
import com.BookingApp.Security.AuthService;
import com.BookingApp.Service.UserService;
import com.BookingApp.Views.Manager.RoomList;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.aspectj.weaver.ast.Not;
import org.springframework.web.bind.annotation.PostMapping;

@PageTitle("BookingApp - Create User Account")
@Route(value = "/newuser")
@AnonymousAllowed
public class CreateUser extends VerticalLayout {
    private TextField givenName;
    private TextField surName;
    private EmailField email;
    private TextField country;
    private TextField city;
    private TextField adress;
    private TextField phone;
    private TextField postalCode;
    private PasswordField password;
    private PasswordField reTypePassowrd;
    private Button createButton;
    private UserService userService;
    private final User user;

    private final AuthService authService;

    
    public CreateUser (UserService userService, User user, AuthService authService){
        this.userService = userService;
        this.user = user;
        this.authService = authService;


        H1 label = new H1("BookingApp");
        H1 label2 = new H1("Create your user profile");

        givenName = new TextField("Given Name");
        givenName.setRequiredIndicatorVisible(true);
        
        surName = new TextField("Surname Name");
        surName.setRequiredIndicatorVisible(true);
        country =new TextField("Country");

        city = new TextField("City");

        adress = new TextField("Adress");

        postalCode = new TextField("Postal Code");

        phone = new TextField("Phone Number");
        phone.setRequiredIndicatorVisible(true);
        phone.setPlaceholder("+0123456789");
        phone.setAllowedCharPattern("[0-9()+-]");
        phone.setPattern("^[+]?[(]?[0-9]{3}[)]?[-s.]?[0-9]{3}[-s.]?[0-9]{4,6}$");
        phone.setMinLength(5);
        phone.setMaxLength(18);

        email = new EmailField("Email address");
        email.setRequiredIndicatorVisible(true);
        email.getElement().setAttribute("name", "email");
        email.setErrorMessage("Enter a valid email address");
        email.setPlaceholder("john@mail.com");

        password = new PasswordField("Password");
        password.setRequiredIndicatorVisible(true);

        reTypePassowrd = new PasswordField("Re-type Password");
        reTypePassowrd.setRequiredIndicatorVisible(true);
        
        createButton = new Button("Create Profile", e -> {
            createUser(
                    givenName.getValue(),
                    surName.getValue(),
                    country.getValue(),
                    city.getValue(),
                    adress.getValue(),
                    postalCode.getValue(),
                    phone.getValue(),
                    email.getValue(),
                    password.getValue(),
                    reTypePassowrd.getValue()
            );
            createButton.getUI().ifPresent(ui -> ui.navigate("/login"));
            }
        );


        createButton.addClickShortcut(Key.ENTER);

        FormLayout formLayout = new FormLayout();
        formLayout.add(givenName,surName, country, city, adress,postalCode, phone, email, password, reTypePassowrd);
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("50", 1)
        );
        formLayout.setColspan(givenName,2);
        formLayout.setColspan(surName,2);
        add(label,label2, formLayout,createButton);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    }

    private void createUser(String givenName, String surName, String country, String city, String adress, String postalCode, String phone, String email, String password, String reTypePassowrd) {
        if(email.trim().isEmpty()){
            Notification.show("Enter a email");
        }else if (password.isEmpty()){
            Notification.show("Enter a password");
        }else if(!password.equals(reTypePassowrd)){
            Notification.show("Password don't match");
        }else {
            authService.registerUser(givenName, surName, email, country, city, adress, postalCode, phone, password, Role.USER);
            Notification.show("Registration succeeded.");
        }
    }

    @PostMapping
    public void createUser(User user){
        userService.createUser(user);

    }
}
