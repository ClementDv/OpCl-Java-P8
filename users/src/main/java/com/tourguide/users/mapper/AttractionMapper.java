package com.tourguide.users.mapper;

import com.tourguide.users.dto.AttractionDto;
import com.tourguide.users.entity.Attraction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AttractionMapper {
    Attraction fromDto(AttractionDto attractionDto);

    AttractionDto toDto(Attraction attraction);
}
