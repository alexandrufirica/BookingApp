package com.BookingApp.Data.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
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