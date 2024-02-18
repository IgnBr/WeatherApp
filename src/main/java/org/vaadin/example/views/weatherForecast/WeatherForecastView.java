package org.vaadin.example.views.weatherForecast;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import jakarta.inject.Inject;
import org.vaadin.example.service.WeatherApiService;
import org.vaadin.example.service.dtos.WeatherForecastDto;
import org.vaadin.example.utils.JsonWeatherDescriptionService;
import org.vaadin.example.views.components.weather.HourlyGrid;
import org.vaadin.example.views.components.weather.WeatherCard;
import org.vaadin.example.views.components.weather.dtos.HourlyGridDto;
import org.vaadin.example.views.components.weather.dtos.WeatherCardDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// TODO: Add css styling
public class WeatherForecastView extends VerticalLayout implements BeforeEnterObserver {

    private final WeatherApiService weatherApiService;
    private final JsonWeatherDescriptionService jsonWeatherDescriptionService;
    private final HourlyGrid hourlyGrid = new HourlyGrid();
    private WeatherForecastDto weather;
    private String location;
    private Integer selectedCard = null;

    @Inject
    public WeatherForecastView(WeatherApiService weatherApiService, JsonWeatherDescriptionService jsonWeatherDescriptionService) {
        this.weatherApiService = weatherApiService;
        this.jsonWeatherDescriptionService = jsonWeatherDescriptionService;
        hourlyGrid.setVisible(false);
        setSizeFull();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Map<String, List<String>> parametersMap = event.getLocation().getQueryParameters().getParameters();
        List<String> longitudeList = parametersMap.get("longitude");
        List<String> latitudeList = parametersMap.get("latitude");
        List<String> locationList = parametersMap.get("location");

        if (longitudeList == null || longitudeList.isEmpty() || latitudeList == null || latitudeList.isEmpty() || locationList == null || locationList.isEmpty()) {
            Notification.show("Longitude, latitude, and location query parameters are required.").addThemeVariants(NotificationVariant.LUMO_ERROR);
            return;
        }

        String longitude = longitudeList.get(0);
        String latitude = latitudeList.get(0);
        location = locationList.get(0);
        weather = weatherApiService.getWeather(longitude, latitude);

        try {
            addWeatherCards();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addWeatherCards() throws IOException {
        HorizontalLayout cardList = new HorizontalLayout();
        cardList.addClassName("weather-cards-container");
        for (int i = 0; i < 7; i++) {
            WeatherCard card = createWeatherCard(i);
            cardList.add(card);
        }

        add(cardList, hourlyGrid);
    }

    private WeatherCard createWeatherCard(int index) throws IOException {
        WeatherCard card = new WeatherCard(buildWeatherCardDto(index));
        card.addClickListener(event -> handleCardClick(index));
        return card;
    }

    private WeatherCardDto buildWeatherCardDto(int index) throws IOException {
        return new WeatherCardDto(
                weather.getLatitude(),
                weather.getLongitude(),
                location,
                getWeatherConditionDescription(weather.getDaily().getWeather_code().get(index)),
                getWeatherConditionIcon(weather.getDaily().getWeather_code().get(index)),
                new WeatherCardDto.Daily(
                        weather.getDaily().getTime().get(index),
                        weather.getDaily().getTemperature_2m_max().get(index),
                        weather.getDaily().getTemperature_2m_min().get(index),
                        weather.getDaily().getRain_sum().get(index),
                        weather.getDaily().getWind_speed_10m_max().get(index)
                ),
                weather.getDaily_units()
        );
    }

    private void handleCardClick(int index) {
        if (selectedCard == null) {
            hourlyGrid.setVisible(true);
        } else if (selectedCard == index) {
            return;
        }
        selectedCard = index;
        updateHourlyGridData();
        Notification.show("Displaying hourly data for the selected day!")
                .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    private void updateHourlyGridData() {
        List<HourlyGridDto> hourlyGridData = new ArrayList<>();
        int startIndex = selectedCard * 24;
        for (int i = 0; i < 24; i++) {
            int currentItem = startIndex + i;
            try {
                HourlyGridDto hourlyGridDto = new HourlyGridDto(
                        weather.getHourly().getTime().get(currentItem),
                        weather.getHourly().getTemperature_2m().get(currentItem),
                        weather.getHourly().getRain().get(currentItem),
                        weather.getHourly().getWind_speed_10m().get(currentItem),
                        getWeatherConditionDescription(weather.getHourly().getWeather_code().get(currentItem))
                );
                hourlyGridData.add(hourlyGridDto);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        hourlyGrid.updateHourlyData(hourlyGridData);
    }

    // TODO: Only read JSON data once
    private String getWeatherConditionIcon(int weatherConditionCode) throws IOException {
        return jsonWeatherDescriptionService.loadWeatherData()
                .getJsonObject(String.valueOf(weatherConditionCode))
                .getJsonObject("day")
                .getJsonString("image")
                .getString();
    }

    private String getWeatherConditionDescription(int weatherConditionCode) throws IOException {
        return jsonWeatherDescriptionService.loadWeatherData()
                .getJsonObject(String.valueOf(weatherConditionCode))
                .getJsonObject("day")
                .getJsonString("description")
                .getString();
    }
}
