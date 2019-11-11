package com.misernandfriends.cinemaclub.serviceInterface;

import com.misernandfriends.cinemaclub.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}