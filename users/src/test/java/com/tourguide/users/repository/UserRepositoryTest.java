package com.tourguide.users.repository;

import com.tourguide.users.data.TestData;
import com.tourguide.users.entity.User;
import com.tourguide.users.entity.UserReward;
import com.tourguide.users.entity.VisitedLocation;
import com.tourguide.users.exceptions.InvalidParamExceptions;
import com.tourguide.users.exceptions.UserNotFoundException;
import com.tourguide.users.repository.impl.UserRepositoryImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepositoryImpl userRepository;

    @BeforeEach
    void init() {
        userRepository.setUsersCount(10);
    }

    @Test
    public void findUserByUserNameTest() {
        // existing user

        Assertions.assertThat(userRepository.findUserByUserName("userName : 9"))
                .hasFieldOrPropertyWithValue("userName", "userName : 9");

        // non existing user

        Assertions.assertThat(userRepository.findUserByUserName("test")).isNull();
    }

    @Test
    public void getAllUserTest() {
        IntStream.range(0, 10).forEach(i ->
        Assertions.assertThat(userRepository.getAllUser()).extracting(User::getUserName).contains("userName : " + i));
    }

    @Test
    public void addUserVisitedLocationsTest() {
        VisitedLocation visitedLocation = TestData.generateAVisitedLocation(UUID.randomUUID());
        userRepository.addUserVisitedLocation("userName : 9", visitedLocation);
        Assertions.assertThat(userRepository.userMap.get("userName : 9").getVisitedLocations()).contains(visitedLocation);

        // invalidParam
        Assertions.assertThatThrownBy(() -> userRepository.addUserVisitedLocation(null, null))
                .isInstanceOf(InvalidParamExceptions.class);

        // UserNotFound
        Assertions.assertThatThrownBy(() -> userRepository.addUserVisitedLocation("test", TestData.generateAVisitedLocation(UUID.randomUUID())))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    public void addUserRewardTest() {
        UserReward userReward = TestData.generateAUserReward(UUID.randomUUID());
        userRepository.addUserReward("userName : 3", userReward);
        Assertions.assertThat(userRepository.userMap.get("userName : 3").getUserRewards()).contains(userReward);

        // invalidParam
        Assertions.assertThatThrownBy(() -> userRepository.addUserReward(null, null))
                .isInstanceOf(InvalidParamExceptions.class);

        // UserNotFound
        Assertions.assertThatThrownBy(() -> userRepository.addUserReward("test", TestData.generateAUserReward(UUID.randomUUID())))
                .isInstanceOf(UserNotFoundException.class);
    }
}
