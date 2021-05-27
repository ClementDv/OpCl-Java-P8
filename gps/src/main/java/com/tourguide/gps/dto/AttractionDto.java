package com.tourguide.gps.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(name = "AttractionDto", description = "All the information of an attraction")
public class AttractionDto {
    @Schema(name = "attractionId", description = "The id of the attraction")
    private UUID attractionId;
    @Schema(name = "attractionName", description = "The name of the attraction")
    private String attractionName;
    @Schema(name = "city", description = "The city of the attraction")
    private String city;
    @Schema(name = "state", description = "The state of the attraction")
    private String state;
    @Schema(name = "location", description = "The location of the attraction")
    private LocationDto location;
}


