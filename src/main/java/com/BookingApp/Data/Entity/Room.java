package com.BookingApp.Data.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Entity
@Component
public class Room extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name= "accommodation_id")
    @NotNull
    @JsonIgnoreProperties({"rooms"})
    private Accommodation accommodation;
    @NotNull
    @JoinColumn(name = "status_id")
    @ManyToOne
    private Status status;
    @NotNull
    private String roomType;
    @NotNull
    private int numberOfRooms;
    private  String roomDescription;
    @NotNull
    private int capacity;
    @NotNull
    private double pricePerNight;
    @NotNull
    private boolean availability;

    public Room(){

    }
    public Room (String roomType, int numberOfRooms, String roomDescription, int capacity, double pricePerNight, boolean availability, Accommodation accommodation, Status status){
        this.roomType = roomType;
        this.numberOfRooms = numberOfRooms;
        this.roomDescription = roomDescription;
        this.capacity = capacity;
        this.pricePerNight = pricePerNight;
        this.availability = availability;
        this.accommodation = accommodation;
        this.status = status;

    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public Status getStatus() {
        return status;
    }

    public String getStatusName(){
        return status.getName();
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

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }



}
