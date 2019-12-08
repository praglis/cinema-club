package com.misernandfriends.cinemaclub.controller.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ErrorResponse extends ResponseEntity implements Serializable {

    ErrorResponse(Object object) {
        super(object, HttpStatus.BAD_REQUEST);
    }

    ErrorResponse(HttpStatus httpStatus, Object object) {
        super(object, httpStatus);
    }

    public static ErrorResponse createError(String message) {
        Map<String, String> map = new HashMap<>();
        map.put("message", message);
        return new ErrorResponse(map);
    }
}
