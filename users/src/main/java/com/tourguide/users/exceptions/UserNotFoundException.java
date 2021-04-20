package com.tourguide.users.exceptions;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {

    private String userName;

    public UserNotFoundException(String userName) {
        super("User not found");
        this.userName = userName;
    }
}
