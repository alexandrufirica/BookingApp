package com.BookingApp.Security;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.VaadinSession;
import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

public class SecurityUtils {
    private static final String LOGOUT_SUCCESS_URL = "/";

    public static boolean isAuthenticated() {
        VaadinServletRequest request = VaadinServletRequest.getCurrent();
        return request != null && request.getUserPrincipal() != null;
    }

    public static boolean authenticate(String email, String password) {
        VaadinServletRequest request = VaadinServletRequest.getCurrent();
        if (request == null) {
            // This is in a background thread and we can't access the request to
            // log in the user
            return false;
        }
        try {
            System.out.println("Attempting to log in with email: " + email);
            request.login(email, password);
            System.out.println("Login successful!");
            return true;
        } catch (ServletException e) {
            // login exception handle code omitted
            e.printStackTrace();
            System.out.println("Login failed: " + e.getMessage());
            return false;
        }
    }

    @Autowired
    public static void logout(UI ui) {
        VaadinSession.getCurrent().getSession().invalidate();
//        UI.getCurrent().getSession().close();
        ui.getPage().setLocation(LOGOUT_SUCCESS_URL);
        SecurityContextLogoutHandler logoutHandler =new SecurityContextLogoutHandler();
        logoutHandler.logout(VaadinServletRequest.getCurrent().getHttpServletRequest(),null, null);

    }
}
