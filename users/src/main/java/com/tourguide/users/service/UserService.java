package com.tourguide.users.service;

import com.tourguide.users.dto.LocationDto;
import com.tourguide.users.dto.VisitedLocationDto;

import java.util.Map;
import java.util.UUID;

public interface UserService {
    VisitedLocationDto getLastVisitedLocation(String username);

    Map<UUID, LocationDto> getAllCurrentLocation();
}
