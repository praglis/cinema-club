package com.misernandfriends.cinemaclub.repository.user;

import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.repository.AbstractRepository;

import java.util.Optional;

public interface UserRepository extends AbstractRepository<UserDTO> {
    Optional<UserDTO> findByUsername(String username);

}