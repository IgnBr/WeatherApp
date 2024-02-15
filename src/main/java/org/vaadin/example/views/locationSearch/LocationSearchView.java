package org.vaadin.example.views.locationSearch;


import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import jakarta.inject.Inject;
import org.vaadin.example.service.LocationApiService;
import org.vaadin.example.service.dtos.LocationListDto;
import org.vaadin.example.views.components.location.LocationGrid;

// TODO: Add css styling
public class LocationSearchView extends VerticalLayout {

    private final LocationGrid locationGrid;
    private final TextField searchField;
    private final LocationApiService weatherApiService;

    @Inject
    public LocationSearchView(LocationApiService weatherApiService, LocationGrid locationView) {
        this.weatherApiService = weatherApiService;
        this.locationGrid = locationView;
        this.locationGrid.setVisible(false);

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setPadding(true);
        getStyle().set("background-color", "#f0f0f0");

        searchField = createSearchField();
        Button searchButton = createSearchButton();

        add(new HorizontalLayout(searchField, searchButton), locationView);
    }

    private TextField createSearchField() {
        TextField searchField = new TextField("Search for locations");
        searchField.setPlaceholder("Enter location name");
        searchField.setWidth("300px");
        searchField.addKeyPressListener(Key.ENTER, event -> searchLocation(searchField.getValue()));
        return searchField;
    }

    private Button createSearchButton() {
        Button searchButton = new Button("Search", event -> searchLocation(searchField.getValue()));
        searchButton.setWidth("100px");
        searchButton.getStyle().set("margin-top", "10px");
        return searchButton;
    }

    private void searchLocation(String cityName) {
        searchField.clear();
        LocationListDto locations = weatherApiService.getLocation(cityName);
        Integer locationsCount = locations.getLocationCount();
        if (locationsCount > 0) {
            locationGrid.setVisible(true);
            Notification.show(locationsCount + " matches found!")
                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            locationGrid.setLocations(locations.getLocations());
        } else {
            locationGrid.setVisible(false);
            Notification.show("No matches found!")
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }
}
