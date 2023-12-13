package com.BookingApp.Views.Login;

import com.BookingApp.Service.PasswordResetService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle(value = "Reset Password")
@Route(value = "reset-password")
public class PasswordResetView extends VerticalLayout implements BeforeEnterObserver {

    private PasswordResetService passwordResetService;
    private PasswordField passwordField;
    private String token;

    public PasswordResetView(){

        Button submitButton = new Button("Submit", e ->{
            passwordResetService.resetPassword(token ,passwordField.getValue());
        });

        add(
                passwordField,
                submitButton);

    }
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        token  = event.getLocation().getQueryParameters().getParameters().get("token").get(0);
        if( !passwordResetService.isValidToken(token)){
            Notification.show("Invalid or expired token. Please request a new one.");
            event.forwardTo(LoginView.class);
        }
    }
}
