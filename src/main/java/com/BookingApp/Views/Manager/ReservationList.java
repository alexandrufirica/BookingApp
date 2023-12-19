package com.BookingApp.Views.Manager;

import com.BookingApp.Data.Entity.Accommodation;
import com.BookingApp.Data.Entity.Reservation;
import com.BookingApp.Security.CustomUserDetailsService;
import com.BookingApp.Service.AccommodationService;
import com.BookingApp.Service.ReservationService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import jakarta.annotation.security.RolesAllowed;

@PageTitle(value = "Reservation List")
@Route(value = "/reservationlist")
@RolesAllowed({"ADMIN","MANAGER"})
public class ReservationList extends VerticalLayout {
    Grid<Reservation> grid = new Grid<>(Reservation.class);
    ManagerNavBar navBar = new ManagerNavBar();
    private final ReservationService reservationService;
    private final Accommodation accommodation;
    private final AccommodationService accommodationService;

    public ReservationList(ReservationService reservationService, AccommodationService accommodationService){
        this.reservationService = reservationService;
        this.accommodationService = accommodationService;

        String accommodationEmail = (String) VaadinSession.getCurrent().getAttribute("userEmail");
        accommodation = accommodationService.getAccommodationbyEmail(accommodationEmail);

        addClassName("reservationList-view");

        H1 label = new H1(accommodation.getName() + " Reservation List");
        configureGrid();

        add(
                navBar,
                label,
                getContent()
        );
        updateList();

        setDefaultHorizontalComponentAlignment(Alignment.END);
    }

    private void configureGrid() {
        grid.addClassName("reservation-grid");
        grid.setColumns("roomReserved","checkIn","checkOut","reservationName");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.setAllRowsVisible(true);
    }

    private HorizontalLayout getContent(){
        HorizontalLayout content = new HorizontalLayout(grid);
        content.addClassName("content");
        content.setSizeFull();
        content.expand(grid);

        return content;
    }


    private void updateList(){
        grid.setItems(reservationService.findReservationByAccommodation(accommodation.getId()));
    }
}
