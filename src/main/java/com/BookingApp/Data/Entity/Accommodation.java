package com.BookingApp.Data.Entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.Formula;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Entity
//@Component
//@Table(name = "accomodation")
public class Accommodation extends AbstractEntity {

//    @Id
//    @SequenceGenerator(
//            name = "accomodation_sequence",
//            allocationSize = 1
//    )
//    @GeneratedValue(
//            generator = "accomodation_sequence",
//            strategy = GenerationType.SEQUENCE
//    )
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String city;
    @NotBlank
    private String country;
    @NotBlank
    private String adress;
    @NotBlank
    private String postalCode;
    @NotBlank
    private String email;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String password;
    @NotBlank
    private String role;
    @OneToMany(mappedBy = "accommodation")
    @Nullable
    private List<Room> rooms = new LinkedList<>();
    @Formula("(select count(c.id) from Room c where c.accommodation_id = id)")
    private int roomsCount;

      public Accommodation(){

    }

    public Accommodation(String name, String city, String country,String adress,String email, String phoneNumber, String password){
        this.name = name;
        this.city = city;
        this.country = country;
        this.adress = adress;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }



    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms( List<Room> rooms) {
        this.rooms = rooms;
    }

    public int getRoomsCount() {
        return roomsCount;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public Long getId() {
        return id;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}

