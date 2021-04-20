package com.tourguide.users.service.impl;

import com.tourguide.users.dto.LocationDto;
import com.tourguide.users.dto.VisitedLocationDto;
import com.tourguide.users.entity.User;
import com.tourguide.users.mapper.LocationMapper;
import com.tourguide.users.mapper.VisitedLocationMapper;
import com.tourguide.users.repository.UserRepository;
import com.tourguide.users.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final VisitedLocationMapper visitedLocationMapper;

    private final LocationMapper locationMapper;

    public UserServiceImpl(UserRepository userRepository, VisitedLocationMapper visitedLocationMapper, LocationMapper locationMapper) {
        this.userRepository = userRepository;
        this.visitedLocationMapper = visitedLocationMapper;
        this.locationMapper = locationMapper;
    }

    @Override
    public VisitedLocationDto getLastVisitedLocation(String userName) {
        if (!StringUtils.isBlank(userName)) {
            User user = userRepository.findUserByUserName(userName);
            return visitedLocationMapper.toLastVisitedLocationDto(user);
        }
        // TODO: 15/04/2021 "Add exception UserNameIsEmpty!"
        return null;
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
}
