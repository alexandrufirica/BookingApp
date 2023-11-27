package com.BookingApp.Service;

import com.BookingApp.Data.Entity.User;
import com.BookingApp.Data.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
    public void saveUser(User user) {
        userRepository.save(user);
    }

    public boolean existsByEmail(String email) {
        return  userRepository.existsByEmail(email);
    }

}
