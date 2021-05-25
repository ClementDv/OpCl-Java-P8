package com.tourguide.users.service;

import com.tourguide.users.dto.NearbyAttractionDto;
import com.tourguide.users.dto.VisitedLocationDto;
import com.tourguide.users.entity.Location;

import java.util.List;
import java.util.UUID;

public interface RestGpsService {
    /**
     * The value is get from the GPS Module by http request.
     * Get all the closest attraction from a location
     *
     * @param location reference to get the nearby's attraction
     * @return a <b>list of Dto NearbyAttraction</b>,
     * if the response body is empty it return an empty list.
     */
    List<NearbyAttractionDto> getNearbyAttractions(Location location);

    /**
     * The value is get from the GPS Module by http request.
     * Get all the closest attraction from a location limited by a maximal distance
     *
     * @param location    reference to get the nearby's attraction
     * @param maxDistance the distance maximal to get the closest attraction
     * @return a <b>list of Dto NearbyAttraction</b>,
     * if the response body is empty it return an empty list.
     */
    List<NearbyAttractionDto> getNearbyAttractions(Location location, double maxDistance);

    /**
     * The value is get from the GPS Module by http request with a limit of attraction to get
     *
     * @param location reference to get the nearby's attraction
     * @param Limit    the number maximal of nearby's Attraction
     * @return a <b>list of Dto NearbyAttraction</b>,
     * if the response body is empty it return an empty list.
     */
    List<NearbyAttractionDto> getNearbyAttractions(Location location, int Limit);

    /**
     * The value is get from the GPS Module by http request.
     * Get all the closest attraction from a location limited by a maximal distance with a limit of attraction to get
     *
     * @param location    reference to get the nearby's attraction
     * @param maxDistance the distance maximal to get the closest attraction
     * @param Limit       the number maximal of nearby's Attraction
     * @return a <b>list of Dto NearbyAttraction</b>,
     * if the response body is empty it return an empty list.
     */
    List<NearbyAttractionDto> getNearbyAttractions(Location location, double maxDistance, int Limit);

    /**
     * The value is get from the GPS Module by http request.
     * Get the location tracked from a user.
     *
     * @param userId to identify the user
     * @return the Dto of the location tracked
     */
    VisitedLocationDto trackAUser(UUID userId);
}
