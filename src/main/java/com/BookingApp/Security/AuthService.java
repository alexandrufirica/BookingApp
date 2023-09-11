package com.BookingApp.Security;

import com.BookingApp.BookingAppController;
import com.BookingApp.Data.Entity.Accommodation;
import com.BookingApp.Data.Entity.Role;
import com.BookingApp.Data.Entity.User;
import com.BookingApp.Data.Repository.AccommodationRepository;
import com.BookingApp.Data.Repository.UserRepository;
import com.BookingApp.Views.Manager.AddRoom;
import com.BookingApp.Views.Manager.RoomList;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {

    public record AuthorizedRoute (String route, String name, Class<? extends Component> view) {

    }

    public class AuthException extends Exception{

    }

    private final UserRepository userRepository;
    private final AccommodationRepository accommodationRepository;
//    private final MailSender mailSender;

    public AuthService(UserRepository userRepository, AccommodationRepository accommodationRepository){
        this.userRepository = userRepository;
        this.accommodationRepository = accommodationRepository;
//        this.mailSender = mailSender;
    }

    public void authenticate (String email,String password) throws AuthException{
        User user = userRepository.getUserByEmail(email);
        if(user != null && user.checkPassword(password) && user.isActive()){
            VaadinSession.getCurrent().setAttribute(User.class,user);
        }else{
            throw new AuthException();
        }
    }





    public void registerUser(String givenName, String surName,String email, String country, String city, String adress, String postalCode, String phone, String password, Role role) {
         userRepository.save(new User(givenName, surName, email, country, city, adress, postalCode, phone, password, role));
//        String text = "http://localhost:8080/activate?code=" + user.getActivationCode();
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("noreply@example.com");
//        message.setSubject("Confirmation email");
//        message.setText(text);
//        message.setTo(email);
//        mailSender.send(message);
    }

    public void registerAccommodation(String name, String country, String city, String adress, String postalCode, String phoneNumber, String email, String password, Role role) {
        accommodationRepository.save(new Accommodation(name, country, city, adress, postalCode, phoneNumber, email, password, role));
    }

    public void activate(String activationCode) throws AuthException {
        User user = userRepository.getUserByActivationCode(activationCode);
        if (user != null) {
            user.setActive(true);
            userRepository.save(user);
        } else {
            throw new AuthException();
        }
    }
}
