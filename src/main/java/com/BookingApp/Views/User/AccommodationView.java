package com.BookingApp.Views.User;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jdk.jfr.Registered;

@PageTitle("Accommodation-view")
@Route("/accommodation")
@PermitAll
public class AccommodationView extends VerticalLayout {

    public AccommodationView(){
        addClassName("accommodation-view");
        H1 label = new H1("Accommodation");
        add(label);
    }
}
