package com.BookingApp.Data.Entity;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

@Entity
@Component
public class Status extends AbstractEntity {

    private String name;

    public Status(){

    }

    public Status (String name) { this.name = name; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
