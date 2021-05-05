package com.tourguide.users.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.reward-service")
@Data
public class RewardServiceProperties {
    private String url;
}
