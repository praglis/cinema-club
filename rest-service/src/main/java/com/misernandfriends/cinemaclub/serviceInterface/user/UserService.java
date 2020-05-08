package com.misernandfriends.cinemaclub.serviceInterface.user;

import com.misernandfriends.cinemaclub.model.user.BadgeDTO;
import com.misernandfriends.cinemaclub.model.user.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    BadgeDTO getBadge(UserDTO userDTO);
    List<String> getAllUsers();
    List<String> getAllAdminEmails();
    Optional<UserDTO> findByEmail(String email);
    Optional<UserDTO> findByUsername(String username);
    boolean isAdminUser(UserDTO userDTO);
    void changePassword(UserDTO user, String password);
    void register(UserDTO user);
    void resetPassword(UserDTO userDTO);
    void updateProfile(UserDTO user, UserDTO userFormDB);
}
