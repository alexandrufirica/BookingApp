package com.BookingApp.Data.Entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.annotations.Formula;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Component
@Table(name = "accommodations")
public class Accommodation{

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "idgenerator")
    @SequenceGenerator(
            name = "idgenerator",
            allocationSize = 1)
    private Long id;

    private String name;
    private String city;
    private String country;
    private String adress;
    private String postalCode;
    private String email;
    private String phoneNumber;
    private String password;
    private String activationCode;
    private boolean haveAvailableRooms;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "accommodation_roles",
            joinColumns = @JoinColumn(name = "name_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles;

    @OneToMany(mappedBy = "accommodation")
    @Nullable
    private List<Room> rooms = new LinkedList<>();

//    @Formula("(select count(c.id) from Room c where c.accommodation_id = id)")
//    private int roomsCount;

      public Accommodation(){

    }

    public Accommodation(String name, String country, String city, String adress, String postalCode, String phoneNumber, String email, String password, Set<Role> role, boolean haveAvailableRooms){
        this.name = name;
        this.city = city;
        this.country = country;
        this.adress = adress;
        this.postalCode = postalCode;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.activationCode = RandomStringUtils.randomAlphanumeric(32);
        this.roles = role;
        this.haveAvailableRooms = haveAvailableRooms;
    }



    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms( List<Room> rooms) {
        this.rooms = rooms;
    }

//    public int getRoomsCount() {
//        return roomsCount;
//    }


    public boolean isHaveAvailableRooms() {
        return haveAvailableRooms;
    }

    public void setHaveAvailableRooms(boolean haveAvailableRooms) {
        this.haveAvailableRooms = haveAvailableRooms;
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

