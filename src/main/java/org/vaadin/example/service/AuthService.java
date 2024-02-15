package org.vaadin.example.service;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.VaadinSession;
import jakarta.inject.Inject;
import org.vaadin.example.entity.User;
import org.vaadin.example.views.favouriteLocations.FavouriteLocationsView;
import org.vaadin.example.views.layout.MainLayout;
import org.vaadin.example.views.locationSearch.LocationSearchView;
import org.vaadin.example.views.weatherForecast.WeatherForecastView;

import java.util.ArrayList;
import java.util.List;

public class AuthService {

    public record AuthorizedRoute(String route, Class<? extends Component> view) {
    }

    public static class AuthException extends Exception {
        public AuthException(String message) {
            super(message);
        }
    }

    private final UserService userService;

    @Inject
    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public void authenticate(String username, String password) throws AuthException {
        User user = userService.findByUsername(username);
        if (user == null || !user.checkPassword(password)) {
            throw new AuthException("Authentication failed. Invalid username or password.");
        }
        VaadinSession.getCurrent().setAttribute(User.class, user);
        createRoutes();
    }

    private void createRoutes() {
        getAuthorizedRoutes()
                .forEach(route ->
                        RouteConfiguration.forSessionScope().setRoute(route.route, route.view, MainLayout.class));
    }

    public static List<AuthorizedRoute> getAuthorizedRoutes() {
        List<AuthorizedRoute> routes = new ArrayList<>();
        routes.add(new AuthorizedRoute("", LocationSearchView.class));
        routes.add(new AuthorizedRoute("favourite", FavouriteLocationsView.class));
        routes.add(new AuthorizedRoute("weather", WeatherForecastView.class));
        return routes;
    }

}