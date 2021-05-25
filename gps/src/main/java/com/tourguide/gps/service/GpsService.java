package com.tourguide.gps.service;

import com.tourguide.gps.dto.NearbyAttractionDto;
import com.tourguide.gps.dto.VisitedLocationDto;

import java.util.List;
import java.util.UUID;

public interface GpsService {
    /**
     * Get all the closest attraction from a location limited by a maximal distance with
     * a limit of attraction to get sort by distance.
     *
     * @param longitude   of the user location
     * @param latitude    of the user location
     * @param maxDistance the distance maximal to get the closest attraction
     * @param limit       the number maximal of nearby's Attraction
     * @return a <b>list of Dto NearbyAttraction</b>,
     * if the location values are wrong an <b>empty list</b> is returned.
     */
    List<NearbyAttractionDto> getNearbyAttractions(Double longitude, Double latitude, Double maxDistance, Integer limit);

    /**
     * Get the location tracked from a user.
     *
     * @param userId to identify the user
     * @return the Dto of the location tracked
     */
    VisitedLocationDto trackAUser(UUID userId);
}
