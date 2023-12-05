package com.BookingApp.Views.User;

import com.BookingApp.Data.Entity.Role;
import com.BookingApp.Data.Entity.User;
import com.BookingApp.Data.Repository.AccommodationRepository;
import com.BookingApp.Data.Repository.RoleRepository;
import com.BookingApp.Service.RoleService;
import com.BookingApp.Service.UserService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@PageTitle("BookingApp - Create User Account")
@Route(value = "/newuser")
@AnonymousAllowed
public class CreateUser extends VerticalLayout {
    private final TextField givenName;
    private final TextField surName;
    private final EmailField email;
    private final TextField country;
    private final TextField city;
    private final TextField adress;
    private final TextField phone;
    private final TextField postalCode;
    private final PasswordField password;
    private final PasswordField reTypePassowrd;
    private final Button createButton;
    private final UserService userService;
    private final RoleService roleService;
    private final AccommodationRepository accommodationRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public CreateUser ( UserService userService, RoleService roleService, AccommodationRepository accommodationRepository){
        this.userService = userService;
        this.roleService = roleService;
        this.accommodationRepository = accommodationRepository;


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
        
        createButton = new Button("Create Profile", e -> createUser(
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
        )
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

    private void createUser(String givenName, String surName, String country, String city, String adress, String postalCode, String phone, String email, String password, String reTypePassword) {
        if(email.trim().isEmpty()){
            Notification.show("Enter a email");
        }else if (password.isEmpty()){
            Notification.show("Enter a password");
        }else if(!password.equals(reTypePassword)){
            Notification.show("Password don't match");
        }else if (userService.existsByEmail(email)){
            Notification.show("This email allready exists as user!");
        } else if(accommodationRepository.existsByEmail(email)){
            Notification.show("This email allready exists as accommodation!");
        }else {
            registerUser(givenName, surName, email, country, city, adress, postalCode, phone, password);
            Notification.show("Registration succeeded.");
            createButton.getUI().ifPresent(ui -> ui.navigate("/login"));
        }
    }

    public void registerUser(String givenName, String surName,String email, String country, String city, String adress, String postalCode, String phone, String password) {

        User user = new User();
        user.setGivenName(givenName);
        user.setSurName(surName);
        user.setEmail(email);
        user.setCountry(country);
        user.setCity(city);
        user.setAdress(adress);
        user.setPostalCode(postalCode);
        user.setPhoneNumber(phone);
        user.setPassword(passwordEncoder.encode(password));

        Role roles = roleService.getRoleByName("ROLE_USER");
        user.setRoles(Collections.singleton(roles));
        userService.saveUser(user);

    }

}
