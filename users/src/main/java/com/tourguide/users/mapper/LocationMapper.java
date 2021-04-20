package com.tourguide.users.mapper;

import com.tourguide.users.dto.LocationDto;
import com.tourguide.users.entity.Location;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    Location fromDto(LocationDto locationDto);

    LocationDto toDto(Location location);
}
