package com.tourguide.gps.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(name = "VisitedLocationDto", description = "A location visited by a User with the location and the visited Time date")
public class VisitedLocationDto {
    @Schema(name = "userId", description = "Id from the user who was at the location")
    private UUID userId;
    @Schema(name = "location", description = "Value of the Location")
    private LocationDto location;
    @Schema(name = "timeVisited", description = "Date at the moment the user was at this location")
    private Date timeVisited;
}
