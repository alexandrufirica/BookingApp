package com.BookingApp.Views.User;

import com.BookingApp.Security.SecurityUtils;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;


public class UserNavBar extends AppLayout {

    public UserNavBar(){

        DrawerToggle drawerToggle = new DrawerToggle();

        H1 title = new H1("BookingApp");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        Tabs navBarTabs = getNavBarTabs();
        Tabs toggleTabs = getToggleTabs();

        Button logout = new Button("Log out");
        logout.addClickListener( e -> SecurityUtils.logout(UI.getCurrent()));

        setDrawerOpened(false);

        addToDrawer(toggleTabs);
        addToNavbar(drawerToggle, title, navBarTabs, logout);

    }

    private Tabs getNavBarTabs() {
        Tabs tabs = new Tabs();
        tabs.setAutoselect(false);
        tabs.getStyle().set("margin", "auto");
        tabs.add(
                createTab("Home View", HomeView.class));
        return tabs;
    }

    private Tabs getToggleTabs() {
        Tabs tabs = new Tabs();
        tabs.add(createTab(VaadinIcon.HOME, "Home", HomeView.class),
                createTab(VaadinIcon.FILE_PROCESS, "Account Settings", UserSettings.class));
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        return tabs;
    }

    private Tab createTab(String viewName, Class cls) {
        RouterLink link = new RouterLink();
        link.add(viewName);
        link.setRoute(cls);
        link.setTabIndex(-1);

        return new Tab(link);
    }

    private Tab createTab(VaadinIcon viewIcon, String viewName, Class cls) {
        Icon icon = viewIcon.create();
        icon.getStyle().set("box-sizing", "border-box")
                .set("margin-inline-end", "var(--lumo-space-m)")
                .set("margin-inline-start", "var(--lumo-space-xs)")
                .set("padding", "var(--lumo-space-xs)");

        RouterLink link = new RouterLink();
        link.add(icon, new Span(viewName));
        link.setRoute(cls);
        link.setTabIndex(-1);

        return new Tab(link);
    }
}
