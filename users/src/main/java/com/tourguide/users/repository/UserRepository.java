package com.tourguide.users.repository;

import com.tourguide.users.entity.User;
import com.tourguide.users.entity.UserReward;
import com.tourguide.users.entity.VisitedLocation;
import com.tourguide.users.exceptions.InvalidParamExceptions;
import com.tourguide.users.exceptions.UserNotFoundException;

import java.util.List;

public interface UserRepository {

    /**
     * Get a User entity by a username,
     *
     * @param userName to identify the user
     * @return a User entity
     */
    User findUserByUserName(String userName);

    /**
     *  Get all user Entity in a list.
     *
     * @return a list of User entity
     */
    List<User> getAllUser();

    /**
     * Add a visitedLocation to a user
     *
     * @param userName to identify the user
     * @param lastVisitedLocation location to add
     * @throws InvalidParamExceptions if parameters are null
     * @throws UserNotFoundException if users isn't found
     */
    void addUserVisitedLocation(String userName, VisitedLocation lastVisitedLocation);

    /**
     * Add a Reward to a user.
     *
     * @param userName to identify the user
     * @param userRewardToAdd reward to add
     * @throws InvalidParamExceptions if parameters are null
     * @throws UserNotFoundException if users isn't found
     */
    void addUserReward(String userName, UserReward userRewardToAdd);
}
