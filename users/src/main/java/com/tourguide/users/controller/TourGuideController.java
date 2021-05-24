package com.tourguide.users.controller;

import com.tourguide.users.dto.LocationDto;
import com.tourguide.users.dto.NearbyAttractionDto;
import com.tourguide.users.dto.UserRewardDto;
import com.tourguide.users.dto.VisitedLocationDto;
import com.tourguide.users.service.TourGuideService;
import com.tourguide.users.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tripPricer.Provider;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@Tag(name = "TourGuideController", description = "Get HTTP requests from users to get all infos they need")
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
    @Operation(description = "Get the current location from a User")
    public VisitedLocationDto getLocation(
            @Parameter(name = "userName", description = "Name to find the user", required = true) @RequestParam String userName
    ) {
        return userService.getLastVisitedLocation(userName);
    }

    @GetMapping("/getAllCurrentLocations")
    @Operation(description = "Get every current users location")
    public Map<UUID, LocationDto> getAllCurrentLocations() {
        return userService.getAllCurrentLocation();
    }

    @GetMapping("/getRewards")
    @Operation(description = "Get the rewards from a User")
    public List<UserRewardDto> getRewards(
            @Parameter(name = "userName", description = "Name to find the user") @RequestParam String userName
    ) {
        return userService.getUserRewards(userName);
    }

    @GetMapping("/getTripDeals")
    @Operation(description = "Get the information about the trip of a user")
    public List<Provider> getTripDeals(
            @Parameter(name = "userName", description = "Name to find the user") @RequestParam String userName
    ) {
        return tourGuideService.getTripDeals(userName);
    }

    @GetMapping("/getNearbyAttractions")
    @Operation(description = "Get 5 closest attractions from a user")
    public List<NearbyAttractionDto> getNearbyAttractions(
            @Parameter(name = "userName", description = "Name to find the user") @RequestParam String userName
    ) {
        return tourGuideService.getNearbyAttractions(userName);
    }
}
