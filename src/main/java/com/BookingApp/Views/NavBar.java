package com.BookingApp.Views;

import com.BookingApp.BookingAppController;
import com.BookingApp.Security.SecurityService;
import com.BookingApp.Security.SecurityUtils;
import com.BookingApp.Views.Manager.AddRoom;
import com.BookingApp.Views.Manager.ReservationList;
import com.BookingApp.Views.Manager.RoomList;
import com.BookingApp.Views.User.MainView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;
import org.springframework.stereotype.Component;



public class NavBar extends AppLayout {

    private SecurityUtils securityUtils;
    public NavBar() {
        securityUtils = new SecurityUtils();
        H1 title = new H1("BookingApp");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("left", "var(--lumo-space-l)").set("margin", "0")
                .set("position", "absolute");

        Tabs tabs = getTabs();

        Button logout = new Button("Log out");

        logout.addClickListener( e -> securityUtils.logout());
        addToNavbar(title, tabs , logout);
    }

    private Tabs getTabs() {
        Tabs tabs = new Tabs();
        tabs.getStyle().set("margin", "auto");
        tabs.add(createTab("Add Room", AddRoom.class),
                createTab("Reservations", ReservationList.class),
                createTab("Room List", RoomList.class),
                createTab("Main View", MainView.class));
        return tabs;
    }

    private Tab createTab(String viewName, Class cls) {
        RouterLink link = new RouterLink();
        link.add(viewName);
        link.setRoute(cls);
        link.setTabIndex(-1);

        return new Tab(link);
    }

}
