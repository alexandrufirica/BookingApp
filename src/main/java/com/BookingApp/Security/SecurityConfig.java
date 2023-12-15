package com.BookingApp.Security;

import com.BookingApp.Views.Login.LoginView;
import com.BookingApp.Views.Manager.RoomList;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{

        return configuration.getAuthenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests().requestMatchers(new AntPathRequestMatcher("/public/**"))
                .permitAll();
        super.configure(http);
        setLoginView(http, LoginView.class);


    }

    @Override
    protected void configure(WebSecurity web) throws Exception {
        //web.ignoring().requestMatchers("/images/**");
        super.configure(web);
    }


//    For simplicity- in development mode can use this.
//    @Bean
//    public UserDetailsManager userDetailsManager(){
//
//        UserDetails user =
//                User.withUsername("user")
//                        .password("{noop}user")
//                        .roles("USER")
//                        .build();
//
//        UserDetails manager =
//                User.withUsername("manager")
//                        .password("{noop}manager")
//                        .roles("MANAGER")
//                        .build();
//
//        UserDetails admin =
//                User.withUsername("admin")
//                        .password("{noop}admin")
//                        .roles("ADMIN")
//                        .build();
//        return new InMemoryUserDetailsManager(user, manager, admin);
//    }


}
