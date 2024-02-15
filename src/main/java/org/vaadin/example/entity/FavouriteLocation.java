package org.vaadin.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "favourite_locations", uniqueConstraints = @UniqueConstraint(columnNames = {"location_id", "user_id"}))
public class FavouriteLocation extends AbstractEntity implements Serializable {

    @Column(name = "location_id", nullable = false)
    private Integer locationId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public FavouriteLocation(Integer locationId, String name, String country, Double latitude, Double longitude, User user) {
        this.locationId = locationId;
        this.name = name;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.user = user;
    }
}