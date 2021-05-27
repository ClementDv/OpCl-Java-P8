package com.tourguide.gps.service;

import com.tourguide.gps.data.TestData;
import com.tourguide.gps.dto.LocationDto;
import com.tourguide.gps.dto.NearbyAttractionDto;
import com.tourguide.gps.dto.VisitedLocationDto;
import com.tourguide.gps.service.impl.GpsServiceImpl;
import gpsUtil.GpsUtil;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@SpringBootTest
@Slf4j
public class GpsServiceTest {

    @Autowired
    GpsServiceImpl gpsService;

    @MockBean
    GpsUtil gpsUtil;

    @Test
    public void getAttractionsTest() {
        Mockito.when(gpsUtil.getAttractions()).thenReturn(TestData.getAttractionList());
        Assertions.assertThat(gpsService.getAttractions())
                .usingRecursiveComparison()
                .ignoringFields("attractionId")
                .isEqualTo(TestData.getAttractionDtoList());
    }

    @Test
    void getNearbyAttractions() {
        double longitudeTest = 1D;
        double latitudeTest = 1D;

        Mockito.when(gpsUtil.getAttractions()).thenReturn(TestData.getAttractionList());

        /*
         * Locations
         */

        List<NearbyAttractionDto> response = gpsService.getNearbyAttractions(longitudeTest, latitudeTest, null, null);
        Assertions.assertThat(response.size()).isEqualTo(4);
        Assertions.assertThat(response).doesNotContainNull().extracting(NearbyAttractionDto::getAttraction)
                .usingElementComparatorIgnoringFields("attractionId").isEqualTo(TestData.getAttractionDtoList());
        response.forEach(e -> Assertions.assertThat(e.getLocation())
                .isEqualTo(LocationDto.builder().longitude(longitudeTest).latitude(latitudeTest).build()));

        // Send null locations

        response = gpsService.getNearbyAttractions(null, null, null, null);
        Assertions.assertThat(response).isEqualTo(Collections.emptyList());

        /*
         * Location + maxDistance
         */

        response = gpsService.getNearbyAttractions(longitudeTest, latitudeTest, 200D, null);
        Assertions.assertThat(response.size()).isEqualTo(3);
        Assertions.assertThat(response).doesNotContainNull().extracting(NearbyAttractionDto::getAttraction)
                .usingElementComparatorIgnoringFields("attractionId").isEqualTo(TestData.getAttractionDtoList(3));
        response.forEach(e -> Assertions.assertThat(e.getLocation())
                .isEqualTo(LocationDto.builder().longitude(longitudeTest).latitude(latitudeTest).build()));

        /*
         * Location + limit
         */

        response = gpsService.getNearbyAttractions(longitudeTest, latitudeTest, null, 2);
        Assertions.assertThat(response.size()).isEqualTo(2);
        Assertions.assertThat(response).doesNotContainNull().extracting(NearbyAttractionDto::getAttraction)
                .usingElementComparatorIgnoringFields("attractionId").isEqualTo(TestData.getAttractionDtoList(2));
        response.forEach(e -> Assertions.assertThat(e.getLocation())
                .isEqualTo(LocationDto.builder().longitude(longitudeTest).latitude(latitudeTest).build()));

        /*
         * Location + maxDistance + limit
         */

        response = gpsService.getNearbyAttractions(longitudeTest, latitudeTest, 200D, 2);
        Assertions.assertThat(response.size()).isEqualTo(2);
        Assertions.assertThat(response).doesNotContainNull().extracting(NearbyAttractionDto::getAttraction)
                .usingElementComparatorIgnoringFields("attractionId").isEqualTo(TestData.getAttractionDtoList(2));
        response.forEach(e -> Assertions.assertThat(e.getLocation())
                .isEqualTo(LocationDto.builder().longitude(longitudeTest).latitude(latitudeTest).build()));
    }

    @Test
    void trackAUserTest() {
        Locale.setDefault(Locale.US);
        UUID testId = UUID.randomUUID();
        Mockito.when(gpsUtil.getUserLocation(testId)).thenReturn(new GpsUtil().getUserLocation(testId));
        VisitedLocationDto response = gpsService.trackAUser(testId);
        Assertions.assertThat(response).hasNoNullFieldsOrProperties().extracting("userId").isEqualTo(testId);
    }
}
