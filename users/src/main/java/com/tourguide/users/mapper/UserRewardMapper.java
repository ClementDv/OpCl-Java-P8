package com.tourguide.users.mapper;

import com.tourguide.users.dto.UserRewardDto;
import com.tourguide.users.entity.UserReward;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {VisitedLocationMapper.class, AttractionMapper.class})
public interface UserRewardMapper {
    UserReward fromDto(UserRewardDto userRewardDto);

    UserRewardDto toDto(UserReward userReward);
}
