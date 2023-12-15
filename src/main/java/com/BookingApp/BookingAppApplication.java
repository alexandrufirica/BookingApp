package com.BookingApp;


import com.BookingApp.Views.Manager.RoomList;
import com.BookingApp.Views.User.HomeView;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootApplication
@PWA(
		name = " Booking Application",
		shortName = "BookingApp",
		offlinePath = "offline.html",
		offlineResources = {"./images/offline.png"}
)
@Theme("my-theme")
public class BookingAppApplication extends SpringBootServletInitializer implements AppShellConfigurator {

	public static void main(String[] args) {
		SpringApplication.run(BookingAppApplication.class, args);
	}
}
