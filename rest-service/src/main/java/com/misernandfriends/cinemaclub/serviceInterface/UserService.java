package com.misernandfriends.cinemaclub.serviceInterface;

import com.misernandfriends.cinemaclub.model.user.BadgeDTO;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void register(UserDTO user);

    void changePassword(UserDTO user, String password);

    ResponseEntity updateProfile(UserDTO user, Optional userFormDB);

    Optional<UserDTO> findByUsername(String username);

    Optional<UserDTO> findByEmail(String email);

    List<String> getAllUsers();

    List<String> getAllAdminEmails();

    ResponseEntity resetPassword(UserDTO userDTO);

    BadgeDTO getBadge(UserDTO userDTO);

    boolean isAdminUser(UserDTO userDTO);
}
