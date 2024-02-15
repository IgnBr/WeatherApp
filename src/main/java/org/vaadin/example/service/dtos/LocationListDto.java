package org.vaadin.example.service.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Setter
@NoArgsConstructor
public class LocationListDto {
    
    @JsonProperty("results")
    private List<LocationDto> results;

    public List<LocationDto> getLocations() {
        return Objects.isNull(results) ? List.of() : results;
    }

    public Integer getLocationCount() {
        return Objects.isNull(results) ? 0 : results.size();
    }
}
