package com.BookingApp.Data.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Entity
@Component
public class Reservation {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "idgenerator")
    @SequenceGenerator(
            name = "idgenerator",
            allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name= "accommodation_id")
    @NotNull
    @JsonIgnoreProperties({"reservations"})
    private Accommodation accommodation;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String reservationName;
    private String roomReserved;

    public Reservation(){

    }
    public Reservation(Accommodation accommodation, LocalDate checkIn, LocalDate checkOut, String reservationName, String roomReserved){
        this.accommodation = accommodation;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.reservationName = reservationName;
        this.roomReserved = roomReserved;

    }

    public Long getId() {
        return id;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public String getReservationName() {
        return reservationName;
    }

    public void setReservationName(String reservationName) {
        this.reservationName = reservationName;
    }

    public String getRoomReserved() {
        return roomReserved;
    }

    public void setRoomReserved(String roomReserved) {
        this.roomReserved = roomReserved;
    }
}
