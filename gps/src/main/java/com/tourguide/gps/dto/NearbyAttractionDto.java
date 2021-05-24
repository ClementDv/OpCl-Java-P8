package com.tourguide.gps.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(name = "NearbyAttractionDto", description = "An attraction close to a user location, with the distance and the rewards points it earns")
public class NearbyAttractionDto {
    @Schema(name = "attraction", description = "The nearby's attraction")
    private AttractionDto attraction;
    @Schema(name = "location", description = "The user location")
    private LocationDto location;
    @Schema(name = "distance", description = "Distance between user location and attraction location")
    private Double distance;
}
