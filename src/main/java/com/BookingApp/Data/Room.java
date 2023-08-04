package com.BookingApp.Data;

import jakarta.persistence.*;

@Entity
@Table(name = "rooms")
public class Room {



    @Id
    @SequenceGenerator(
            name = "room_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "room_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;
    private String roomType;
    private int numberOfRooms;
    private  String roomDescription;
    private int capacity;
    private double pricePerNight;
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
    public Long getId() {
        return id;
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
