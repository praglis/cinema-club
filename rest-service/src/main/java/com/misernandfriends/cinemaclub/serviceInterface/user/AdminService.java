package com.misernandfriends.cinemaclub.serviceInterface.user;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    ResponseEntity<Object> activeUser(String userName);
    ResponseEntity<Object> banUser(String userName);
    ResponseEntity<Object> blockUser(String userName);
    ResponseEntity<Object> deleteUser(String userName);
}
