package com.BookingApp.Data.Repository;

import com.BookingApp.Data.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    User getUserByEmail(String email);

    Optional<User> findByEmail(String email);

    User getUserByActivationCode (String activationCode);
}
