package com.tourguide.users.service;

import com.google.common.collect.Iterables;
import com.tourguide.users.data.TestData;
import com.tourguide.users.dto.LocationDto;
import com.tourguide.users.dto.UserRewardDto;
import com.tourguide.users.dto.VisitedLocationDto;
import com.tourguide.users.entity.User;
import com.tourguide.users.exceptions.InvalidUserNameException;
import com.tourguide.users.exceptions.UserNotFoundException;
import com.tourguide.users.mapper.LocationMapper;
import com.tourguide.users.mapper.UserRewardMapper;
import com.tourguide.users.mapper.VisitedLocationMapper;
import com.tourguide.users.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@SpringBootTest
@Slf4j
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private LocationMapper locationMapper;

    @Autowired
    private VisitedLocationMapper visitedLocationMapper;

    @Autowired
    private UserRewardMapper userRewardMapper;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void getLastVisitedLocationTest() {
        User user = TestData.generateAUser("test");
        Mockito.when(userRepository.findUserByUserName(Mockito.eq("test"))).thenReturn(user);

        user.setVisitedLocations(TestData.generateAVisitedLocationsList(user.getUserId(), 3));
        VisitedLocationDto lastVisitedLocation = visitedLocationMapper.toDto(Iterables.getLast(user.getVisitedLocations()));
        Assertions.assertThat(userService.getLastVisitedLocation("test")).isEqualTo(lastVisitedLocation);

        // Empty Visited locations

        user.setVisitedLocations(Collections.emptyList());
        Assertions.assertThat(userService.getLastVisitedLocation("test")).isEqualTo(null);

        // Null Visited locations

        user.setVisitedLocations(null);
        Assertions.assertThat(userService.getLastVisitedLocation("test")).isEqualTo(null);
    }

    @Test
    public void getALlCurrentLocationTest() {
        List<User> userList = TestData.generateAUserList(3);
        Mockito.when(userRepository.getAllUser()).thenReturn(userList);

        Map<UUID, LocationDto> result = userService.getAllCurrentLocation();

        userList.forEach(u -> {
                    if (u.getVisitedLocations().isEmpty()) {
                        Assertions.assertThat(result).containsEntry(u.getUserId(), LocationDto.builder().build());
                    } else {
                        Assertions.assertThat(result).containsEntry(u.getUserId(),
                                locationMapper.toDto(Iterables.getLast(u.getVisitedLocations()).getLocation()));
                    }
                }
        );

        // Empty

        Mockito.when(userRepository.getAllUser()).thenReturn(Collections.emptyList());
        Assertions.assertThat(userService.getAllCurrentLocation()).isEqualTo(Collections.emptyMap());

        // Null

        Mockito.when(userRepository.getAllUser()).thenReturn(null);
        Assertions.assertThat(userService.getAllCurrentLocation()).isEqualTo(Collections.emptyMap());
    }

    @Test
    public void getUserRewardsTest() {
        User user = TestData.generateAUser("test");
        Mockito.when(userRepository.findUserByUserName(Mockito.eq("test"))).thenReturn(user);

        user.setUserRewards(TestData.generateAUserRewardList(user.getUserId(), 3));

        List<UserRewardDto> userRewardDto = user.getUserRewards().stream().map(userRewardMapper::toDto).collect(Collectors.toList());
        Assertions.assertThat(userService.getUserRewards("test")).isEqualTo(userRewardDto);

        // EMPTY

        user.setUserRewards(Collections.emptyList());
        Assertions.assertThat(userService.getUserRewards("test")).isEqualTo(Collections.emptyList());

        // Null

        user.setUserRewards(null);
        Assertions.assertThat(userService.getUserRewards("test")).isEqualTo(Collections.emptyList());
    }

    @Test
    public void getUserByUsername() {

        User user = TestData.generateAUser("test");
        Mockito.when(userRepository.findUserByUserName("test")).thenReturn(user);
        Assertions.assertThat(userService.getUserByUsername("test")).isEqualTo(user);

        // Wrong Username

        Assertions.assertThatThrownBy(() -> userService.getUserByUsername("")).isInstanceOf(InvalidUserNameException.class);

        // User not found

        Mockito.when(userRepository.findUserByUserName(Mockito.anyString())).thenReturn(null);
        Assertions.assertThatThrownBy(() -> userService.getUserByUsername("test")).isInstanceOf(UserNotFoundException.class);
    }
}
