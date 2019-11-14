package com.misernandfriends.cinemaclub.repository;

import com.misernandfriends.cinemaclub.model.UserDTO;

public interface UserRepository extends AbstractRepository<UserDTO> {
    UserDTO findByUsername(String username);

}