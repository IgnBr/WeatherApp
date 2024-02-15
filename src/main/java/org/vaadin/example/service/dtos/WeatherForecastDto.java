package org.vaadin.example.service.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
// TODO: Refactor dto to use camel case
public class WeatherForecastDto {
    @JsonProperty("latitude")
    private double latitude;
    @JsonProperty("longitude")
    private double longitude;

    @JsonProperty("timezone")
    private String timezone;
    @JsonProperty("hourly_units")
    private HourlyUnits hourly_units;
    @JsonProperty("hourly")
    private Hourly hourly;
    @JsonProperty("daily_units")
    private DailyUnits daily_units;
    @JsonProperty("daily")
    private Daily daily;

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    public static class Hourly {
        @JsonProperty("time")
        private List<String> time;
        @JsonProperty("temperature_2m")
        private List<Double> temperature_2m;
        @JsonProperty("rain")
        private List<Double> rain;
        @JsonProperty("weather_code")
        private List<Integer> weather_code;
        @JsonProperty("wind_speed_10m")
        private List<Double> wind_speed_10m;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    public static class HourlyUnits {
        @JsonProperty("temperature_2m")
        private String temperature_2m;
        @JsonProperty("rain")
        private String rain;
        @JsonProperty("wind_speed_10m")
        private String wind_speed_10m;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    public static class Daily {
        @JsonProperty("time")
        private List<String> time;
        @JsonProperty("weather_code")
        private List<Integer> weather_code;
        @JsonProperty("temperature_2m_max")
        private List<Double> temperature_2m_max;
        @JsonProperty("temperature_2m_min")
        private List<Double> temperature_2m_min;
        @JsonProperty("rain_sum")
        private List<Double> rain_sum;
        @JsonProperty("wind_speed_10m_max")
        private List<Double> wind_speed_10m_max;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    public static class DailyUnits {
        @JsonProperty("temperature_2m_max")
        private String temperature_2m_max;
        @JsonProperty("temperature_2m_min")
        private String temperature_2m_min;
        @JsonProperty("wind_speed_10m_max")
        private String wind_speed_10m_max;
        @JsonProperty("rain_sum")
        private String rain_sum;
    }
}