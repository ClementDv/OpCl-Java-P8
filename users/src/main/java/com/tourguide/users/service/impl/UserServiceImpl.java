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
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
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
            return visitedLocationMapper.toLastVisitedLocationDto(user);
    }

    @Override
    public Map<UUID, LocationDto> getAllCurrentLocation() {
        List<User> userList = userRepository.getAllUser();
        Map<UUID, LocationDto> mapUserLocation = new HashMap<>();
        userList.forEach(
                e -> mapUserLocation.put(
                        e.getUserId(), visitedLocationMapper.toLastVisitedLocationDto(e).getLocation()));
        return mapUserLocation;
    }

    @Override
    public List<UserRewardDto> getUserRewards(String userName) {
            User user = getUserByUsername(userName);
            List<UserRewardDto> userRewardDtoList = new ArrayList<>();
            if (!CollectionUtils.isEmpty(user.getUserRewards())) {
                user.getUserRewards().forEach(e -> userRewardDtoList.add(userRewardMapper.toDto(e)));
                return userRewardDtoList;
            }
            return null;
    }

    private User getUserByUsername(String userName) {
        if (!StringUtils.isBlank(userName)) {
            User user = userRepository.findUserByUserName(userName);
            if (!Objects.isNull(user)) {
                return user;
            }
            throw new UserNotFoundException(userName);
        }
        throw new InvalidUserNameException("UserName is empty or null");
    }
}
