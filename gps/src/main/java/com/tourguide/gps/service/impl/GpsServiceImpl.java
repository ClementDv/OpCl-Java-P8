package com.tourguide.gps.service.impl;

import com.tourguide.gps.dto.AttractionDto;
import com.tourguide.gps.dto.LocationDto;
import com.tourguide.gps.dto.NearbyAttractionDto;
import com.tourguide.gps.dto.VisitedLocationDto;
import com.tourguide.gps.mapper.AttractionMapper;
import com.tourguide.gps.mapper.VisitedLocationMapper;
import com.tourguide.gps.service.GpsService;
import gpsUtil.GpsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class GpsServiceImpl implements GpsService {

    private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;
    private static final Double MAX_DISTANCE_DEFAULT = Double.MAX_VALUE;

    private final VisitedLocationMapper visitedLocationMapper;
    private final AttractionMapper attractionMapper;
    private final GpsUtil gpsUtil;

    public GpsServiceImpl(VisitedLocationMapper visitedLocationMapper, AttractionMapper attractionMapper, GpsUtil gpsUtil) {
        this.visitedLocationMapper = visitedLocationMapper;
        this.attractionMapper = attractionMapper;
        this.gpsUtil = gpsUtil;
    }

    public List<AttractionDto> getAttractions() {
        return gpsUtil.getAttractions().stream().map(attractionMapper::fromUtilToAttractionDto).collect(Collectors.toList());
    }

    @Override
    public List<NearbyAttractionDto> getNearbyAttractions(
            Double longitude, Double latitude, Double maxDistance, Integer limit) {
        if (longitude != null && latitude != null) {
            if (maxDistance == null) {
                maxDistance = MAX_DISTANCE_DEFAULT;
            }
            LocationDto userLocation = LocationDto.builder().longitude(longitude).latitude(latitude).build();
            Double finalMaxDistance = maxDistance;
            Stream<NearbyAttractionDto> mainStream = getAttractions().stream()
                    .map(attraction -> {
                        double distance = getDistanceInMile(userLocation, attraction.getLocation());
                        if (distance < finalMaxDistance) {
                            return NearbyAttractionDto.builder()
                                    .attraction(attraction)
                                    .location(userLocation)
                                    .distance(distance)
                                    .build();
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .sorted(Comparator.comparingDouble(NearbyAttractionDto::getDistance));
            if (limit != null) {
                mainStream = mainStream.limit(limit);
            }
            log.debug("Gps service success : get nearby attraction for longitude {}, latitude {}", longitude, latitude);
            return mainStream.collect(Collectors.toList());
        }
        log.debug("Gps service warning : get nearby attraction for longitude {}, latitude {}", longitude, latitude);
        return Collections.emptyList();
    }

    @Override
    public VisitedLocationDto trackAUser(UUID userId) {
        return visitedLocationMapper.toDto(gpsUtil.getUserLocation(userId));
    }

    double getDistanceInMile(LocationDto a, LocationDto b) {
        double lat1 = Math.toRadians(a.getLatitude());
        double lon1 = Math.toRadians(a.getLongitude());
        double lat2 = Math.toRadians(b.getLatitude());
        double lon2 = Math.toRadians(b.getLongitude());

        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

        double nauticalMiles = 60 * Math.toDegrees(angle);
        return STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
    }
}
