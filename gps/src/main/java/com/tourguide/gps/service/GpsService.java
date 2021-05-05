package com.tourguide.gps.service;

import com.tourguide.gps.dto.LocationDto;
import com.tourguide.gps.dto.NearbyAttractionDto;
import com.tourguide.gps.dto.VisitedLocationDto;

import java.util.List;
import java.util.UUID;

public interface GpsService {
    List<NearbyAttractionDto> getNearbyAttractions(Double longitude, Double latitude, Double maxDistance, Integer limit);

    VisitedLocationDto trackAUser(UUID userId);
}
