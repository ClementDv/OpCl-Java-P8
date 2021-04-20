package com.tourguide.users.exceptions;

import com.tourguide.users.dto.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

import static com.tourguide.users.exceptions.ErrorCodesEnum.INVALID_USERNAME;
import static com.tourguide.users.exceptions.ErrorCodesEnum.USER_NOT_FOUND;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e) {
        return response(ErrorResponse.builder()
                .status(USER_NOT_FOUND.getStatus())
                .code(USER_NOT_FOUND.getCode())
                .message(e.getMessage())
                .metadata(new HashMap<>())
                .build()
                .withMetadata("userName", e.getUserName()));
    }

    @ExceptionHandler(InvalidUserNameException.class)
    @ResponseBody
    public ResponseEntity<?> handleInvalidUserNameException(InvalidUserNameException e) {
        return response(ErrorResponse.builder()
                .status(INVALID_USERNAME.getStatus())
                .code(INVALID_USERNAME.getCode())
                .message(e.getMessage())
                .build());
    }

    protected ResponseEntity<ErrorResponse> response(ErrorResponse errorResponse) {
        HttpStatus status =  HttpStatus.valueOf(errorResponse.getStatus());
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), status);
    }
}
