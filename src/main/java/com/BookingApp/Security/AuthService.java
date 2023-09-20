package com.BookingApp.Security;

import com.BookingApp.Data.Entity.Accommodation;
import com.BookingApp.Data.Entity.Roles;
import com.BookingApp.Data.Entity.User;
import com.BookingApp.Data.Repository.AccommodationRepository;
import com.BookingApp.Data.Repository.UserRepository;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.stereotype.Service;

//@Service
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
        if(user != null ){
            VaadinSession.getCurrent().setAttribute(User.class,user);
        }else{
            throw new AuthException();
        }
    }





//    public void registerUser(String givenName, String surName,String email, String country, String city, String adress, String postalCode, String phone, String password, Roles roles) {
//         userRepository.save(new User(givenName, surName, email, country, city, adress, postalCode, phone, password, roles));
//        String text = "http://localhost:8080/activate?code=" + user.getActivationCode();
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("noreply@example.com");
//        message.setSubject("Confirmation email");
//        message.setText(text);
//        message.setTo(email);
//        mailSender.send(message);
//    }


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
