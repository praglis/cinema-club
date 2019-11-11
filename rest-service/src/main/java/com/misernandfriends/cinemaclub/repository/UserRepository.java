package com.misernandfriends.cinemaclub.repository;

import com.misernandfriends.cinemaclub.model.User;

public interface UserRepository extends AbstractRepository<User> {
    User findByUsername(String username);

}