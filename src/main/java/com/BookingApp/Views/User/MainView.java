package com.BookingApp.Views.User;

import com.BookingApp.Data.Entity.Accommodation;
import com.BookingApp.Data.Entity.Room;
import com.BookingApp.Data.Repository.AccommodationRepository;
import com.BookingApp.Service.AccommodationService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@PageTitle(value = "Main View")
@Route(value = "/mainview")
@RolesAllowed("USER")
public class MainView extends VerticalLayout implements AfterNavigationObserver {

    Grid<Accommodation>  grid = new Grid<>();

    AccommodationService accommodationService;
    public MainView(AccommodationService accommodationService){
        this.accommodationService = accommodationService;
        addClassName("home-view");
        setSizeFull();
        grid.setHeight("100%");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);
        grid.addComponentColumn( accommodation -> createCard(accommodation));
        add(grid);
    }

    private Component createCard(Accommodation accommodation) {
        HorizontalLayout card = new HorizontalLayout();
        card.addClassName("card");
        card.setSpacing(false);
        card.getThemeList().add("spacing-s");

        VerticalLayout decription = new VerticalLayout();
        decription.addClassName("decription");
        decription.setSpacing(false);
        decription.setPadding(false);

        HorizontalLayout header = new HorizontalLayout();
        header.addClassName("header");
        header.setSpacing(false);
        header.getThemeList().add("spacing-s");

        Span name = new Span(accommodation.getName());
        name.addClassName("name");
        Span country = new Span(accommodation.getCountry());
        country.addClassName("country");
        Span city = new Span(accommodation.getCity());
        city.addClassName("city");

        header.add(name,country,city);

        decription.add(header);
        card.add(decription);
        return card;

    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {

        List<Accommodation> accommodations = new ArrayList<>();

        accommodations.addAll(accommodationService.getAllAccommodations());

        grid.setItems(accommodations);
    }

}
