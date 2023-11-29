package com.BookingApp;


import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@PWA(
		name = " Booking Application",
		shortName = "BookingApp",
		offlinePath = "offline.html",
		offlineResources = {"./images/offline.png"}
)
@Theme("my-theme")
public class BookingAppApplication implements AppShellConfigurator {

	public static void main(String[] args) {
		SpringApplication.run(BookingAppApplication.class, args);
	}

}
