package org.vaadin.example.config;

import jakarta.annotation.Resource;
import jakarta.inject.Singleton;
import lombok.Getter;

@Getter
@Singleton
public class WeatherApiConfig {

    @Resource(name = "location-search-url")
    private String locationSearchUrl;

    @Resource(name = "location-search-path")
    private String locationSearchPath;

    @Resource(name = "weather-forecast-url")
    private String weatherForecastUrl;

    @Resource(name = "weather-forecast-path")
    private String weatherForecastPath;
}