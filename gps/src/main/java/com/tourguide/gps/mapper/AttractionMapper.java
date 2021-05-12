package com.tourguide.gps.mapper;

import com.tourguide.gps.dto.AttractionDto;
import com.tourguide.gps.dto.LocationDto;
import gpsUtil.location.Attraction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class AttractionMapper {

    @Mapping(target = "location", ignore = true)
    abstract AttractionDto toDto(Attraction attraction);

    @Mapping(target = "longitude", ignore = true)
    @Mapping(target = "latitude", ignore = true)
    abstract Attraction fromDto(AttractionDto attractionDto);


    public AttractionDto fromUtilToAttractionDto(Attraction attraction) {
        return AttractionDto.builder()
                .attractionId(attraction.attractionId)
                .attractionName(attraction.attractionName)
                .city(attraction.city)
                .state(attraction.state)
                .location(LocationDto.builder()
                        .latitude(attraction.latitude)
                        .longitude(attraction.longitude)
                        .build())
                .build();
    }
}
