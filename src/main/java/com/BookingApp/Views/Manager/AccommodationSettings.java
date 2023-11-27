package com.BookingApp.Views.Manager;

import com.BookingApp.Data.Entity.Accommodation;
import com.BookingApp.Security.CustomUserDetailsService;
import com.BookingApp.Service.AccommodationService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@PageTitle(value = "Account Settings")
@Route(value = "/accommodationsettings")
@RolesAllowed({"MANAGER","ADMIN"})
public class AccommodationSettings extends VerticalLayout {

    ManagerNavBar navBar = new ManagerNavBar();
    private final TextField name;
    private final TextField country;
    private final TextField city;
    private final TextField adress;
    private final EmailField email;
    private final TextField phone;
    private final TextField postalCode;
    private final Accommodation accommodation;
    private final Button modifyAccommodationDetails;
    private final AccommodationService accommodationService;
    public AccommodationSettings(AccommodationService accommodationService){
        this.accommodationService = accommodationService;

        accommodation = CustomUserDetailsService.accommodation;

        H1 label = new H1(accommodation.getName() + " Account Settings");

        name = new TextField("Accommodation Name");
        name.setRequiredIndicatorVisible(true);
        name.setValue(accommodation.getName());

        country =new TextField("Country");
        country.setValue(accommodation.getCountry());

        city = new TextField("City");
        city.setValue(accommodation.getCity());

        adress = new TextField("Adress");
        adress.setValue(accommodation.getAdress());

        postalCode = new TextField("Postal Code");
        postalCode.setValue(accommodation.getPostalCode());

        phone = new TextField("Phone Number");
        phone.setRequiredIndicatorVisible(true);
        phone.setPlaceholder("+0123456789");
        phone.setAllowedCharPattern("[0-9()+-]");
        phone.setPattern("^[+]?[(]?[0-9]{3}[)]?[-s.]?[0-9]{3}[-s.]?[0-9]{4,6}$");
        phone.setMinLength(5);
        phone.setMaxLength(18);
        phone.setValue(accommodation.getPhoneNumber());

        email = new EmailField("Email address");
        email.setRequiredIndicatorVisible(true);
        email.getElement().setAttribute("name", "email");
        email.setErrorMessage("Enter a valid email address");
        email.setPlaceholder("john@mail.com");
        email.setValue(accommodation.getEmail());

        modifyAccommodationDetails = new Button("Modify", e -> {
            updateAccommodation(
                    name.getValue(),
                    country.getValue(),
                    city.getValue(),
                    adress.getValue(),
                    postalCode.getValue(),
                    phone.getValue(),
                    email.getValue()
            );
        });
        
        add(
                navBar,
                label,
                getaccommodationDetails(),
                modifyAccommodationDetails
        );

        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

    }

    private Component getaccommodationDetails(){
        FormLayout formLayout = new FormLayout();
        formLayout.add(name, country, city, adress,postalCode, phone, email);
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("50", 1)
        );
        formLayout.setColspan(name,2);
        add(formLayout,modifyAccommodationDetails);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        return formLayout;
    }

    private void updateAccommodation(String name, String country, String city, String adress, String postalCode, String phone, String email){
        accommodation.setName(this.name.getValue());
        accommodation.setEmail(this.email.getValue());
        accommodation.setCountry(this.country.getValue());
        accommodation.setCity(this.city.getValue());
        accommodation.setAdress(this.adress.getValue());
        accommodation.setPostalCode(this.postalCode.getValue());
        accommodation.setPhoneNumber(this.phone.getValue());

        accommodationService.saveAccommodation(accommodation);
    }
}
