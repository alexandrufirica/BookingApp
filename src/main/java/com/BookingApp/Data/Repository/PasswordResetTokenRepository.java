package com.BookingApp.Data.Repository;

import com.BookingApp.Data.Entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken,Long> {

    PasswordResetToken findByToken(String token);
}
