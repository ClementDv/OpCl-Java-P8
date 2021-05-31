package com.tourguide.users.data;

import com.tourguide.users.dto.*;
import com.tourguide.users.entity.*;
import com.tourguide.users.util.CollectionUtil;
import tripPricer.Provider;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestData {

    private static Random random = new Random(10L);

    public static NearbyAttractionDto[] getNearbyAttractionDtoArray(LocationDto location, Double maxDistance, Integer limit) {
        if (limit == null) limit = 10;
        if (maxDistance == null) maxDistance = 1000D;
        Double finalMaxDistance = maxDistance;
        return IntStream.range(0, limit).mapToObj(i -> NearbyAttractionDto.builder()
                .attraction(generateAAttractionDto())
                .userLocation(location)
                .distance(0 + (finalMaxDistance - 0) * random.nextDouble())
                .rewardPoints(random.nextInt())
                .build())
                .toArray(NearbyAttractionDto[]::new);
    }

    public static Map<UUID, LocationDto> getLocationMapFromUserList(List<User> userList) {
        return CollectionUtil.notNullOrEmpty(userList).stream()
                .collect(Collectors.toMap(User::getUserId, e -> generateALocationDto(), (a, b) -> b));
    }

    public static List<NearbyAttractionDto> getNearbyAttractionListFromArray(NearbyAttractionDto[] array) {
        return Arrays.asList(array);
    }

    public static List<NearbyAttractionDto> getNearbyAttractionDtoList(LocationDto location, Double maxDistance, Integer limit) {
        if (limit == null) limit = 10;
        if (maxDistance == null) maxDistance = 1000D;
        Double finalMaxDistance = maxDistance;
        return IntStream.range(0, limit).mapToObj(i -> NearbyAttractionDto.builder()
                .attraction(generateAAttractionDto())
                .userLocation(location)
                .distance(0 + (finalMaxDistance - 0) * random.nextDouble())
                .rewardPoints(random.nextInt())
                .build()).collect(Collectors.toList());
    }

    public static List<User> generateAUserList(int number) {
        return IntStream.range(0, number).mapToObj(i -> generateAUser("test" + i)).collect(Collectors.toList());
    }

    public static User generateAUser(String userName) {
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

    private static List<VisitedLocation> generateAVisitedLocationsList(UUID uuid) {
        return IntStream.range(0, random.nextInt(3))
                .mapToObj(i -> generateAVisitedLocation(uuid)).collect(Collectors.toList());
    }

    public static List<VisitedLocation> generateAVisitedLocationsList(UUID uuid, int limit) {
        return IntStream.range(0, limit)
                .mapToObj(i -> generateAVisitedLocation(uuid)).collect(Collectors.toList());
    }

    public static VisitedLocation generateAVisitedLocation(UUID uuid) {
        return VisitedLocation.builder()
                .userId(uuid)
                .location(generateALocation())
                .timeVisited(new Date(ThreadLocalRandom.current().nextInt() * 1000L))
                .build();
    }

    public static VisitedLocationDto generateAVisitedLocationDto(UUID uuid) {
        return VisitedLocationDto.builder()
                .userId(uuid)
                .location(generateALocationDto())
                .timeVisited(new Date(ThreadLocalRandom.current().nextInt() * 1000L))
                .build();
    }

    public static Location generateALocation() {
        double longitudeLeftLimit = -180;
        double longitudeRightLimit = 180;
        double latitudeLeftLimit = -85.05112878;
        double latitudeRightLimit = 85.05112878;
        return Location.builder()
                .longitude(random.nextDouble() * (longitudeRightLimit - longitudeLeftLimit))
                .latitude(random.nextDouble() * (latitudeRightLimit - latitudeLeftLimit))
                .build();
    }

    public static LocationDto generateALocationDto() {
        double longitudeLeftLimit = -180;
        double longitudeRightLimit = 180;
        double latitudeLeftLimit = -85.05112878;
        double latitudeRightLimit = 85.05112878;
        return LocationDto.builder()
                .longitude(random.nextDouble() * (longitudeRightLimit - longitudeLeftLimit))
                .latitude(random.nextDouble() * (latitudeRightLimit - latitudeLeftLimit))
                .build();
    }

    private static List<UserReward> generateAUserRewardList(UUID uuid) {
        return IntStream.range(0, random.nextInt(10))
                .mapToObj(i -> UserReward.builder()
                        .visitedLocation(generateAVisitedLocation(uuid))
                        .attraction(generateAAttraction())
                        .rewardPoints(random.nextInt(10000))
                        .build()
                ).collect(Collectors.toList());
    }

    public static List<UserReward> generateAUserRewardList(UUID uuid, int limit) {
        return IntStream.range(0, limit)
                .mapToObj(i -> UserReward.builder()
                        .visitedLocation(generateAVisitedLocation(uuid))
                        .attraction(generateAAttraction())
                        .rewardPoints(random.nextInt(10000))
                        .build()
                ).collect(Collectors.toList());
    }

    public static List<UserRewardDto> generateAUserRewardDtoList(UUID uuid, int limit) {
        return IntStream.range(0, limit)
                .mapToObj(i -> UserRewardDto.builder()
                        .visitedLocation(generateAVisitedLocationDto(uuid))
                        .attraction(generateAAttractionDto())
                        .rewardPoints(random.nextInt(10000))
                        .build()
                ).collect(Collectors.toList());
    }

    public static UserReward generateAUserReward(UUID uuid) {
        return UserReward.builder()
                .visitedLocation(generateAVisitedLocation(uuid))
                .attraction(generateAAttraction())
                .rewardPoints(random.nextInt(10000))
                .build();
    }

    private static Attraction generateAAttraction() {
        return Attraction.builder()
                .attractionName("attractionTest" + random.nextInt(100))
                .city("city" + random.nextInt(100))
                .state("state" + random.nextInt(100))
                .attractionId(UUID.randomUUID())
                .build();
    }

    private static AttractionDto generateAAttractionDto() {
        return AttractionDto.builder()
                .attractionName("attractionTest" + random.nextInt(100))
                .city("city" + random.nextInt(100))
                .state("state" + random.nextInt(100))
                .attractionId(UUID.randomUUID())
                .build();
    }

    public static List<Provider> getProviderList() {
        return new ArrayList<>(Arrays.asList(
                new Provider(UUID.randomUUID(), "test1", 200),
                new Provider(UUID.randomUUID(), "test2", 1200),
                new Provider(UUID.randomUUID(), "test3", 300.02)
        ));
    }
}
