package com.tourguide.users.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.gps-service")
@Data
public class GpsServiceProperties {
    private String url;
}
