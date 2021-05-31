package com.tourguide.users.service.impl;

import com.tourguide.users.dto.LocationDto;
import com.tourguide.users.dto.UserRewardDto;
import com.tourguide.users.dto.VisitedLocationDto;
import com.tourguide.users.entity.User;
import com.tourguide.users.exceptions.InvalidUserNameException;
import com.tourguide.users.exceptions.UserNotFoundException;
import com.tourguide.users.mapper.UserRewardMapper;
import com.tourguide.users.mapper.VisitedLocationMapper;
import com.tourguide.users.repository.UserRepository;
import com.tourguide.users.service.UserService;
import com.tourguide.users.util.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final VisitedLocationMapper visitedLocationMapper;

    private final UserRewardMapper userRewardMapper;


    public UserServiceImpl(UserRepository userRepository, VisitedLocationMapper visitedLocationMapper, UserRewardMapper userRewardMapper) {
        this.userRepository = userRepository;
        this.visitedLocationMapper = visitedLocationMapper;
        this.userRewardMapper = userRewardMapper;
    }

    @Override
    public VisitedLocationDto getLastVisitedLocation(String userName) {
        User user = getUserByUsername(userName);
        log.debug("User service success : get last visited location");
        return visitedLocationMapper.toLastVisitedLocationDto(user);
    }

    @Override
    public Map<UUID, LocationDto> getAllCurrentLocation() {
        List<User> userList = userRepository.getAllUser();
        log.debug("User service success : get all current location");
        return CollectionUtil.notNullOrEmpty(userList).stream()
                .collect(Collectors.toMap(User::getUserId, e -> visitedLocationMapper.toLastVisitedLocationDto(e) == null ? LocationDto.builder().build()
                        : visitedLocationMapper.toLastVisitedLocationDto(e).getLocation(), (a, b) -> b));
    }

    @Override
    public List<UserRewardDto> getUserRewards(String userName) {
        User user = getUserByUsername(userName);
        log.debug("User service success : get user rewards for username {}", userName);
        return CollectionUtil.notNullOrEmpty(user.getUserRewards())
                .stream().map(userRewardMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public User getUserByUsername(String userName) {
        if (StringUtils.isBlank(userName)) {
            throw new InvalidUserNameException("UserName is empty or null");
        }
        User user = userRepository.findUserByUserName(userName);
        if (Objects.isNull(user)) {
            throw new UserNotFoundException(userName);
        }
        return user;
    }
}
