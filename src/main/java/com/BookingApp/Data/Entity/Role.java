package com.BookingApp.Data.Entity;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

@Entity
@Component
@Table(name = "roles")
public class Role extends AbstractEntity {


    @Column(length = 60)
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}