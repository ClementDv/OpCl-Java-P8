package com.tourguide.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRewardDto {
    private VisitedLocationDto visitedLocation;
    private AttractionDto attraction;
    private int rewardPoints;
}
