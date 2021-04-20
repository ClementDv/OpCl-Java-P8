package com.tourguide.users.controller;

import com.tourguide.users.dto.LocationDto;
import com.tourguide.users.dto.VisitedLocationDto;
import com.tourguide.users.entity.Location;
import com.tourguide.users.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
public class TourGuideController {

    private final UserService userService;

    public TourGuideController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index() {
        return "Greetings from TourGuide!";
    }

    @GetMapping("/getLocation")
    public VisitedLocationDto getLocation(@RequestParam String userName) {
        return userService.getLastVisitedLocation(userName);
    }

    @GetMapping("/getAllCurrentLocations")
    public Map<UUID, LocationDto> getAllCurrentLocations() {
        return userService.getAllCurrentLocation();
    }
}
