package com.BookingApp.Security;

import com.BookingApp.Data.Repository.UserRepository;
import com.BookingApp.Views.Login.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {
    UserRepository userRepository;

    public SecurityConfig(UserRepository userRepository){
        this.userRepository =userRepository;

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

    //Just for simplicity- this should be secured.
    @Bean
    public UserDetailsManager userDetailsManager(){

        UserDetails user =
                User.withUsername("user")
                        .password("{noop}user")
                        .roles("USER")
                        .build();

        UserDetails manager =
                User.withUsername("manager")
                        .password("{noop}manager")
                        .roles("MANAGER")
                        .build();

        UserDetails admin =
                User.withUsername("admin")
                        .password("{noop}admin")
                        .roles("ADMIN")
                        .build();
        return new InMemoryUserDetailsManager(user, manager, admin);
    }


}
