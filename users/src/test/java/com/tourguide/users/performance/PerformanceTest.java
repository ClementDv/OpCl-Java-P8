package com.tourguide.users.performance;

import com.tourguide.users.entity.Location;
import com.tourguide.users.entity.VisitedLocation;
import com.tourguide.users.repository.impl.UserRepositoryImpl;
import com.tourguide.users.service.TrackerService;
import com.tourguide.users.config.ScheduledConfig;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@Import(ScheduledConfig.class)
public class PerformanceTest {

    private final UserRepositoryImpl userRepository;
    private final TrackerService tracker;

    private Random random = new Random(10L);

    @Autowired
    public PerformanceTest(UserRepositoryImpl userRepository, TrackerService tracker) {
        this.userRepository = userRepository;
        this.tracker = tracker;
    }

    @Test
    public void TrackerPerformance() {
        userRepository.setUsersCount(10000);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        tracker.tracker();
        stopWatch.stop();
        System.out.println("Execution time of tracking : " + stopWatch.getTime(TimeUnit.SECONDS));
    }

    /* 11,48 min */
    @Test
    public void TrackLocationPerformance() {
        userRepository.setUsersCount(10000);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        userRepository.getAllUser().forEach(tracker::trackLastLocation);
        stopWatch.stop();
        System.out.println("Execution time of tracking : " + stopWatch.getTime(TimeUnit.SECONDS));
    }

    /* 5,21 min */
    @Test
    public void AddRewardPerformance() {
        userRepository.setUsersCount(10000);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        userRepository.getAllUser().forEach(user -> tracker.addReward(user, generateAVisitedLocation(user.getUserId())));
        stopWatch.stop();
        System.out.println("Execution time of tracking : " + stopWatch.getTime(TimeUnit.SECONDS));
    }

    private VisitedLocation generateAVisitedLocation(UUID uuid) {
        return VisitedLocation.builder()
                .userId(uuid)
                .location(generateALocation())
                .timeVisited(new Date(ThreadLocalRandom.current().nextInt() * 1000L))
                .build();
    }

    private Location generateALocation() {
        double longitudeLeftLimit = -180;
        double longitudeRightLimit = 180;
        double latitudeLeftLimit = -85.05112878;
        double latitudeRightLimit = 85.05112878;
        return Location.builder()
                .longitude(random.nextDouble() * (longitudeRightLimit - longitudeLeftLimit))
                .latitude(random.nextDouble() * (latitudeRightLimit - latitudeLeftLimit))
                .build();
    }
}
