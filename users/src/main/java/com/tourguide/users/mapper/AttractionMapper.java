package com.tourguide.users.mapper;

import com.tourguide.users.dto.AttractionDto;
import com.tourguide.users.entity.Attraction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {LocationMapper.class})
public interface AttractionMapper {
    Attraction fromDto(AttractionDto attractionDto);

    @Mapping(target = "location", ignore = true)
    AttractionDto toDto(Attraction attraction);
}
