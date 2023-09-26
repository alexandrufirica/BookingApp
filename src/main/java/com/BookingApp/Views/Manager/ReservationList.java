package com.BookingApp.Views.Manager;

import com.BookingApp.Data.Entity.Accommodation;
import com.BookingApp.Data.Entity.Reservation;
import com.BookingApp.Data.Entity.Room;
import com.BookingApp.Security.CustomUserDetailsService;
import com.BookingApp.Service.ReservationService;
import com.BookingApp.Views.NavBar;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@PageTitle(value = "Reservation List")
@Route(value = "/reservationlist")
@RolesAllowed({"ADMIN","MANAGER"})
public class ReservationList extends VerticalLayout {
    Grid<Reservation> grid = new Grid<>(Reservation.class);
    NavBar navBar = new NavBar();
    private final ReservationService reservationService;
    private Accommodation accommodation;

    public ReservationList(ReservationService reservationService){
        this.reservationService = reservationService;
        this.accommodation = CustomUserDetailsService.accommodation;

        accommodation.setId(CustomUserDetailsService.accommodation.getId());

        addClassName("reservationList-view");

        H1 label = new H1(accommodation.getName() + " Reservation List");

        configureGrid();

        add(
                navBar,
                label,
                getContent()
        );
        updateList();

        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    }

    private void configureGrid() {
        grid.addClassName("reservation-grid");
        grid.setColumns("roomReserved","checkIn","checkOut","reservationName");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
//        grid.asSingleSelect().addValueChangeListener(event -> editRoom( event.getValue()));
    }

    private HorizontalLayout getContent(){
        HorizontalLayout content = new HorizontalLayout(grid);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }

    private void updateList(){
        grid.setItems(reservationService.findReservationByAccommodation(accommodation.getId()));
    }
}
