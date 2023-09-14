package com.BookingApp.Security;

import com.BookingApp.Data.Entity.Role;
import com.BookingApp.Data.Entity.Roles;
import com.BookingApp.Data.Entity.User;
import com.BookingApp.Data.Repository.RoleRepository;
import com.BookingApp.Data.Repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private RoleRepository roleRepository;

    public CustomUserDetailsService(UserRepository userRepository,RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow( ()-> new UsernameNotFoundException("Username not found with this email: " + email));

        Set<GrantedAuthority> authorities = user
                .getRole()
                .stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
    }

    public void registerUser(String givenName, String surName,String email, String country, String city, String adress, String postalCode, String phone, String password) {
        userRepository.save(new User(givenName, surName, email, country, city, adress, postalCode, phone, SecurityConfig.passwordEncoder().encode(password), Collections.singleton(roleRepository.findByName("ROLES_USER"))));
    }
}
