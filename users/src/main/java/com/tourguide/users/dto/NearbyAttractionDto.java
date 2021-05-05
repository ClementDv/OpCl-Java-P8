package com.tourguide.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NearbyAttractionDto {
    private AttractionDto attraction;
    private LocationDto userLocation;
    private Double distance;
    private int rewardPoints;
}
