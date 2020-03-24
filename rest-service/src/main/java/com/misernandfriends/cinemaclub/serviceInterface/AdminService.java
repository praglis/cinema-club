package com.misernandfriends.cinemaclub.serviceInterface;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    ResponseEntity banUser(String userName);
}
