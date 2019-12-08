package com.misernandfriends.cinemaclub.serviceInterface;

import com.misernandfriends.cinemaclub.model.user.UserDTO;

import java.util.Optional;

public interface UserService {
    void register(UserDTO user);
    void changePassword(UserDTO user, String password);

    Optional<UserDTO> findByUsername(String username);
    Optional<UserDTO> findByEmail(String email);
}
