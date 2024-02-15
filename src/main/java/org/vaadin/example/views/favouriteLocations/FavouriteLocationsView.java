package org.vaadin.example.views.favouriteLocations;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinSession;
import jakarta.inject.Inject;
import org.vaadin.example.entity.FavouriteLocation;
import org.vaadin.example.entity.User;
import org.vaadin.example.service.UserService;
import org.vaadin.example.service.dtos.LocationDto;
import org.vaadin.example.views.components.location.LocationGrid;

import java.util.List;

// TODO: Add css styling
public class FavouriteLocationsView extends VerticalLayout {

    private final LocationGrid locationView;
    private final UserService userService;

    @Inject
    public FavouriteLocationsView(LocationGrid locationView, UserService userService) {
        this.locationView = locationView;
        this.userService = userService;

        setupLocationGrid();
        setSizeFull();
        add(locationView);
    }

    private void setupLocationGrid() {
        User user = getUserFromSession();
        List<FavouriteLocation> locations = userService.findById(user.getId()).getFavouriteLocations();
        List<LocationDto> locationsDto = mapToFavouriteLocationDtoList(locations);
        locationView.setLocations(locationsDto);
    }

    private User getUserFromSession() {
        return VaadinSession.getCurrent().getAttribute(User.class);
    }

    private List<LocationDto> mapToFavouriteLocationDtoList(List<FavouriteLocation> locations) {
        return locations.stream()
                .map(location -> new LocationDto(
                        location.getLocationId(),
                        location.getName(),
                        location.getCountry(),
                        location.getLatitude(),
                        location.getLongitude()
                ))
                .toList();
    }
}