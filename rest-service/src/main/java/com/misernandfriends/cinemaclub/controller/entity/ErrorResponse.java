package com.misernandfriends.cinemaclub.controller.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

@Getter
@Setter
public class ErrorResponse extends ResponseEntity implements Serializable {

    ErrorResponse(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    ErrorResponse(HttpStatus httpStatus, String message) {
        super(message, httpStatus);
    }

    public static ErrorResponse createError(String message) {
        return new ErrorResponse(message);
    }
}
