package com.tourguide.gps.controller;

import com.tourguide.gps.dto.NearbyAttractionDto;
import com.tourguide.gps.dto.VisitedLocationDto;
import com.tourguide.gps.service.GpsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "GpsController", description = "Controller used to communicate with user module to get GPS information")
public class GpsController {

    private final GpsService gpsService;

    public GpsController(GpsService gpsService) {
        this.gpsService = gpsService;
    }

    @GetMapping("/getNearbyAttractions")
    @Operation(description = "Get the closest attraction from a location, with possibility to filter them by distance and number")
    public List<NearbyAttractionDto> getNearbyAttractions(
            @Parameter(name = "longitude", description = "The longitude of the position") @RequestParam Double longitude,
            @Parameter(name = "latitude", description = "The latitude of the position") @RequestParam Double latitude,
            @Parameter(name = "maxDistance", description = "The distance to filter") @RequestParam(required = false) @DecimalMin(value = "0", inclusive = false) Double maxDistance,
            @Parameter(name = "limit", description = "The number of attraction to get") @RequestParam(required = false) @Min(1) Integer limit
    ) {
        return gpsService.getNearbyAttractions(longitude, latitude, maxDistance, limit);
    }

    @GetMapping("/trackAUser")
    @Operation(description = "Track the Location of a User")
    public VisitedLocationDto trackAUser(
            @Parameter(name = "userId", description = "The id of a user to track") @RequestParam UUID userId
    ) {
        return gpsService.trackAUser(userId);
    }
}
