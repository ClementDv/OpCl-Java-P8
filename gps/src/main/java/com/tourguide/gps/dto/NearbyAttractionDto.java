package com.tourguide.gps.dto;

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
    private LocationDto location;
    private Double distance;
}
