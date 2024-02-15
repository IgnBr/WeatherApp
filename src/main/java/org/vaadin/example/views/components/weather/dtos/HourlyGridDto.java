package org.vaadin.example.views.components.weather.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class HourlyGridDto {

    private String time;

    private Double temperature;

    private Double rain;

    private Double wind;

    private String description;
}
