package com.BookingApp.Service;

import com.BookingApp.Data.User;

import java.util.List;

public interface IUserService {
    List <User> getAllUsers();

    void createUser(User user);

}
