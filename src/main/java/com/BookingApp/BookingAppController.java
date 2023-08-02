package com.BookingApp;


import com.BookingApp.SecurityConfig.SecurityService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@PageTitle("MY App")
@Route(value = "/myapp")
public class BookingAppController extends VerticalLayout {

    private SecurityService securityService = new SecurityService();
    public  BookingAppController(){
        NavBar navBar = new NavBar(securityService);

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
