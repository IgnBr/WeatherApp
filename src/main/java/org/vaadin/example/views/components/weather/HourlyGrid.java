package org.vaadin.example.views.components.weather;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.vaadin.example.views.components.weather.dtos.HourlyGridDto;

import java.util.List;

public class HourlyGrid extends VerticalLayout {
    private final Grid<HourlyGridDto> grid = new Grid<>();

    public HourlyGrid() {
        setupGrid();
        add(grid);
    }

    public void updateHourlyData(List<HourlyGridDto> hourlyData) {
        grid.setItems(hourlyData);
    }

    private void setupGrid() {
        grid.addColumn(HourlyGridDto::getTime).setHeader("Time");
        grid.addColumn(HourlyGridDto::getDescription).setHeader("Description");
        grid.addColumn(HourlyGridDto::getTemperature).setHeader("Temperature");
        grid.addColumn(HourlyGridDto::getRain).setHeader("Rain");
        grid.addColumn(HourlyGridDto::getWind).setHeader("Wind");
    }
}
