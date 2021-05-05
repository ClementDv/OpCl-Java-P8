package com.tourguide.gps.mapper;

import com.tourguide.gps.dto.AttractionDto;
import com.tourguide.gps.dto.LocationDto;
import gpsUtil.location.Attraction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class AttractionMapper {

    abstract AttractionDto toDto(Attraction attraction);

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
