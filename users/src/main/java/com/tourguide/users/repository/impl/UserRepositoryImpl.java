package com.tourguide.users.repository.impl;

import com.tourguide.users.entity.*;
import com.tourguide.users.repository.UserRepository;
import com.tourguide.users.util.CollectionUtil;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final int USER_NUMBER_GEN_DEFAULT = 10;
    private Random random = new Random(10L);

    public Map<String, User> userMap = new HashMap<>();

    public UserRepositoryImpl() {
        setUsersCount(USER_NUMBER_GEN_DEFAULT);
    }

    public synchronized void setUsersCount(int userToGenerate) {
        userMap.clear();
        IntStream.range(0, userToGenerate).forEach(i -> {
            String userName = "userName : " + i;
            userMap.put(userName, generateAUser(userName));
        });
    }

    @Override
    public synchronized User findUserByUserName(String userName) {
        return cloneUser(userMap.get(userName));
    }

    @Override
    public synchronized List<User> getAllUser() {
        return userMap.values().stream().map(this::cloneUser).collect(Collectors.toList());
    }

    @Override
    public synchronized boolean addUserVisitedLocation(String userName, VisitedLocation lastVisitedLocation) {
        if (userName == null || lastVisitedLocation == null) {
            return false;
        }
        User user = userMap.get(userName);
        if (user == null) {
            return false;
        }
        return user.getVisitedLocations().add(lastVisitedLocation);
    }

    @Override
    public synchronized boolean addUserReward(String userName, UserReward userRewardToAdd) {
        if (userName == null || userRewardToAdd == null) {
            return false;
        }
        User user = userMap.get(userName);
        if (user == null) {
            return false;
        }
        return user.getUserRewards().add(userRewardToAdd);
    }

    private User generateAUser(String userName) {
        UUID uuid = UUID.randomUUID();
        return User.builder()
                .userId(uuid)
                .userName(userName)
                .phoneNumber("0" + random.nextInt(10))
                .emailAddress("emailAdress@Test.com")
                .latestLocationTimestamp(null)
                .visitedLocations(generateAVisitedLocationsList(uuid))
                .userRewards(new ArrayList<>())
                .userPreferences(new UserPreferences())
                .build();
    }

    private List<VisitedLocation> generateAVisitedLocationsList(UUID uuid) {
        return IntStream.range(0, random.nextInt(3))
                .mapToObj(i -> generateAVisitedLocation(uuid)).collect(Collectors.toList());
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

    private List<UserReward> generateAUserRewardList(UUID uuid) {
        return IntStream.range(0, random.nextInt(10))
                .mapToObj(i -> UserReward.builder()
                        .visitedLocation(generateAVisitedLocation(uuid))
                        .attraction(generateAAttraction())
                        .rewardPoints(random.nextInt(10000))
                        .build()
                ).collect(Collectors.toList());
    }

    private Attraction generateAAttraction() {
        return Attraction.builder()
                .attractionName("attractionTest" + random.nextInt(100))
                .city("city" + random.nextInt(100))
                .state("state" + random.nextInt(100))
                .attractionId(UUID.randomUUID())
                .build();
    }

    private User cloneUser(User user) {
        if (user == null) {
            return null;
        }
        return User.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .phoneNumber(user.getPhoneNumber())
                .emailAddress(user.getEmailAddress())
                .latestLocationTimestamp(user.getLatestLocationTimestamp())
                .userRewards(CollectionUtil.notNullOrEmpty(user.getUserRewards())
                        .stream().map(this::cloneUserReward).collect(Collectors.toList()))
                .visitedLocations(CollectionUtil.notNullOrEmpty(user.getVisitedLocations())
                        .stream().map(this::cloneVisitedLocation).collect(Collectors.toList()))
                .userPreferences(clonePreferences(user.getUserPreferences()))
                .build();
    }

    private UserReward cloneUserReward(UserReward userReward) {
        if (userReward == null) {
            return null;
        }
        return UserReward.builder()
                .visitedLocation(cloneVisitedLocation(userReward.getVisitedLocation()))
                .attraction(cloneAttraction(userReward.getAttraction()))
                .rewardPoints(userReward.getRewardPoints())
                .build();
    }

    private Attraction cloneAttraction(Attraction attraction) {
        if (attraction == null) {
            return null;
        }
        return Attraction.builder()
                .attractionId(attraction.getAttractionId())
                .attractionName(attraction.getAttractionName())
                .city(attraction.getCity())
                .state(attraction.getState())
                .build();
    }

    private VisitedLocation cloneVisitedLocation(VisitedLocation visitedLocation) {
        if (visitedLocation == null) {
            return null;
        }
        return VisitedLocation.builder()
                .userId(visitedLocation.getUserId())
                .location(cloneLocation(visitedLocation.getLocation()))
                .timeVisited(visitedLocation.getTimeVisited())
                .build();
    }

    private Location cloneLocation(Location location) {
        if (location != null) {
            return Location.builder()
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude())
                    .build();
        }
        return null;
    }

    private UserPreferences clonePreferences(UserPreferences userPreferences) {
        if (userPreferences == null) {
            return null;
        }
        return UserPreferences.builder()
                .tripDuration(userPreferences.getTripDuration())
                .build();
    }
}
