package com.tourguide.users.service;

import com.tourguide.users.dto.NearbyAttractionDto;
import com.tourguide.users.dto.VisitedLocationDto;
import com.tourguide.users.entity.Location;

import java.util.List;
import java.util.UUID;

public interface RestGpsService {
    List<NearbyAttractionDto> getNearbyAttractions(Location location);

    List<NearbyAttractionDto> getNearbyAttractions(Location location, double maxDistance);

    List<NearbyAttractionDto> getNearbyAttractions(Location location, int Limit);

    List<NearbyAttractionDto> getNearbyAttractions(Location location, double maxDistance, int Limit);

    VisitedLocationDto trackAUser(UUID userId);
}
