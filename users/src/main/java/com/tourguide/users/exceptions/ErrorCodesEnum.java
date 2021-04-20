package com.tourguide.users.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCodesEnum {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "USER_NOT_FOUND"),
    INVALID_USERNAME(HttpStatus.BAD_REQUEST.value(), "INVALID_USERNAME");

    private final int status;
    private final String code;
}
