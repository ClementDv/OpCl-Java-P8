package com.tourguide.users.repository;

import com.tourguide.users.entity.User;

import java.util.ArrayList;

public interface UserRepository {

    User findUserByUserName(String userName);

    User save(User user);

    ArrayList<User> getAllUser();
}
