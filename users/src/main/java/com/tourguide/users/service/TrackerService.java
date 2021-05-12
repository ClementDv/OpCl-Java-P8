package com.tourguide.users.service;

import com.tourguide.users.dto.NearbyAttractionDto;
import com.tourguide.users.entity.User;
import com.tourguide.users.entity.UserReward;
import com.tourguide.users.entity.VisitedLocation;
import com.tourguide.users.mapper.AttractionMapper;
import com.tourguide.users.mapper.VisitedLocationMapper;
import com.tourguide.users.properties.TrackingProperties;
import com.tourguide.users.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TrackerService {

    private final TrackingProperties trackingProperties;

    private final UserRepository userRepository;
    private final RestGpsService restGpsService;
    private final RestRewardService restRewardService;

    private final VisitedLocationMapper visitedLocationMapper;
    private final AttractionMapper attractionMapper;

    public TrackerService(TrackingProperties trackingProperties, UserRepository userRepository, RestGpsService restGpsService,
                          RestRewardService restRewardService, VisitedLocationMapper visitedLocationMapper, AttractionMapper attractionMapper) {
        this.trackingProperties = trackingProperties;
        this.userRepository = userRepository;
        this.restGpsService = restGpsService;
        this.restRewardService = restRewardService;
        this.visitedLocationMapper = visitedLocationMapper;
        this.attractionMapper = attractionMapper;
    }

    @Scheduled(initialDelay = 5000L, fixedRate = 5000L)
    public void tracker() {
        userRepository.getAllUser().forEach(user -> {
            // Track last visited location

            VisitedLocation lastVisitedLocation = trackLastLocation(user);

            // Add Reward from last visited location

            addReward(user, lastVisitedLocation);
            log.info("Tracker : User {} have been successfully tracked", user.getUserName());
        });
    }

    public VisitedLocation trackLastLocation(User user) {
        VisitedLocation lastVisitedLocation = visitedLocationMapper.fromDto(restGpsService.trackAUser(user.getUserId()));
        userRepository.addUserVisitedLocation(user.getUserName(), lastVisitedLocation);
        return lastVisitedLocation;
    }

    public void addReward(User user, VisitedLocation lastLocation) {
        List<NearbyAttractionDto> nearbyAttractionDtoList = restGpsService.getNearbyAttractions(
                lastLocation.getLocation(), trackingProperties.getProximityDistance());
        for (NearbyAttractionDto nearbyAttraction : nearbyAttractionDtoList) {
            // Check if attractions are already been visited
            if (user.getUserRewards().stream().noneMatch(
                    userReward -> userReward.getAttraction().getAttractionName().equals(nearbyAttraction.getAttraction().getAttractionName()))) {
                UserReward userRewardToAdd = UserReward.builder()
                        .visitedLocation(lastLocation)
                        .attraction(attractionMapper.fromDto(nearbyAttraction.getAttraction()))
                        .rewardPoints(restRewardService.getAttractionRewardPoint(
                                nearbyAttraction.getAttraction().getAttractionId(), user.getUserId()))
                        .build();
                userRepository.addUserReward(user.getUserName(), userRewardToAdd);
            }
        }
    }
}
