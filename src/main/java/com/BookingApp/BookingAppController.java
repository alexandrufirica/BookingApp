package com.BookingApp;


import com.BookingApp.Security.SecurityService;
import com.BookingApp.Views.NavBar;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import jakarta.annotation.security.PermitAll;

@PageTitle("MyApp")
@Route(value = "/myapp")
@RouteAlias("")
@PermitAll

public class BookingAppController extends VerticalLayout {

    private SecurityService securityService = new SecurityService();
    public  BookingAppController(){
        setId("MyApp-view");
        NavBar navBar = new NavBar();

        addClassName("MyApp-view");
        add(navBar);
        hello();

    }

    public void hello (){
        TextField textArea = new TextField("My page");

        setMargin(true);
        add(textArea);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    }
}
