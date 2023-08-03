package com.BookingApp.Views;

import com.BookingApp.BookingAppController;
import com.BookingApp.Security.SecurityService;
import com.BookingApp.Views.Manager.AddRoom;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;


public class NavBar extends AppLayout {

    private SecurityService securityService;
    public NavBar(SecurityService securityService) {
        this.securityService = securityService;
        H1 title = new H1("BookingApp");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("left", "var(--lumo-space-l)").set("margin", "0")
                .set("position", "absolute");

        Tabs tabs = getTabs();

        Button logout = new Button("Log out");

        logout.addClickListener( e -> securityService.logout());

        addToNavbar(title, tabs , logout);
    }

    private Tabs getTabs() {
        Tabs tabs = new Tabs();
        tabs.getStyle().set("margin", "auto");
        tabs.add(createTab("Add Room", AddRoom.class),
                createTab("Reservations", BookingAppController.class),
                createTab("Room List", BookingAppController.class));
        return tabs;
    }

    private Tab createTab(String viewName, Class cls) {
        RouterLink link = new RouterLink();
        link.add(viewName);
        link.setRoute(cls);
        link.setTabIndex(-1);

        return new Tab(link);
    }

    private Tab createTab(String viewName) {
        RouterLink link = new RouterLink();
        link.add(viewName);
        link.setTabIndex(-1);

        return new Tab(link);
    }
}
