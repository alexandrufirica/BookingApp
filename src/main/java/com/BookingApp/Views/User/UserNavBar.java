package com.BookingApp.Views.User;

import com.BookingApp.Security.SecurityUtils;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;


public class UserNavBar extends AppLayout {

    private final SecurityUtils securityUtils;

    private final Tabs tabs;

    public UserNavBar(){
        securityUtils = new SecurityUtils();

        H1 title = new H1("BookingApp");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("left", "var(--lumo-space-l)").set("margin", "0")
                .set("position", "absolute");


        tabs = getTabs();

        Button logout = new Button("Log out");

        logout.addClickListener( e -> securityUtils.logout());



        addToNavbar( title, tabs, logout);
    }

    private Tabs getTabs() {
        Tabs tabs = new Tabs();
        tabs.setAutoselect(false);
        tabs.getStyle().set("margin", "auto");
        tabs.add(
                createTab("Main View", HomeView.class));
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
