package com.tourguide.users.service;

import com.google.common.collect.Iterables;
import com.tourguide.users.data.TestData;
import com.tourguide.users.dto.NearbyAttractionDto;
import com.tourguide.users.entity.User;
import com.tourguide.users.mapper.LocationMapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import tripPricer.Provider;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@Slf4j
public class TourGuideServiceTest {

    @Autowired
    private TourGuideService tourGuideService;

    @Autowired
    private LocationMapper locationMapper;

    @MockBean
    private RestGpsService gpsService;

    @MockBean
    private RestRewardService rewardService;

    @MockBean
    private TripPricerService tripPricerService;

    @MockBean
    private UserService userService;

    @Test
    public void getTripDealsTest() {
        User user = TestData.generateAUser("test");
        Mockito.when(userService.getUserByUsername("test")).thenReturn(user);
        List<Provider> providerList = TestData.getProviderList();
        Mockito.when(tripPricerService.getTripDeals(user)).thenReturn(providerList);
        Assertions.assertThat(tourGuideService.getTripDeals("test")).isEqualTo(providerList);
    }

    @Test
    public void getNearbyAttractionTest() {
        User user = TestData.generateAUser("test");
        Mockito.when(userService.getUserByUsername(Mockito.eq("test"))).thenReturn(user);
        user.setVisitedLocations(TestData.generateAVisitedLocationsList(user.getUserId(), 3));

        List<NearbyAttractionDto> nearbyAttractionList = (TestData.getNearbyAttractionDtoList(
                locationMapper.toDto(Iterables.getLast(user.getVisitedLocations()).getLocation()),
                200D, 5));

        Mockito.when(gpsService.getNearbyAttractions(Mockito.any(), Mockito.eq(5)))
                .thenReturn(nearbyAttractionList);
        Mockito.when(rewardService.getAttractionRewardPoint(Mockito.any(), Mockito.eq(user.getUserId())))
                .thenReturn(200);

        nearbyAttractionList = nearbyAttractionList.stream().peek(e -> e.setRewardPoints(200)).collect(Collectors.toList());

        Assertions.assertThat(tourGuideService.getNearbyAttractions("test")).isEqualTo(nearbyAttractionList);


        // empty VisitedLocationList

        user.setVisitedLocations(Collections.emptyList());
        Assertions.assertThat(tourGuideService.getNearbyAttractions("test")).isEqualTo(Collections.emptyList());
    }
}
