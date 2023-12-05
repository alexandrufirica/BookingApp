package com.BookingApp.Views.Manager;

import com.BookingApp.Data.Entity.Accommodation;
import com.BookingApp.Data.Entity.Role;
import com.BookingApp.Service.AccommodationService;
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

@PageTitle("BookingApp - Create Accommodation")
@Route(value = "/newaccommodation")
@AnonymousAllowed
public class CreateAccomodation extends VerticalLayout {

    private final TextField name;
    private final TextField country;
    private final TextField city;
    private final TextField adress;
    private final EmailField email;
    private final TextField phone;
    private final TextField postalCode;
    private final PasswordField password;
    private final PasswordField reTypePassowrd;
    private final Button createButton;
    private final AccommodationService accommodationService;
    private final RoleService roleService;
    private final UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public CreateAccomodation(AccommodationService accommodationService, RoleService roleService, UserService userService){
        this.accommodationService = accommodationService;
        this.roleService = roleService;
        this.userService = userService;


        H1 label = new H1("BookingApp");
        H1 label2 = new H1("Create your accommodation profile");

        name = new TextField("Accommodation Name");
        name.setRequiredIndicatorVisible(true);

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
        reTypePassowrd.setErrorMessage("Password donesn't match");



        createButton = new Button("Create Profile", e -> createAccommodation(
                name.getValue(),
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
        formLayout.add(name, country, city, adress,postalCode, phone, email, password, reTypePassowrd);
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("50", 1)
        );
        formLayout.setColspan(name,2);
        add(label,label2, formLayout,createButton);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    }


    private void createAccommodation(String name, String country, String city, String adress, String postalCode, String phoneNumber, String email, String password, String reTypePassword){
        if(email.trim().isEmpty()){
            Notification.show("Enter a email");
        }else if (password.isEmpty()){
            Notification.show("Enter a password");
        }else if(!password.equals(reTypePassword)){
            Notification.show("Password don't match");
        }else if(accommodationService.existsByEmail(email)){
            Notification.show("This email allready exists!");
        } else if (userService.existsByEmail(email)) {
            Notification.show("This email allready exists!");
        } else {
            registerAccommodation(name, country, city, adress, postalCode, phoneNumber, email, password);
            Notification.show("Registration succeeded.");
            createButton.getUI().ifPresent( ui -> ui.navigate("/login"));

        }
    }

    public void registerAccommodation(String name, String country, String city, String adress, String postalCode, String phone,String email, String password) {

        Accommodation accommodation = new Accommodation();
        accommodation.setName(name);
        accommodation.setEmail(email);
        accommodation.setCountry(country);
        accommodation.setCity(city);
        accommodation.setAdress(adress);
        accommodation.setPostalCode(postalCode);
        accommodation.setPhoneNumber(phone);
        accommodation.setPassword(passwordEncoder.encode(password));
        accommodation.setHaveAvailableRooms(false);

        Role roles = roleService.getRoleByName("ROLE_MANAGER");
        accommodation.setRoles(Collections.singleton(roles));
        accommodationService.saveAccomodation(accommodation);

    }

}
