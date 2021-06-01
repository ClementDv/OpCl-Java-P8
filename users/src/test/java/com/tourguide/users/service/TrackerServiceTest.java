package com.tourguide.users.service;

import com.tourguide.users.data.TestData;
import com.tourguide.users.dto.NearbyAttractionDto;
import com.tourguide.users.dto.VisitedLocationDto;
import com.tourguide.users.entity.User;
import com.tourguide.users.entity.VisitedLocation;
import com.tourguide.users.mapper.VisitedLocationMapper;
import com.tourguide.users.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

@SpringBootTest
public class TrackerServiceTest {

    @Autowired
    private TrackerService trackerService;

    @Autowired
    private VisitedLocationMapper visitedLocationMapper;

    @MockBean
    private RestGpsService gpsService;

    @MockBean
    private RestRewardService rewardService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void trackLastLocationTest() {
        User user = TestData.generateAUser("test");
        VisitedLocationDto visitedLocationDto = TestData.generateAVisitedLocationDto(user.getUserId());
        Mockito.when(gpsService.trackAUser(Mockito.eq(user.getUserId())))
                .thenReturn(visitedLocationDto);
        VisitedLocation visitedLocation = visitedLocationMapper.fromDto(visitedLocationDto);
        Assertions.assertThat(trackerService.trackLastLocation(user))
                .isEqualTo(visitedLocation);
        Mockito.verify(userRepository, Mockito.atLeast(1)).addUserVisitedLocation(
                user.getUserName(), visitedLocation);
    }

    @Test
    public void addReward() {
        User user = TestData.generateAUser("test");
        VisitedLocation visitedLocation = TestData.generateAVisitedLocation(user.getUserId());
        VisitedLocationDto visitedLocationDto = visitedLocationMapper.toDto(visitedLocation);
        List<NearbyAttractionDto> nearbyAttractionDtoList = TestData.getNearbyAttractionDtoList(
                visitedLocationDto.getLocation(), 200D, null);

        Mockito.when(gpsService.getNearbyAttractions(Mockito.eq(visitedLocation.getLocation()), Mockito.anyDouble()))
                .thenReturn(nearbyAttractionDtoList);

        // Attraction never visited / add Reward

        trackerService.addReward(user, visitedLocation);
        Mockito.verify(userRepository, Mockito.atLeast(1)).addUserReward(
                Mockito.eq(user.getUserName()), Mockito.any());
    }
}
