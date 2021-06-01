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
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

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

    /*
     Track all user and calculate the reward of each users every 5 minutes
     */

    @Scheduled(initialDelay = 5000L, fixedRate = 300000L)
    public void tracker() throws ExecutionException, InterruptedException {
        List<User> userList = userRepository.getAllUser();
        AtomicInteger usersCounter = new AtomicInteger(0);
        log.info("Tracking Start. User(s) to track : {}", userList.size());
        ForkJoinPool pool = new ForkJoinPool(150);
        StopWatch timeCounter = new StopWatch();
        timeCounter.start();
        ForkJoinTask<?> task = pool.submit(() -> userList.parallelStream().forEach(user -> {

            // Track last visited location

            VisitedLocation lastVisitedLocation = trackLastLocation(user);

            // Add Reward from last visited location

            addReward(user, lastVisitedLocation);
            usersCounter.incrementAndGet();
        }));
        boolean isFinish = false;
        while (!isFinish) {
            try {
                task.get(1, TimeUnit.SECONDS);
                isFinish = true;
            } catch (TimeoutException ignored) { // Used to log each seconds
            }
            int usersCount = usersCounter.get();
            long timeMillis = timeCounter.getTime(TimeUnit.MILLISECONDS);
            logProgress(usersCount, userList.size(), timeMillis);
        }
        timeCounter.stop();
        pool.shutdownNow();
        log.info("Tracking End. User(s) have been successfully tracked");
    }

    private void logProgress(int usersCount, int userListSize, long timeSeconds) {
        double percentageProgress = roundedDecimals((double) usersCount / userListSize * 100);
        log.info("Tracking progress : {}/{}. [{}%]. Time : {}",
                usersCount, userListSize, percentageProgress, formatTime(timeSeconds));
    }

    private String formatTime(long timeMillis) {
        String time = "";
        time = time + TimeUnit.MILLISECONDS.toMinutes(timeMillis) + "m";
        time = time + TimeUnit.MILLISECONDS.toSeconds(timeMillis) % 60 + "s";
        return time;
    }

    private double roundedDecimals(double value) {
        return new BigDecimal(value).setScale(1, RoundingMode.HALF_UP).doubleValue();
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
