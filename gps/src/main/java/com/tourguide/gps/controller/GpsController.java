package com.tourguide.gps.controller;

import com.tourguide.gps.dto.NearbyAttractionDto;
import com.tourguide.gps.dto.VisitedLocationDto;
import com.tourguide.gps.service.GpsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.UUID;

@RestController
public class GpsController {

    private final GpsService gpsService;

    public GpsController(GpsService gpsService) {
        this.gpsService = gpsService;
    }

    @GetMapping("/getNearbyAttractions")
    public List<NearbyAttractionDto> getNearbyAttractions(
            @RequestParam Double longitude,
            @RequestParam Double latitude,
            @RequestParam(required = false) @DecimalMin(value = "0", inclusive = false) Double maxDistance,
            @RequestParam(required = false) @Min(1) Integer limit
    ) {
        return gpsService.getNearbyAttractions(longitude, latitude, maxDistance, limit);
    }

    @GetMapping("/trackAUser")
    public VisitedLocationDto trackAUser(
            @RequestParam UUID userId
            ) {
        return gpsService.trackAUser(userId);
    }
}
