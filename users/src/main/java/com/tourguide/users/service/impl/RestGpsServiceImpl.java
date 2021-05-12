package com.tourguide.users.service.impl;

import com.tourguide.users.dto.NearbyAttractionDto;
import com.tourguide.users.dto.VisitedLocationDto;
import com.tourguide.users.entity.Location;
import com.tourguide.users.properties.GpsServiceProperties;
import com.tourguide.users.service.RestGpsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Service
@Slf4j
public class RestGpsServiceImpl implements RestGpsService {

    private final GpsServiceProperties gpsServiceProperties;

    public RestGpsServiceImpl(GpsServiceProperties gpsServiceProperties) {
        this.gpsServiceProperties = gpsServiceProperties;
    }

    @Override
    public List<NearbyAttractionDto> getNearbyAttractions(Location location) {
        return getNearbyAttractions(location, null, null);
    }

    @Override
    public List<NearbyAttractionDto> getNearbyAttractions(Location location, double maxDistance) {
        return getNearbyAttractions(location, maxDistance, null);
    }

    @Override
    public List<NearbyAttractionDto> getNearbyAttractions(Location location, int limit) {
        return getNearbyAttractions(location, null, limit);
    }

    @Override
    public List<NearbyAttractionDto> getNearbyAttractions(Location location, double maxDistance, int limit) {
        return getNearbyAttractions(location, null, limit);
    }

    private List<NearbyAttractionDto> getNearbyAttractions(Location location, Double maxDistance, Integer limit) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(gpsServiceProperties.getUrl())
                .path("/getNearbyAttractions")
                .queryParam("longitude", location.getLongitude())
                .queryParam("latitude", location.getLatitude());
        if (maxDistance != null) {
            builder = builder.queryParam("maxDistance", maxDistance);
        }
        if (limit != null) {
            builder = builder.queryParam("limit", limit);
        }


        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<NearbyAttractionDto[]> response = restTemplate.getForEntity(
                builder.toUriString(),
                NearbyAttractionDto[].class
        );

        if (ArrayUtils.isEmpty(response.getBody())) {
            log.info("Gps service : No result found from gps service for location{}", location);
            return Collections.emptyList();
        }
        log.info("Gps service successfully called : Get nearby attractions for location {}", location);
        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }

    @Override
    public VisitedLocationDto trackAUser(UUID userId) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(gpsServiceProperties.getUrl())
                .path("/trackAUser")
                .queryParam("userId", userId);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<VisitedLocationDto> response = restTemplate.getForEntity(
                builder.toUriString(),
                VisitedLocationDto.class
        );
        log.info("Gps service successfully called : Track a user with userId {}", userId);
        return response.getBody();
    }
}
