package com.BookingApp.Data.Entity;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Entity
@Component
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @OneToOne(targetEntity = Accommodation.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = true, name = "accommodation_id")
    private Accommodation accommodation;
    private String token;
    private LocalDateTime expiryDate;
    private boolean expired;

    public PasswordResetToken() {
    }

    public PasswordResetToken(String token, User user, Accommodation accommodation) {
        this.token = token;
        this.user = user;
        this.accommodation = accommodation;
        this.expiryDate = calculateExpiryDate();
        this.expired = false;
    }

    private LocalDateTime calculateExpiryDate() {
        return LocalDateTime.now().plusHours(1);
    }

    public boolean isExpired() {
        return this.expired || LocalDateTime.now().isAfter(this.expiryDate);
    }

    public void setExpired(boolean expired){
        this.expired=expired;
    }

    public long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

}
