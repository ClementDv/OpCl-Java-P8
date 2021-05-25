package com.tourguide.users.exceptions;

public class InvalidParamExceptions extends RuntimeException {

    public InvalidParamExceptions() {
        super("Invalid parameter(s)");
    }
}
