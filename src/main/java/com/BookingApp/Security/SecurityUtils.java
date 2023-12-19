package com.BookingApp.Security;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.VaadinSession;
import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    private static final String LOGOUT_SUCCESS_URL = "/";

    @Autowired
    public SecurityUtils(){
    }

    public static boolean isAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }

    @Autowired
    public static void logout(UI ui) {
        try {
            VaadinSession.getCurrent().getSession().invalidate();
            ui.getPage().setLocation(LOGOUT_SUCCESS_URL);
            VaadinServletRequest.getCurrent().getHttpServletRequest().logout();
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
