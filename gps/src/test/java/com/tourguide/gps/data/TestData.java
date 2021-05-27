package com.tourguide.gps.data;

import com.tourguide.gps.dto.AttractionDto;
import com.tourguide.gps.dto.LocationDto;
import gpsUtil.location.Attraction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestData {

    public static List<Attraction> getAttractionList() {
        return new ArrayList<>(Arrays.asList(
                new Attraction("test1", "city1", "state1", 1D, 1D),
                new Attraction("test2", "city2", "state2", 2D, 2D),
                new Attraction("test3", "city3", "state3", 3D, 3D),
                new Attraction("test4", "city4", "state4", 4D, 4D)
        ));
    }

    public static List<AttractionDto> getAttractionDtoList() {
      return  getAttractionDtoList(null);
    }

    public static List<AttractionDto> getAttractionDtoList(Integer limit) {
        List<AttractionDto> response = new ArrayList<>(Arrays.asList(
                AttractionDto.builder()
                        .attractionName("test1")
                        .city("city1")
                        .state("state1")
                        .location(
                                LocationDto.builder()
                                        .latitude(1D)
                                        .longitude(1D)
                                        .build())
                        .build(),
                AttractionDto.builder()
                        .attractionName("test2")
                        .city("city2")
                        .state("state2")
                        .location(
                                LocationDto.builder()
                                        .latitude(2D)
                                        .longitude(2D)
                                        .build())
                        .build(),
                AttractionDto.builder()
                        .attractionName("test3")
                        .city("city3")
                        .state("state3")
                        .location(
                                LocationDto.builder()
                                        .latitude(3D)
                                        .longitude(3D)
                                        .build())
                        .build(),
                AttractionDto.builder()
                        .attractionName("test4")
                        .city("city4")
                        .state("state4")
                        .location(
                                LocationDto.builder()
                                        .latitude(4D)
                                        .longitude(4D)
                                        .build())
                        .build()
        ));
        if (limit != null) {
            return response.stream().limit(limit).collect(Collectors.toList());
        }
        return response;
    }

}
