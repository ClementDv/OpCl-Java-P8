package com.tourguide.users.service;

import com.tourguide.users.dto.NearbyAttractionDto;
import tripPricer.Provider;

import java.util.List;

public interface TourGuideService {
    List<Provider> getTripDeals(String userName);

    List<NearbyAttractionDto> getNearbyAttractions(String userName);
}
