package com.tourguide.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(name = "UserRewardDto", description = "The rewards Points from an visited attraction, with the attraction and the user's location which valid the visit")
public class UserRewardDto {
    @Schema(name = "visitedLocation", description = "The visited location which valid the visit")
    private VisitedLocationDto visitedLocation;
    @Schema(name = "attraction", description = "The visited attraction")
    private AttractionDto attraction;
    @Schema(name = "rewardPoints", description = "The number of point get for the visit")
    private int rewardPoints;
}
