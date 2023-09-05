package com.BookingApp.Security;

import com.BookingApp.BookingAppController;
import com.BookingApp.Data.Entity.Role;
import com.BookingApp.Data.Entity.User;
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
//    private final MailSender mailSender;

    public AuthService(UserRepository userRepository){
        this.userRepository = userRepository;
//        this.mailSender = mailSender;
    }

    public void authenticate (String email,String password) throws AuthException{
        User user = userRepository.getUserByEmail(email);
        if(user != null && user.checkPassword(password) && user.isActive()){
            VaadinSession.getCurrent().setAttribute(User.class,user);
            createRoutes(user.getRole());
        }else{
            throw new AuthException();
        }
    }

    private void createRoutes(Role role) {
        getAuthorizedRoutes(role).stream()
                .forEach(route ->
                        RouteConfiguration.forSessionScope().setRoute(
                        route.route, BookingAppController.class
                        ));
    }

    public List<AuthorizedRoute> getAuthorizedRoutes(Role role) {
        ArrayList<AuthorizedRoute> routes = new ArrayList<AuthorizedRoute>();

        if (role.equals(Role.USER)) {
            routes.add(new AuthorizedRoute("MyApp", "App", BookingAppController.class));

        } else if (role.equals(Role.MANAGER)) {
            routes.add(new AuthorizedRoute("roomlist", "RoomList", RoomList.class));
            routes.add(new AuthorizedRoute("addRoom", "AddRoom", AddRoom.class));

        } else if (role.equals(Role.ADMIN)) {
            routes.add(new AuthorizedRoute("roomlist", "RoomList", RoomList.class));
            routes.add(new AuthorizedRoute("addRoom", "AddRoom", AddRoom.class));
            routes.add(new AuthorizedRoute("MyApp", "App", BookingAppController.class));
        }
        return routes;
    }

    public void register(String givenName, String surName,String email, String country, String city, String adress, String postalCode, String phone, String password) {
         userRepository.save(new User(givenName, surName, email, country, city, adress, postalCode, phone, password, Role.USER));
//        String text = "http://localhost:8080/activate?code=" + user.getActivationCode();
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("noreply@example.com");
//        message.setSubject("Confirmation email");
//        message.setText(text);
//        message.setTo(email);
//        mailSender.send(message);
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
