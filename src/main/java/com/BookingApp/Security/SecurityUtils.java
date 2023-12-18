package com.BookingApp.Security;

import com.vaadin.flow.server.VaadinServletRequest;
import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    @Autowired
    public SecurityUtils(){
    }

    public static boolean isAuthenticated() {
        VaadinServletRequest request = VaadinServletRequest.getCurrent();
        return request != null && request.getUserPrincipal() != null;
    }

    @Autowired
    public static void logout() {
        try {
            VaadinServletRequest.getCurrent().getHttpServletRequest().logout();
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
