package com.tourguide.users.exceptions;

import lombok.Getter;

@Getter
public class InvalidUserNameException extends RuntimeException {

    public InvalidUserNameException(String userName) {
        super("UserName is invalid");
    }
}
