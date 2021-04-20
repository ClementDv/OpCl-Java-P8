package com.tourguide.users.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserReward {
    private VisitedLocation visitedLocation;
    private Attraction attraction;
    private int rewardPoints;
}
