package com.tourguide.gps.dto;

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
public class VisitedLocationDto {
    private UUID userId;
    private LocationDto location;
    private Date timeVisited;
}
