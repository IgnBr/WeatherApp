package org.vaadin.example.service;

import jakarta.inject.Inject;
import org.vaadin.example.entity.FavouriteLocation;
import org.vaadin.example.repository.FavouriteLocationRepository;

public class FavouriteLocationService {
    private final FavouriteLocationRepository favouriteLocationRepository;

    @Inject
    public FavouriteLocationService(FavouriteLocationRepository favouriteLocationRepository) {
        this.favouriteLocationRepository = favouriteLocationRepository;
    }

    public FavouriteLocation save(FavouriteLocation location) {
        return favouriteLocationRepository.save(location);
    }

    public void removeByUserIdAndLocationId(Integer userId, Integer locationId) {
        favouriteLocationRepository.removeByUserIdAndLocationId(userId, locationId);
    }
}