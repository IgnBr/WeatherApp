package org.vaadin.example.views.components.weather;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.vaadin.example.views.components.weather.dtos.WeatherCardDto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@CssImport("./themes/my-theme/weatherCard.css")
public class WeatherCard extends VerticalLayout {

    private final WeatherCardDto weatherCardDto;

    public WeatherCard(WeatherCardDto weatherCardDto) {
        this.addClassName("weather-card");
        this.weatherCardDto = weatherCardDto;
        String date = LocalDate.parse(weatherCardDto.getDaily().getTime()).format(DateTimeFormatter.ofPattern("EEEE, MMMM dd"));
        VerticalLayout weatherCardLayout = getWeatherCardLayout(date);
        weatherCardLayout.setPadding(false);
        add(weatherCardLayout);
    }

    private VerticalLayout getWeatherCardLayout(String date) {
        VerticalLayout weatherCardLayout = new VerticalLayout();
        weatherCardLayout.add(
                getLocationLayout(),
                getDateLayout(date),
                getTemperatureLayout("Max Temperature: ", weatherCardDto.getDaily().getMaxTemp(), weatherCardDto.getDailyUnits().getTemperature_2m_max()),
                getTemperatureLayout("Min Temperature: ", weatherCardDto.getDaily().getMinTemp(), weatherCardDto.getDailyUnits().getTemperature_2m_min()),
                getConditionLayout(),
                getWindSpeedLayout(),
                getHumidityLayout()
        );
        return weatherCardLayout;
    }

    private HorizontalLayout getHumidityLayout() {
        return getHorizontalLayout("Rain: ", weatherCardDto.getDaily().getRain(), weatherCardDto.getDailyUnits().getRain_sum());
    }

    private HorizontalLayout getWindSpeedLayout() {
        return getHorizontalLayout("Wind Speed: ", weatherCardDto.getDaily().getWind(), weatherCardDto.getDailyUnits().getWind_speed_10m_max());
    }

    private HorizontalLayout getConditionLayout() {
        return getHorizontalLayout("Condition: ", weatherCardDto.getDescription());
    }

    private HorizontalLayout getTemperatureLayout(String label, Double value, String unit) {
        return getHorizontalLayout(label, value, unit);
    }

    private HorizontalLayout getDateLayout(String date) {
        return getHorizontalLayout("Date: ", date);
    }

    private HorizontalLayout getLocationLayout() {
        HorizontalLayout locationLayout = new HorizontalLayout();
        Div imageContainer = new Div(new Image(weatherCardDto.getIcon(), "Weather Icon"));
        imageContainer.addClassName("image-container");
        locationLayout.add(imageContainer);
        locationLayout.add(new Span(weatherCardDto.getLocation()));
        locationLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        return locationLayout;
    }

    private HorizontalLayout getHorizontalLayout(String label, Object value) {
        HorizontalLayout layout = new HorizontalLayout();
        layout.add(new Span(label));
        layout.add(new Span(value.toString()));
        layout.setAlignItems(Alignment.BASELINE);
        return layout;
    }

    private HorizontalLayout getHorizontalLayout(String label, Double value, String unit) {
        return getHorizontalLayout(label, value.toString() + " " + unit);
    }
}