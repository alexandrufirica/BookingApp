package com.BookingApp.Views.User;

import com.BookingApp.Data.Entity.Accommodation;
import com.BookingApp.Data.Entity.Room;
import com.BookingApp.Service.AccommodationService;
import com.BookingApp.Service.RoomService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@PageTitle(value = "Home")
@Route(value = "/home")
@RolesAllowed({"USER","ADMIN"})
public class HomeView extends AppLayout implements AfterNavigationObserver {
    public static final Long STATUS_AVAILABLE = 1L;
    Grid<Accommodation>  grid = new Grid<>();
    UserNavBar userNavBar = new UserNavBar();
    AccommodationService accommodationService;
    RoomService roomService;
    public static long accommodationId;

    public HomeView(AccommodationService accommodationService, RoomService roomService){
        this.accommodationService = accommodationService;
        this.roomService = roomService;
        addClassName("home-view");

        DatePicker.DatePickerI18n singleFormat = new DatePicker.DatePickerI18n();
        singleFormat.setDateFormat("dd-MM-yyyy");

        DatePicker checkinPicker = new DatePicker("Check-in:");
        checkinPicker.setI18n(singleFormat);

        DatePicker checkoutPicker = new DatePicker("Check-out:");
        checkoutPicker.setI18n(singleFormat);

        HorizontalLayout pickersLayout = new HorizontalLayout();
        pickersLayout.add(checkinPicker, checkoutPicker);

//        long diffdays = ChronoUnit.DAYS.between(checkinPicker.getValue(),checkoutPicker.getValue());

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);
        grid.addComponentColumn( accommodation -> createCard(accommodation));
        grid.setAllRowsVisible(true);

        addToNavbar(userNavBar);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        verticalLayout.add(pickersLayout,grid);

        setContent(verticalLayout);

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
        card.addClickListener(event -> {
            accommodationId = accommodation.getId();
            navigate();
        });
        return card;

    }

    private void navigate() {
        UI.getCurrent().getPage().setLocation("/accommodationPage");
        System.out.println("Accommodation have rooms? :" + roomService.existRoomByAccommodationId(accommodationId));


    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {

        List<Accommodation> accommodations = new ArrayList<>();
        accommodations.addAll(accommodationService.getAccommodationByHaveAvailableRooms());

        grid.setItems(accommodations);
    }

}
