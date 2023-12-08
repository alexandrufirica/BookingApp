package com.BookingApp.Security;

import com.BookingApp.Data.Entity.Accommodation;
import com.BookingApp.Data.Entity.User;
import com.BookingApp.Data.Repository.AccommodationRepository;
import com.BookingApp.Data.Repository.UserRepository;
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

            user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Username not found with this email: " + email));

            Set<GrantedAuthority> authorities = user
                    .getRoles()
                    .stream()
                    .map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);

        } else if (accommodationRepository.existsByEmail(email)) {

            accommodation = accommodationRepository.findByEmail(email)
                    .orElseThrow( () -> new UsernameNotFoundException("Username not found with this email: " + email));

            Set<GrantedAuthority> authorities = accommodation
                    .getRoles()
                    .stream()
                    .map((role -> new SimpleGrantedAuthority(role.getName()))).collect(Collectors.toSet());
            return new org.springframework.security.core.userdetails.User(accommodation.getEmail(),accommodation.getPassword(),authorities);

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
