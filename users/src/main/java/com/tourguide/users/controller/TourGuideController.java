package com.tourguide.users.controller;

import com.tourguide.users.dto.LocationDto;
import com.tourguide.users.dto.NearbyAttractionDto;
import com.tourguide.users.dto.UserRewardDto;
import com.tourguide.users.dto.VisitedLocationDto;
import com.tourguide.users.service.TourGuideService;
import com.tourguide.users.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tripPricer.Provider;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class TourGuideController {

    private final TourGuideService tourGuideService;

    private final UserService userService;

    public TourGuideController(TourGuideService tourGuideService, UserService userService) {
        this.tourGuideService = tourGuideService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String index() {
        return "Greetings from TourGuide!";
    }

    @GetMapping("/getLocation")
    public VisitedLocationDto getLocation(
            @RequestParam String userName
    ) {
        return userService.getLastVisitedLocation(userName);
    }

    @GetMapping("/getAllCurrentLocations")
    public Map<UUID, LocationDto> getAllCurrentLocations() {
        return userService.getAllCurrentLocation();
    }

    @GetMapping("/getRewards")
    public List<UserRewardDto> getRewards(
            @RequestParam String userName
    ) {
        return userService.getUserRewards(userName);
    }

    @GetMapping("/getTripDeals")
    public List<Provider> getTripDeals(
            @RequestParam String userName
    ) {
        return tourGuideService.getTripDeals(userName);
    }

    @GetMapping("/getNearbyAttractions")
    public List<NearbyAttractionDto> getNearbyAttractions(
            @RequestParam String userName
    ) {
        return tourGuideService.getNearbyAttractions(userName);
    }
}
