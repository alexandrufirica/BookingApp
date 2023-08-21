package com.BookingApp.Service;

import com.BookingApp.Data.Entity.User;

import java.util.List;

public interface IUserService {
    List <User> getAllUsers();

    void createUser(User user);

}
