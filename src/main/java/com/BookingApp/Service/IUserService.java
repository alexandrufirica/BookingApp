package com.BookingApp.Service;

import com.BookingApp.Data.Entity.User;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.List;

public interface IUserService {
    List <User> getAllUsers();

    void createUser(User user);


}
