package org.vaadin.example.views.components.weather.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.vaadin.example.service.dtos.WeatherForecastDto;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherCardDto {
    
    private double latitude;

    private double longitude;

    private String location;

    private String description;

    private String icon;

    private Daily daily;

    private WeatherForecastDto.DailyUnits dailyUnits;

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Daily {
        private String time;

        private Double maxTemp;

        private Double minTemp;

        private Double rain;

        private Double wind;
    }
}
