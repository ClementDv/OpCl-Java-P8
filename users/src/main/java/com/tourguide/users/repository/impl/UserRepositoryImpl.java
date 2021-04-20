package com.tourguide.users.repository.impl;

import com.tourguide.users.entity.Location;
import com.tourguide.users.entity.User;
import com.tourguide.users.entity.VisitedLocation;
import com.tourguide.users.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private HashMap<String, User> userMap;

    public UserRepositoryImpl() {
        this.userMap = new HashMap<>(Map.of(
                "jean",
                User.builder().userName("jean").userId(UUID.randomUUID()).visitedLocations(new ArrayList<>(Arrays.asList(
                        VisitedLocation.builder().timeVisited(new Date(System.currentTimeMillis() - 200000000000L)).userId(UUID.randomUUID()).location(null).build(),
                        VisitedLocation.builder().timeVisited(new Date(System.currentTimeMillis() - 100000000000L)).userId(UUID.randomUUID()).location(null).build(),
                        VisitedLocation.builder().timeVisited(new Date(System.currentTimeMillis())).userId(UUID.randomUUID()).location(
                                Location.builder().longitude(-350).latitude(350).build()
                        ).build()
                ))).build(),
                "test",
                User.builder().userName("test").userId(UUID.randomUUID()).visitedLocations(new ArrayList<>(Arrays.asList(
                        VisitedLocation.builder().timeVisited(new Date(System.currentTimeMillis() - 200000000000L)).userId(UUID.randomUUID()).location(null).build(),
                        VisitedLocation.builder().timeVisited(new Date(System.currentTimeMillis() - 100000000000L)).userId(UUID.randomUUID()).location(
                                Location.builder().longitude(-200).latitude(200).build()
                        ).build(),
                        VisitedLocation.builder().timeVisited(new Date(System.currentTimeMillis())).userId(UUID.randomUUID()).location(
                                Location.builder().longitude(-300).latitude(300).build()
                        ).build()
                ))).build()));
    }

    @Override
    public User findUserByUserName(String userName) {
        return cloneUser(userMap.get(userName));
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public ArrayList<User> getAllUser() {
        return new ArrayList<>(userMap.values());
    }

    User cloneUser(User user) {
        if (user == null) {
            return null;
        }
        return User.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .phoneNumber(user.getPhoneNumber())
                .emailAddress(user.getEmailAddress())
                .visitedLocations(user.getVisitedLocations().stream().map(this::cloneVisitedLocation).collect(Collectors.toList()))
                .build();
    }

    VisitedLocation cloneVisitedLocation(VisitedLocation visitedLocation) {
        if (visitedLocation != null) {
            return VisitedLocation.builder()
                    .userId(visitedLocation.getUserId())
                    .location(cloneLocation(visitedLocation.getLocation()))
                    .timeVisited(visitedLocation.getTimeVisited())
                    .build();
        }
        return null;
    }

    Location cloneLocation(Location location) {
        if (location != null) {
            return Location.builder()
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude())
                    .build();
        }
        return null;
    }
}
