package com.misernandfriends.cinemaclub.serviceInterface;

import com.misernandfriends.cinemaclub.model.user.UserDTO;

public interface UserService {
    void save(UserDTO user);

    UserDTO findByUsername(String username);
}