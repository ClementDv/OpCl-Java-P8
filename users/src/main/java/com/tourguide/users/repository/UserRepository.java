package com.tourguide.users.repository;

import com.tourguide.users.entity.User;
import com.tourguide.users.entity.UserReward;
import com.tourguide.users.entity.VisitedLocation;

import java.util.List;

public interface UserRepository {

    User findUserByUserName(String userName);

    List<User> getAllUser();

    boolean addUserVisitedLocation(String userName, VisitedLocation lastVisitedLocation);

    boolean addUserReward(String userName, UserReward userRewardToAdd);
}
