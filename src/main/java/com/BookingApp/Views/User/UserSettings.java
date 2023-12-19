package com.BookingApp.Views.User;

import com.BookingApp.Data.Entity.Accommodation;
import com.BookingApp.Data.Entity.User;
import com.BookingApp.Security.CustomUserDetailsService;
import com.BookingApp.Service.UserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@PageTitle(value = "Account Settings")
@Route(value = "/usersettings")
@RolesAllowed({"USER","ADMIN"})
public class UserSettings extends VerticalLayout {

    UserNavBar navBar = new UserNavBar();
    private final User user;
    private final TextField givenName;
    private final TextField surName;
    private final EmailField email;
    private final TextField country;
    private final TextField city;
    private final TextField adress;
    private final TextField phone;
    private final TextField postalCode;
    private final Button modifyUserDetails;
    private final UserService userService;
    private String userEmail;

    public UserSettings(UserService userService){
        this.userService = userService;

        addClassName("user-settings");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Retrieve user and accommodation details from userDetails
             userEmail = userDetails.getUsername();

        }
        user = userService.getUserbyEmail(userEmail);

        String username= user.getGivenName() + " " + user.getSurName();
        H1 label = new H1(username + " Account Settings");

        givenName = new TextField("Given Name");
        givenName.setRequiredIndicatorVisible(true);
        givenName.setValue(user.getGivenName());

        surName = new TextField("Surname Name");
        surName.setRequiredIndicatorVisible(true);
        surName.setValue(user.getSurName());

        country =new TextField("Country");
        country.setValue(user.getCountry());

        city = new TextField("City");
        city.setValue(user.getCity());

        adress = new TextField("Adress");
        adress.setValue(user.getAdress());

        postalCode = new TextField("Postal Code");
        postalCode.setValue(user.getPostalCode());

        phone = new TextField("Phone Number");
        phone.setRequiredIndicatorVisible(true);
        phone.setPlaceholder("+0123456789");
        phone.setAllowedCharPattern("[0-9()+-]");
        phone.setPattern("^[+]?[(]?[0-9]{3}[)]?[-s.]?[0-9]{3}[-s.]?[0-9]{4,6}$");
        phone.setMinLength(5);
        phone.setMaxLength(18);
        phone.setValue(user.getPhoneNumber());

        email = new EmailField("Email address");
        email.setRequiredIndicatorVisible(true);
        email.getElement().setAttribute("name", "email");
        email.setErrorMessage("Enter a valid email address");
        email.setPlaceholder("john@mail.com");
        email.setValue(user.getEmail());

        modifyUserDetails = new Button("Modify", e -> updateUser(
                givenName.getValue(),
                surName.getValue(),
                country.getValue(),
                city.getValue(),
                adress.getValue(),
                postalCode.getValue(),
                phone.getValue(),
                email.getValue()
        ));

        add(
                navBar,
                label,
                getUserDetails(),
                modifyUserDetails
        );

        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    }

    private Component getUserDetails(){
        FormLayout formLayout = new FormLayout();
        formLayout.add(givenName,surName, country, city, adress,postalCode, phone, email);
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("50", 1)
        );
        formLayout.setColspan(givenName,2);
        formLayout.setColspan(surName,2);
        add(formLayout,modifyUserDetails);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        return formLayout;
    }

    private void updateUser(String givenName, String surName, String country, String city, String adress, String postalCode, String phone, String email){
        user.setGivenName(givenName);
        user.setSurName(surName);
        user.setEmail(email);
        user.setCountry(country);
        user.setCity(city);
        user.setAdress(adress);
        user.setPostalCode(postalCode);
        user.setPhoneNumber(phone);

        userService.saveUser(user);
    }

}
