package com.tourguide.users.service;

import com.tourguide.users.dto.LocationDto;
import com.tourguide.users.dto.UserRewardDto;
import com.tourguide.users.dto.VisitedLocationDto;
import com.tourguide.users.entity.User;
import com.tourguide.users.exceptions.InvalidUserNameException;
import com.tourguide.users.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface UserService {

    /**
     * Get the last location of a user
     *
     * @param username to identify the user
     * @return a <b>visitedLocation</b> (location, date, userId),
     * if user does not have visitedLocation <b>null</b> is returned.
     */
    VisitedLocationDto getLastVisitedLocation(String username);

    /**
     * Get all Location of Users
     *
     * @return a <b>Map of location Dto associate to user id</b> {key : user id (UUID), value : location},
     * if all user have an empty visitedLocation list and <b>empty list</b> is returned.
     */
    Map<UUID, LocationDto> getAllCurrentLocation();

    /**
     * Get the rewards of a user
     *
     * @param userName to identify the user
     * @return a <b>list of Dto : userReward</b> (attraction, location, rewardPoint),
     * if all user have an empty userReward list and <b>empty list</b> is returned
     */
    List<UserRewardDto> getUserRewards(String userName);

    /**
     * Get a User by Username
     *
     * @param userName to identify the user
     * @return a <b>User entity</b>
     * @throws InvalidUserNameException if the userName is empty or null
     * @throws UserNotFoundException    if the user doesn't exist
     */
    User getUserByUsername(String userName);
}
