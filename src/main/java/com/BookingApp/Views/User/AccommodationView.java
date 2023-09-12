package com.BookingApp.Views.User;

import com.BookingApp.Data.Entity.Accommodation;
import com.BookingApp.Data.Repository.AccommodationRepository;
import com.BookingApp.Views.NavBar;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@PageTitle("Accommodation-view")
@Route("/accommodationPage")
@PermitAll
public class AccommodationView extends VerticalLayout {

    NavBar navBar = new NavBar();
    private final AccommodationRepository accommodationRepository;

    public AccommodationView(AccommodationRepository accommodationRepository){
        this.accommodationRepository = accommodationRepository;

        Accommodation accommodation = accommodationRepository.getAccommodationById(MainView.accommodationId);

        addClassName("accommodation-view");

        H1 label = new H1(accommodation.getName());

        DatePicker.DatePickerI18n singleFormat = new DatePicker.DatePickerI18n();
        singleFormat.setDateFormat("dd-MM-yyyy");

        DatePicker checkinPicker = new DatePicker("Check-in:");
        checkinPicker.setI18n(singleFormat);

        DatePicker checkoutPicker = new DatePicker("Check-out:");
        checkoutPicker.setI18n(singleFormat);

        HorizontalLayout pickersLayout = new HorizontalLayout();
        pickersLayout.add(checkinPicker, checkoutPicker);

        add(navBar,label, pickersLayout);

        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    }
}
