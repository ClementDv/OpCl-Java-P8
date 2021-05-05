package com.tourguide.users.service.impl;

import com.google.common.collect.Iterables;
import com.tourguide.users.dto.NearbyAttractionDto;
import com.tourguide.users.entity.User;
import com.tourguide.users.entity.VisitedLocation;
import com.tourguide.users.mapper.LocationMapper;
import com.tourguide.users.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import tripPricer.Provider;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TourGuideServiceImpl implements TourGuideService {

    private final RestRewardService restRewardService;
    private final RestGpsService restGpsService;
    private final TripPricerService tripPricerService;
    private final UserService userService;

    private final LocationMapper locationMapper;

    public TourGuideServiceImpl(RestRewardService restRewardService, RestGpsService restGpsService,
                                TripPricerService tripPricerService, UserService userService, LocationMapper locationMapper) {
        this.restRewardService = restRewardService;
        this.restGpsService = restGpsService;
        this.tripPricerService = tripPricerService;
        this.userService = userService;
        this.locationMapper = locationMapper;
    }

    @Override
    public List<Provider> getTripDeals(String userName) {
        User user = userService.getUserByUsername(userName);
        log.info("Tourguide service success : getTripDeals for user : {}", userName);
        return tripPricerService.getTripDeals(user);
    }

    @Override
    public List<NearbyAttractionDto> getNearbyAttractions(String userName) {
        User user = userService.getUserByUsername(userName);
        VisitedLocation userLastVisitedLocation = Iterables.getLast(user.getVisitedLocations());
        if (ObjectUtils.isEmpty(userLastVisitedLocation)) {
            log.warn("Tourguide service warning : No visited location found for user {}", userName);
            return Collections.emptyList();
        }
        List<NearbyAttractionDto> nearbyAttractionDtoList = restGpsService.getNearbyAttractions(userLastVisitedLocation.getLocation(), 5);
        return nearbyAttractionDtoList.stream().peek(e -> {
            e.setRewardPoints(restRewardService.getAttractionRewardPoint(e.getAttraction().getAttractionId(), user.getUserId()));
            e.setUserLocation(locationMapper.toDto(userLastVisitedLocation.getLocation()));
            log.info("Tourguide service success : getNearbyAttraction for user {}", userName);
        }).collect(Collectors.toList());
    }
}
