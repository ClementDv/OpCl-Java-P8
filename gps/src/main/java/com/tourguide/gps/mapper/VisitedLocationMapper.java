package com.tourguide.gps.mapper;

import com.tourguide.gps.dto.VisitedLocationDto;
import gpsUtil.location.VisitedLocation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {LocationMapper.class})
public interface VisitedLocationMapper {
    VisitedLocation fromDto(VisitedLocationDto visitedLocationDto);

    VisitedLocationDto toDto(VisitedLocation visitedLocation);
}
