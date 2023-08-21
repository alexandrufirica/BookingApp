package com.BookingApp.Data.Repository;

import com.BookingApp.Data.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

}
