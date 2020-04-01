package com.misernandfriends.cinemaclub.serviceInterface;

import com.misernandfriends.cinemaclub.model.user.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void register(UserDTO user);
    void changePassword(UserDTO user, String password);

    void updateProfile(UserDTO user, Optional userFormDB);

    Optional<UserDTO> findByUsername(String username);
    Optional<UserDTO> findByEmail(String email);
    List<String> getAllUsers();
}
