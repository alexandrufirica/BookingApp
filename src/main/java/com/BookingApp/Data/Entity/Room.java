package com.BookingApp.Data.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class Room extends AbstractEntity{

    @ManyToOne
//    @JoinColumn(name= "accommodation_id")
    @NotNull
    @JsonIgnoreProperties({"rooms"})
    private Accommodation accommodation;
    @NotNull
    @ManyToOne
    private Status status;
    @NotEmpty
    private String roomType;
    @NotEmpty
    private int numberOfRooms;
    @NotEmpty
    private  String roomDescription;
    @NotEmpty
    private int capacity;
    @NotEmpty
    private double pricePerNight;
    @NotEmpty
    private boolean availablility;

    public Room(){

    }

    public Room (String roomType, int numberOfRooms, String roomDescription, int capacity, double pricePerNight,boolean availablility){
        this.roomType = roomType;
        this.numberOfRooms = numberOfRooms;
        this.roomDescription = roomDescription;
        this.capacity = capacity;
        this.pricePerNight = pricePerNight;
        this.availablility = availablility;
    }
//    public Long getId() {
//        return id;
//    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isAvailablility() {
        return availablility;
    }

    public void setAvailablility(boolean availablility) {
        this.availablility = availablility;
    }



}
