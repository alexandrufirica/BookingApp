package com.BookingApp.Data.Entity;

import jakarta.persistence.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;
import org.apache.commons.codec.digest.DigestUtils;


import java.util.Random;

@Entity
@Component
@Table(name = "users")
public class User extends AbstractEntity {

    private String email;
    private String givenName;
    private String surName;
    private String country;
    private String city;
    private String adress;
    private String postalCode;
    private String phoneNumber;
    private String passwordSalt;
    private String passwordHash;
    private Role role;
    private String activationCode;
    private boolean active;

    public User(){

    }

    public User(String givenName, String surName, String email, String country, String city, String adress, String postalCode, String phoneNumber, String password, Role role){
        this.givenName = givenName;
        this.surName = surName;
        this.email = email;
        this.country = country;
        this.city = city;
        this.adress = adress;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.passwordSalt = RandomStringUtils.random(32);
        this.passwordHash = DigestUtils.sha1Hex(password + passwordSalt);
        this.activationCode = RandomStringUtils.randomAlphanumeric(32);
        this.role = role;

    }

    public boolean checkPassword(String password){
        return DigestUtils.sha1Hex(password + passwordSalt).equals(passwordHash);
    }

    public String getPassword(String password){
        if(DigestUtils.sha1Hex(password + passwordSalt).equals(passwordHash)){
            return password;
        }
        return null;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String password) {
        this.passwordSalt = password;
    }

    public String getPasswordHash() { return passwordHash; }

    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public Role getRole() { return role; }

    public void setRole(Role role) { this.role = role; }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
