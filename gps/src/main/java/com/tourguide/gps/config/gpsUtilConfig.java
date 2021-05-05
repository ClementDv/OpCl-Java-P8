package com.tourguide.gps.config;

import gpsUtil.GpsUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class gpsUtilConfig {

    @Bean
    public GpsUtil getGpsUtil() {
        return new GpsUtil();
    }
}
