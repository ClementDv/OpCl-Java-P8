package com.tourguide.gps.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AttractionDto {
    private UUID attractionId;
    private String attractionName;
    private String city;
    private String state;
    private LocationDto location;
}
