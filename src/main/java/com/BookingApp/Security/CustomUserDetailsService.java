package com.BookingApp.Security;

import com.BookingApp.Data.Entity.Accommodation;
import com.BookingApp.Data.Entity.User;
import com.BookingApp.Data.Repository.AccommodationRepository;
import com.BookingApp.Data.Repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AccommodationRepository accommodationRepository;
    private User user;
    private Accommodation accommodation;

    public CustomUserDetailsService(UserRepository userRepository, AccommodationRepository accommodationRepository) {
        this.userRepository = userRepository;
        this.accommodationRepository = accommodationRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (userRepository.existsByEmail(email)) {

            User user = userRepository.findByEmail(email);

            Set<GrantedAuthority> authorities = user
                    .getRoles()
                    .stream()
                    .map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());

            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                    user.getEmail(),user.getPassword(),authorities);

            //Set the user details in the authentication context
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,null,userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            return userDetails;

        } else if (accommodationRepository.existsByEmail(email)) {

            Accommodation accommodation = accommodationRepository.findByEmail(email);

            Set<GrantedAuthority> authorities = accommodation
                    .getRoles()
                    .stream()
                    .map((role -> new SimpleGrantedAuthority(role.getName()))).collect(Collectors.toSet());

            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                    accommodation.getEmail(),accommodation.getPassword(),authorities);

            //Set the user details in the authentication context
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,null,userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            return userDetails;
        }else {
            return null;
        }
    }

    public User getUser() {
        return user;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }


}
