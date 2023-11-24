package com.BookingApp.Data.Repository;

import com.BookingApp.Data.Entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository <Status, Long> {

    Status getStatusById(long id);
}
