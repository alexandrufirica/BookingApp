package com.BookingApp.Views.Manager;

import com.BookingApp.Data.Entity.Accommodation;
import com.BookingApp.Service.AccommodationService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@PageTitle("BookingApp - Create Accommodation")
@Route(value = "/newaccommodation")
@Component
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
    private final AccommodationService accommodationService;
    private final Accommodation accommodation;

    public CreateAccomodation(AccommodationService accommodationService, Accommodation accommodation){
        this.accommodationService = accommodationService;
        this.accommodation = accommodation;

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



        createButton = new Button("Create Profile");
        createButton.addClickListener( e -> {
           accommodation.setName(name.getValue());
           accommodation.setCountry(country.getValue());
           accommodation.setCity(city.getValue());
           accommodation.setAdress(adress.getValue());
           accommodation.setPostalCode(postalCode.getValue());
           accommodation.setPhoneNumber(phone.getValue());
           accommodation.setEmail(email.getValue());
           accommodation.setPassword(password.getValue());
           accommodation.setRole("MANAGER");
           createAccommodation(accommodation);
        });

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

    @PostMapping
    public void createAccommodation(Accommodation accommodation){
        accommodationService.createAccomodation(accommodation);

    }
}
