package com.tourguide.gps.mapper;

import com.tourguide.gps.dto.LocationDto;
import gpsUtil.location.Location;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    Location fromDto(LocationDto locationDto);

    LocationDto toDto(Location location);
}
