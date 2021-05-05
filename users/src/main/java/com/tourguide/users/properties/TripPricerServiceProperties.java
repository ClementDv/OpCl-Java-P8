package com.tourguide.users.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.trip-pricer-service")
@Data
public class TripPricerServiceProperties {
    private String tripPricerApiKey;
}
