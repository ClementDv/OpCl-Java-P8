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
@Schema(name = "LocationDto", description = "A location with longitude and latitude value")
public class LocationDto {
    @Schema(name = "longitude", description = "Longitude value of position")
    private double longitude;
    @Schema(name = "latitude", description = "Latitude value of position")
    private double latitude;
}
