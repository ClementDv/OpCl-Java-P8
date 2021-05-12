package com.tourguide.users.dto;

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
@Schema(name = "VisitedLocationDto", description = "An object with the location and the visited Time date")
public class VisitedLocationDto {

    @Schema(name = "userId", description = "Id from User")
    private UUID userId;
    private LocationDto location;
    private Date timeVisited;
}
