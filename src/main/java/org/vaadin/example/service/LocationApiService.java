package org.vaadin.example.service;

import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.vaadin.example.config.WeatherApiConfig;
import org.vaadin.example.service.dtos.LocationListDto;

public class LocationApiService {

    private final String apiUrl;
    private final String apiPath;


    @Inject
    public LocationApiService(WeatherApiConfig weatherApiConfig) {
        this.apiUrl = weatherApiConfig.getLocationSearchUrl();
        this.apiPath = weatherApiConfig.getLocationSearchPath();
    }

    public LocationListDto getLocation(String location) {
        Client client = ClientBuilder.newClient();

        try (client) {
            WebTarget target = client.target(apiUrl);
            Response response = target.path(apiPath).queryParam("name", location).queryParam("count", 100).request(MediaType.APPLICATION_JSON).get();
            if (response.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
                throw new WebApplicationException("Failed to call external API: " + response.getStatus());
            }

            return response.readEntity(LocationListDto.class);
        }
    }
}