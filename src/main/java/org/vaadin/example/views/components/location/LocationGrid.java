package org.vaadin.example.views.components.location;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.server.VaadinSession;
import jakarta.inject.Inject;
import org.vaadin.example.entity.FavouriteLocation;
import org.vaadin.example.entity.User;
import org.vaadin.example.service.FavouriteLocationService;
import org.vaadin.example.service.UserService;
import org.vaadin.example.service.dtos.LocationDto;

import java.util.*;
import java.util.stream.Collectors;

public class LocationGrid extends VerticalLayout {

    private static final int pageSize = 10;

    private final FavouriteLocationService favouriteLocationService;

    private final User user;

    private int currentPage = 0;

    private int totalPages = 0;

    private Span pageInfo;

    private List<LocationDto> locations = new ArrayList<>();

    private List<LocationDto> originalLocations = new ArrayList<>();

    private final Grid<LocationDto> grid = new Grid<>();

    private HorizontalLayout gridPagingControls;

    private Button prevButton;

    private Button nextButton;

    private List<Integer> favouriteIds;

    private final List<FavouriteLocation> favouriteLocations;

    @Inject
    public LocationGrid(UserService userService, FavouriteLocationService favouriteLocationService) {
        this.favouriteLocationService = favouriteLocationService;
        User user = VaadinSession.getCurrent().getAttribute(User.class);
        this.user = userService.findById(user.getId());
        favouriteLocations = this.user.getFavouriteLocations();

        setupGrid();
        setupPagingControls();
        TextField locationFilterField = getLocationFilterField();
        updateNavigation();

        add(locationFilterField, grid, gridPagingControls);

    }

    private TextField getLocationFilterField() {
        TextField locationFilterField = new TextField();
        locationFilterField.setPlaceholder("Filter by location");
        locationFilterField.addValueChangeListener(event -> {
            if (Objects.equals(locationFilterField.getValue(), "")) locations = originalLocations;
            else locations = originalLocations.stream()
                    .filter(location -> location.getName().contains(locationFilterField.getValue()))
                    .collect(Collectors.toList());
            updateGrid(1);
        });
        return locationFilterField;
    }


    public void setLocations(List<LocationDto> locations) {
        this.locations = locations;
        originalLocations = locations;
        updateGrid(1);
    }

    private void updateGrid(int page) {
        totalPages = (int) Math.ceil((double) locations.size() / pageSize);
        if (totalPages == 0) return;
        currentPage = Math.min(Math.max(1, page), totalPages);
        int startIndex = (currentPage - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, locations.size());

        List<LocationDto> pageItems = locations.subList(startIndex, endIndex);
        grid.setItems(pageItems);

        updateNavigation();
    }

    private void updateNavigation() {
        prevButton.setEnabled(currentPage > 1);
        nextButton.setEnabled(currentPage < totalPages);

        pageInfo.setText("Page " + currentPage + " of " + totalPages);
    }

    private void setupPagingControls() {
        prevButton = new Button("Previous", e -> updateGrid(currentPage - 1));
        nextButton = new Button("Next", e -> updateGrid(currentPage + 1));
        pageInfo = new Span();
        gridPagingControls = new HorizontalLayout(prevButton, nextButton, pageInfo);
    }

    private void setupGrid() {
        grid.addColumn(LocationDto::getName).setHeader("Location");
        grid.addColumn(LocationDto::getCountry).setHeader("Country");
        grid.addColumn(LocationDto::getLatitude).setHeader("Latitude");
        grid.addColumn(LocationDto::getLongitude).setHeader("Longitude");

        favouriteIds = favouriteLocations.stream().map(FavouriteLocation::getLocationId)
                .collect(Collectors.toCollection(ArrayList::new));

        grid.addComponentColumn(this::addFavouriteCheckbox);
        grid.addItemClickListener(this::onGridItemClick);
    }

    private Checkbox addFavouriteCheckbox(LocationDto locationDto) {
        boolean isChecked = favouriteIds.contains(locationDto.getId());
        Checkbox favoriteCheckbox = new Checkbox(isChecked);
        favoriteCheckbox.addValueChangeListener(event -> onFavouriteCheckToggle(locationDto, event));

        return favoriteCheckbox;
    }

    private void onGridItemClick(ItemClickEvent<LocationDto> event) {
        LocationDto selectedLocation = event.getItem();
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("longitude", Double.toString(selectedLocation.getLongitude()));
        queryParams.put("latitude", Double.toString(selectedLocation.getLatitude()));
        queryParams.put("location", selectedLocation.getName());
        getUI().ifPresent(ui -> ui.navigate("weather", QueryParameters.simple(queryParams)));
    }

    private void onFavouriteCheckToggle(LocationDto locationDto, AbstractField.ComponentValueChangeEvent<Checkbox, Boolean> event) {
        boolean isFavourite = event.getValue();
        if (isFavourite) {
            FavouriteLocation favouriteLocation = new FavouriteLocation(locationDto.getId(), locationDto.getName(), locationDto.getCountry(), locationDto.getLatitude(), locationDto.getLongitude(), user);
            favouriteLocationService.save(favouriteLocation);
            user.getFavouriteLocations().add(favouriteLocation);
            favouriteIds.add(locationDto.getId());
            return;
        }
        user.getFavouriteLocations().removeIf(location -> Objects.equals(location.getLocationId(), locationDto.getId()));
        favouriteLocationService.removeByUserIdAndLocationId(user.getId(), locationDto.getId());
        favouriteIds.remove(locationDto.getId());
    }
}