package com.BookingApp.Views.User;

import com.BookingApp.Views.NavBar;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@PageTitle("Reservation")
@Route("/reservation")
@RolesAllowed({"USER","ADMIN","MANAGER"})

public class ReservationView extends VerticalLayout {

    NavBar navBar = new NavBar();

    public ReservationView (){

        addClassName("reservation-view");
        add(
                navBar,
                getComponent()
        );

        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    }

    private Component getComponent() {
        DatePicker.DatePickerI18n singleFormat = new DatePicker.DatePickerI18n();
        singleFormat.setDateFormat("dd-MM-yyyy");

        DatePicker checkinPicker = new DatePicker("Check-in:");
        checkinPicker.setI18n(singleFormat);
        checkinPicker.setRequiredIndicatorVisible(true);

        DatePicker checkoutPicker = new DatePicker("Check-out:");
        checkoutPicker.setI18n(singleFormat);
        checkoutPicker.setRequiredIndicatorVisible(true);

        TextField reservationName = new TextField("Reservation name");
        reservationName.setRequiredIndicatorVisible(true);

        Button reserveButton = new Button("Reserve");
        reserveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        HorizontalLayout pickersLayout = new HorizontalLayout();
        pickersLayout.add(checkinPicker, checkoutPicker);
        pickersLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);

       VerticalLayout verticalLayout = new VerticalLayout();
       verticalLayout.add(pickersLayout, reservationName, reserveButton);
       verticalLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        return verticalLayout;
    }
}
