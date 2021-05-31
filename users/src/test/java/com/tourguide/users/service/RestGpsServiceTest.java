package com.tourguide.users.service;

import com.tourguide.users.data.TestData;
import com.tourguide.users.dto.NearbyAttractionDto;
import com.tourguide.users.dto.VisitedLocationDto;
import com.tourguide.users.entity.Location;
import com.tourguide.users.mapper.LocationMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.UUID;

@SpringBootTest
public class RestGpsServiceTest {

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private RestGpsService restGpsService;

    @Autowired
    private LocationMapper locationMapper;

    @Test
    public void getNearbysAttractionLocationsOnly() {
        Location location = TestData.generateALocation();
        NearbyAttractionDto[] nearbyAttractionArray = TestData.getNearbyAttractionDtoArray(
                locationMapper.toDto(location), null, null);
        Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Mockito.any()))
                .thenReturn(new ResponseEntity(nearbyAttractionArray, HttpStatus.OK));

        Assertions.assertThat(restGpsService.getNearbyAttractions(location)).isEqualTo(TestData.getNearbyAttractionListFromArray(nearbyAttractionArray));
    }

    @Test
    public void getNearbysAttractionLocationsLimit() {
        Location location = TestData.generateALocation();
        NearbyAttractionDto[] nearbyAttractionArray = TestData.getNearbyAttractionDtoArray(
                locationMapper.toDto(location), null, 4);
        Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Mockito.any()))
                .thenReturn(new ResponseEntity(nearbyAttractionArray, HttpStatus.OK));

        Assertions.assertThat(restGpsService.getNearbyAttractions(location, 4)).isEqualTo(TestData.getNearbyAttractionListFromArray(nearbyAttractionArray));
    }

    @Test
    public void getNearbysAttractionLocationsMaxDistance() {
        Location location = TestData.generateALocation();
        NearbyAttractionDto[] nearbyAttractionArray = TestData.getNearbyAttractionDtoArray(
                locationMapper.toDto(location), 200D, null);
        Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Mockito.any()))
                .thenReturn(new ResponseEntity(nearbyAttractionArray, HttpStatus.OK));

        Assertions.assertThat(restGpsService.getNearbyAttractions(location, 200D)).isEqualTo(TestData.getNearbyAttractionListFromArray(nearbyAttractionArray));
    }

    @Test
    public void getNearbysAttractionLocationsMaxDistanceLimit() {
        Location location = TestData.generateALocation();
        NearbyAttractionDto[] nearbyAttractionArray = TestData.getNearbyAttractionDtoArray(
                locationMapper.toDto(location), 400D, 8);
        Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Mockito.any()))
                .thenReturn(new ResponseEntity(nearbyAttractionArray, HttpStatus.OK));

        Assertions.assertThat(restGpsService.getNearbyAttractions(location, 400D, 8)).isEqualTo(TestData.getNearbyAttractionListFromArray(nearbyAttractionArray));
    }

    @Test
    public void getNearbysAttractionEmptyList() {
        Location location = TestData.generateALocation();
        Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Mockito.any()))
                .thenReturn(new ResponseEntity(Collections.emptyList().toArray(), HttpStatus.OK));

        Assertions.assertThat(restGpsService.getNearbyAttractions(location, 400D, 8)).isEqualTo(Collections.emptyList());
    }

    @Test
    public void trackAUser() {
        UUID userId = UUID.randomUUID();
        VisitedLocationDto visitedLocation = TestData.generateAVisitedLocationDto(userId);
        Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Mockito.any()))
                .thenReturn(new ResponseEntity(visitedLocation, HttpStatus.OK));

        Assertions.assertThat(restGpsService.trackAUser(userId))
                .isEqualTo(visitedLocation);
    }
}
