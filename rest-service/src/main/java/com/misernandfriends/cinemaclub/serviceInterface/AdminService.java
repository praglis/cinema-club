package com.misernandfriends.cinemaclub.serviceInterface;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminService {
    ResponseEntity banUser(String userName);
    List<String> getAllUsers ();
}
