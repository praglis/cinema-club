package com.misernandfriends.cinemaclub.serviceInterface;

import com.misernandfriends.cinemaclub.model.user.UserDTO;

import java.util.Optional;

public interface UserService {
    void save(UserDTO user);

    Optional<UserDTO> findByUsername(String username);
}