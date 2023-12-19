package com.BookingApp.Security;

import com.BookingApp.Views.Login.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {
    private static final String LOGOUT_SUCCESS_URL = "/";

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
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

        setLoginView(http, LoginView.class, LOGOUT_SUCCESS_URL);
        String privateSecretKeyToChange = "JKJDSKLDJdJSisdjsdfjmkdjdfkljkJKLjlk";
        http.rememberMe()
                .key(privateSecretKeyToChange).tokenValiditySeconds(7200).userDetailsService(this.userDetailsService);
        http.logout()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "remember-me")
                .logoutSuccessUrl(LOGOUT_SUCCESS_URL);
        http.sessionManagement()
                .maximumSessions(1)
                .sessionRegistry(sessionRegistry())
                .and()
                .sessionFixation()
                .none();
    }

    @Override
    protected void configure(WebSecurity web) throws Exception {
        //web.ignoring().requestMatchers("/images/**");
        super.configure(web);
    }

    @Bean
    public SessionRegistry sessionRegistry(){
        return  new SessionRegistryImpl();
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
