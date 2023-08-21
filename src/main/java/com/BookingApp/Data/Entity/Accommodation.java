package com.BookingApp.Data.Entity;

import jakarta.persistence.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Entity
@Component
@Table(name = "accomodation")
public class Accommodation {

    @Id
    @SequenceGenerator(
            name = "accomodation_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "accomodation_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;
    private String name;
    private String city;
    private String country;
    private String adress;
    private String postalCode;
    private String email;
    private String phoneNumber;
    private String password;
    private String role;

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

