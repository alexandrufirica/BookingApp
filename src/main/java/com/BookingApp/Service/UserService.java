package com.BookingApp.Service;

import com.BookingApp.Data.Entity.User;
import com.BookingApp.Data.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService{

    private final UserRepository userRepository;
    private final User user;

    public UserService(UserRepository userRepository, User user){
        this.userRepository = userRepository;
        this.user = user;
    }
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void getUser(String emailAdress) {
        Optional<User> userOptional = userRepository.getUserByEmailAdress(emailAdress);
        if(userOptional.isPresent()){

        }

    }
}
