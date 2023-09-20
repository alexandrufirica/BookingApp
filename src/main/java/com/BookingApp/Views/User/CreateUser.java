package com.BookingApp.Views.User;

import com.BookingApp.Data.Entity.Role;
import com.BookingApp.Data.Entity.Roles;
import com.BookingApp.Data.Entity.User;
import com.BookingApp.Data.Repository.RoleRepository;
import com.BookingApp.Data.Repository.UserRepository;
import com.BookingApp.Security.AuthService;
import com.BookingApp.Security.CustomUserDetailsService;
import com.BookingApp.Security.SecurityConfig;
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
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;




    public CreateUser ( UserRepository userRepository, RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;


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

    private void createUser(String givenName, String surName, String country, String city, String adress, String postalCode, String phone, String email, String password, String reTypePassword) {
        if(email.trim().isEmpty()){
            Notification.show("Enter a email");
        }else if (password.isEmpty()){
            Notification.show("Enter a password");
        }else if(!password.equals(reTypePassword)){
            Notification.show("Password don't match");
        }else if (userRepository.existsByEmail(email)){
            Notification.show("This email allready exists!");
        } else {
            registerUser(givenName, surName, email, country, city, adress, postalCode, phone, password);
            Notification.show("Registration succeeded.");
            createButton.getUI().ifPresent(ui -> ui.navigate("/login"));
        }
    }

    public void registerUser(String givenName, String surName,String email, String country, String city, String adress, String postalCode, String phone, String password) {

        User user = new User();
        user.setGivenName(this.givenName.getValue());
        user.setSurName(this.surName.getValue());
        user.setEmail(this.email.getValue());
        user.setCountry(this.country.getValue());
        user.setCity(this.city.getValue());
        user.setAdress(this.adress.getValue());
        user.setPostalCode(this.postalCode.getValue());
        user.setPhoneNumber(this.phone.getValue());
        user.setPassword(passwordEncoder.encode(this.password.getValue()));

        Role roles = roleRepository.findByName("ROLE_USER").get();
        user.setRoles(Collections.singleton(roles));
        userRepository.save(user);

    }

}
