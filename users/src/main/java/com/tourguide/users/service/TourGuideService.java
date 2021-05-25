package com.tourguide.users.service;

import com.tourguide.users.dto.NearbyAttractionDto;
import tripPricer.Provider;

import java.util.List;

public interface TourGuideService {
    /**
     * Get the list about all trip information of a user
     *
     * @param userName to identify the user
     * @return a <b>list of Provider(id, name, price)</b>
     */
    List<Provider> getTripDeals(String userName);

    /**
     * Get the five closest attraction from the last user location
     *
     * @param userName to identify the user
     * @return a <b>list of Nearby Attractions</b>,
     * if the user does not have visitedLocation an <b>empty list</b> is return
     */
    List<NearbyAttractionDto> getNearbyAttractions(String userName);
}
