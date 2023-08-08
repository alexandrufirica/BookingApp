package com.BookingApp.Service;

import com.BookingApp.Data.User;
import com.BookingApp.Data.UserRepository;

import java.util.List;

public class UserService implements IUserService{

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }
}
