package org.vaadin.example.service;

import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.vaadin.example.config.WeatherApiConfig;
import org.vaadin.example.service.dtos.WeatherForecastDto;


public class WeatherApiService {

    private static final String HOURLY_PARAMS = "temperature_2m,rain,weather_code,wind_speed_10m";
    private static final String DAILY_PARAMS = "weather_code,temperature_2m_max,temperature_2m_min,rain_sum,wind_speed_10m_max";
    private final String apiUrl;
    private final String apiPath;


    @Inject
    public WeatherApiService(WeatherApiConfig weatherApiConfig) {
        this.apiUrl = weatherApiConfig.getWeatherForecastUrl();
        this.apiPath = weatherApiConfig.getWeatherForecastPath();
    }

    public WeatherForecastDto getWeather(String longitude, String latitude) {
        Client client = ClientBuilder.newClient();

        try (client) {
            WebTarget target = client.target(apiUrl);
            Response response = target.path(apiPath)
                    .queryParam("longitude", longitude)
                    .queryParam("latitude", latitude)
                    .queryParam("hourly", HOURLY_PARAMS)
                    .queryParam("daily", DAILY_PARAMS)
                    .request(MediaType.APPLICATION_JSON)
                    .get();

            if (response.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
                throw new WebApplicationException("Failed to call external API: " + response.getStatus());
            }

            return response.readEntity(WeatherForecastDto.class);
        }
    }
}
