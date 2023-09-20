package com.BookingApp.Views.Manager;

import com.BookingApp.Data.Entity.Accommodation;
import com.BookingApp.Data.Entity.Role;
import com.BookingApp.Data.Entity.Roles;
import com.BookingApp.Data.Entity.User;
import com.BookingApp.Data.Repository.AccommodationRepository;
import com.BookingApp.Data.Repository.RoleRepository;
import com.BookingApp.Security.AuthService;
import com.BookingApp.Service.AccommodationService;
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

    private TextField name;
    private TextField country;
    private TextField city;
    private TextField adress;
    private EmailField email;
    private TextField phone;
    private TextField postalCode;
    private PasswordField password;
    private PasswordField reTypePassowrd;
    private Button createButton;
    private final AccommodationRepository accommodationRepository;
    private final RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public CreateAccomodation(AccommodationRepository accommodationRepository, RoleRepository roleRepository){
        this.accommodationRepository = accommodationRepository;
        this.roleRepository = roleRepository;


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



        createButton = new Button("Create Profile", e -> {
            createAccommodation(
                    name.getValue(),
                    country.getValue(),
                    city.getValue(),
                    adress.getValue(),
                    postalCode.getValue(),
                    phone.getValue(),
                    email.getValue(),
                    password.getValue(),
                    reTypePassowrd.getValue()
            );
            createButton.getUI().ifPresent( ui -> ui.navigate("/login"));
            }
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
        }else {
            registerAccommodation(name, country, city, adress, postalCode, phoneNumber, email, password);
            Notification.show("Registration succeeded.");
        }
    }

    public void registerAccommodation(String name, String country, String city, String adress, String postalCode, String phone,String email, String password) {

        Accommodation accommodation = new Accommodation();
        accommodation.setName(this.name.getValue());
        accommodation.setEmail(this.email.getValue());
        accommodation.setCountry(this.country.getValue());
        accommodation.setCity(this.city.getValue());
        accommodation.setAdress(this.adress.getValue());
        accommodation.setPostalCode(this.postalCode.getValue());
        accommodation.setPhoneNumber(this.phone.getValue());
        accommodation.setPassword(passwordEncoder.encode(this.password.getValue()));

        Role roles = roleRepository.findByName("ROLE_MANAGER").get();
        accommodation.setRoles(Collections.singleton(roles));
        accommodationRepository.save(accommodation);

    }

}
