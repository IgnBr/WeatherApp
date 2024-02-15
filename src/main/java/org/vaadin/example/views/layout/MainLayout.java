package org.vaadin.example.views.layout;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import org.vaadin.example.views.favouriteLocations.FavouriteLocationsView;
import org.vaadin.example.views.locationSearch.LocationSearchView;

// TODO: Add css styling
@CssImport("./themes/my-theme/styles.css")
public class MainLayout extends AppLayout implements RouterLayout {

    private static final String APP_NAME = "Weather App";


    public MainLayout() {
        HorizontalLayout header = buildHeader();
        Tabs navBar = createMenu();

        addToNavbar(new VerticalLayout(header, navBar));
    }

    private HorizontalLayout buildHeader() {
        Span appName = new Span(APP_NAME);
        appName.addClassNames("text-white", "font-bold", "text-lg");
        Button logout = new Button("Log Out", VaadinIcon.SIGN_OUT.create(), this::logout);
        logout.addClassNames("text-white", "font-bold");

        HorizontalLayout header = new HorizontalLayout(appName, logout);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        header.setWidthFull();
        header.addClassNames("bg-blue-600", "p-4", "text-white");

        return header;
    }

    private void logout(ClickEvent<Button> buttonClickEvent) {
        UI.getCurrent().getPage().setLocation("login");
        VaadinSession.getCurrent().getSession().invalidate();
        VaadinSession.getCurrent().close();
    }

    private Tabs createMenu() {
        Tab homeTab = createTab("Search for location", VaadinIcon.SEARCH.create(), LocationSearchView.class);
        Tab favoritesTab = createTab("Favorites", VaadinIcon.HEART.create(), FavouriteLocationsView.class);

// TODO: Fix bug where incorrect tab is highlighted
        Tabs tabs = new Tabs(homeTab, favoritesTab);
        tabs.setAutoselect(false);
        tabs.setOrientation(Tabs.Orientation.HORIZONTAL);
        tabs.setFlexGrowForEnclosedTabs(1);
        tabs.setWidthFull();
        tabs.addClassNames("bg-gray-900", "text-white", "font-bold");

        return tabs;
    }

    private Tab createTab(String label, Icon icon, Class<? extends Component> navigationTarget) {
        RouterLink link = new RouterLink(label, navigationTarget);
        Tab tab = new Tab(link);
        tab.addComponentAsFirst(icon);
        tab.addClassName("nav-tab");

        return tab;
    }
}
