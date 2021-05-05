package com.tourguide.users.mapper;

import com.tourguide.users.dto.VisitedLocationDto;
import com.tourguide.users.entity.User;
import com.tourguide.users.entity.VisitedLocation;
import org.mapstruct.Mapper;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Mapper(componentModel = "spring", uses = {LocationMapper.class})
public interface VisitedLocationMapper {
    VisitedLocation fromDto(VisitedLocationDto visitedLocationDto);

    VisitedLocationDto toDto(VisitedLocation visitedLocation);

    default VisitedLocationDto toLastVisitedLocationDto(User user) {
        if (user == null) {
            return null;
        }
        List<VisitedLocation> visitedLocations = user.getVisitedLocations();
        if (CollectionUtils.isEmpty(visitedLocations)) {
            return null;
        }
        VisitedLocation lastLocation = visitedLocations.get(visitedLocations.size() - 1);
        return toDto(lastLocation);
    }
}
