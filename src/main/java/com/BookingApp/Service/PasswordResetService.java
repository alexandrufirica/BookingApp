package com.BookingApp.Service;

import com.BookingApp.Data.Entity.Accommodation;
import com.BookingApp.Data.Entity.PasswordResetToken;
import com.BookingApp.Data.Entity.User;
import com.BookingApp.Data.Repository.AccommodationRepository;
import com.BookingApp.Data.Repository.PasswordResetTokenRepository;
import com.BookingApp.Data.Repository.UserRepository;
import com.BookingApp.Security.Email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PasswordResetService {

    private UserRepository userRepository;
    private AccommodationRepository accommodationRepository;
    private PasswordResetTokenRepository tokenRepository;
    private PasswordEncoder passwordEncoder;
    private EmailService emailService;

    @Autowired
    public PasswordResetService(UserRepository userRepository,
                                AccommodationRepository accommodationRepository,
                                PasswordResetTokenRepository passwordResetTokenRepository,
                                PasswordEncoder passwordEncoder,
                                EmailService emailService) {
        this.userRepository = userRepository;
        this.accommodationRepository = accommodationRepository;
        this.tokenRepository = passwordResetTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public void generatePasswordResetToken(String email){
        User user = userRepository.findByEmail(email);
        Accommodation accommodation = accommodationRepository.findByEmail(email);

        if(user != null || accommodation != null){
            String token = UUID.randomUUID().toString();
            PasswordResetToken resetToken = new PasswordResetToken(token,user,accommodation );
            tokenRepository.save(resetToken);
            sendResetEmail(email,token);
        }

    }

    public boolean isValidToken(String token){
        PasswordResetToken resetToken = tokenRepository.findByToken(token);
        return resetToken != null && !resetToken.isExpired();
    }

    public void resetPassword(String token, String newPassword){
        PasswordResetToken resetToken = tokenRepository.findByToken(token);
        if(resetToken != null && !resetToken.isExpired()){
            if(resetToken.getUser() != null){
                User user = resetToken.getUser();
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
            } else if (resetToken.getAccommodation() != null) {
                Accommodation accommodation =resetToken.getAccommodation();
                accommodation.setPassword(passwordEncoder.encode(newPassword));
                accommodationRepository.save(accommodation);
            }
            resetToken.setExpired(true);
            tokenRepository.save(resetToken);
        }
    }

    private void sendResetEmail(String email, String token) {
        emailService.sendForgotPasswordEmail(email,token);
    }
}
