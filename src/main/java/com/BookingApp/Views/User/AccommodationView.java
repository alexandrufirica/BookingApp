package com.BookingApp.Views.User;

import com.BookingApp.Data.Entity.Accommodation;
import com.BookingApp.Data.Repository.AccommodationRepository;
import com.BookingApp.Service.AccommodationService;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jdk.jfr.Registered;

@PageTitle("Accommodation-view")
@Route("/accommodationPage")
@PermitAll
public class AccommodationView extends VerticalLayout {

    private final AccommodationRepository accommodationRepository;

    public AccommodationView(AccommodationRepository accommodationRepository){
        this.accommodationRepository = accommodationRepository;

        Accommodation accommodation = accommodationRepository.getAccommodationById(MainView.accommodationId);

        addClassName("accommodation-view");

        H1 label = new H1("Accommodation" + accommodation.getName());
        add(label);
    }
}
