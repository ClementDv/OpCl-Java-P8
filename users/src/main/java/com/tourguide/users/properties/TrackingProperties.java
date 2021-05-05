package com.tourguide.users.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.tracker")
@Data
public class TrackingProperties {
    private Double proximityDistance;
}
