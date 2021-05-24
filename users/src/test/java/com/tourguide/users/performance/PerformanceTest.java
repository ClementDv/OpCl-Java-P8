package com.tourguide.users.performance;

import com.tourguide.users.entity.Location;
import com.tourguide.users.entity.VisitedLocation;
import com.tourguide.users.repository.impl.UserRepositoryImpl;
import com.tourguide.users.service.TrackerService;
import com.tourguide.users.config.ScheduledConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
@Import(ScheduledConfig.class)
@Slf4j
public class PerformanceTest {

    static final int USERS_NUMBER = 100000;
    private final UserRepositoryImpl userRepository;
    private final TrackerService tracker;

    private Random random = new Random(10L);

    @Autowired
    public PerformanceTest(UserRepositoryImpl userRepository, TrackerService tracker) {
        this.userRepository = userRepository;
        this.tracker = tracker;
    }

    // 497 sec / 8 min 17

    @Test
    public void TrackerPerformance() throws ExecutionException, InterruptedException {
        userRepository.setUsersCount(10000);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        tracker.tracker();
        stopWatch.stop();
        log.info("Execution time of tracking : {}m{}s", stopWatch.getTime(TimeUnit.MINUTES), (stopWatch.getTime(TimeUnit.SECONDS) % 60));
    }

    /* 11,48 min */
    @Test
    public void TrackLocationPerformance() throws ExecutionException, InterruptedException {
        userRepository.setUsersCount(USERS_NUMBER);

        StopWatch stopWatch = new StopWatch();
        AtomicInteger usersCounter = new AtomicInteger(0);
        ForkJoinPool customThreadPool = new ForkJoinPool(100);
        stopWatch.start();
        ForkJoinTask<?> task = customThreadPool.submit(() -> userRepository.getAllUser().parallelStream().forEach(
                user -> {
                    tracker.trackLastLocation(user);
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
            long timeMillis = stopWatch.getTime(TimeUnit.MILLISECONDS);
            logProgress(usersCount, USERS_NUMBER, timeMillis);
        }
        customThreadPool.shutdownNow();
        stopWatch.stop();
        log.info("Execution time of tracking : {}", formatTime(stopWatch.getTime(TimeUnit.MILLISECONDS)));
    }

    /* 5,21 min */
    @Test
    public void AddRewardPerformance() throws ExecutionException, InterruptedException {
        userRepository.setUsersCount(USERS_NUMBER);

        StopWatch stopWatch = new StopWatch();
        AtomicInteger usersCounter = new AtomicInteger(0);
        ForkJoinPool customThreadPool = new ForkJoinPool(100);
        stopWatch.start();
        ForkJoinTask<?> task = customThreadPool.submit(() -> userRepository.getAllUser().parallelStream().forEach(
                user -> {
                    tracker.addReward(user, generateAVisitedLocation(user.getUserId()));
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
            long timeMillis = stopWatch.getTime(TimeUnit.MILLISECONDS);
            logProgress(usersCount, USERS_NUMBER, timeMillis);
        }
        customThreadPool.shutdownNow();
        stopWatch.stop();
        log.info("Execution time of tracking : {}", formatTime(stopWatch.getTime(TimeUnit.MILLISECONDS)));
    }

    private void logProgress(int usersCount, int userListSize, long timeSeconds) {
        double percentageProgress = roundedDecimals((double) usersCount / userListSize * 100);
        log.info("Tracking progress : {}/{}. [{}%]. Time : {}",
                usersCount, userListSize, percentageProgress, formatTime(timeSeconds));
    }

    private double roundedDecimals(double value) {
        return new BigDecimal(value).setScale(1, RoundingMode.HALF_UP).doubleValue();
    }

    private String formatTime(long timeMillis) {
        String time = "";
        time = time + TimeUnit.MILLISECONDS.toMinutes(timeMillis) + "m";
        time = time + TimeUnit.MILLISECONDS.toSeconds(timeMillis) % 60 + "s";
        return time;
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
