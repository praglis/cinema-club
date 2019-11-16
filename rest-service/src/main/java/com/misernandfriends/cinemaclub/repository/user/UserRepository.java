package com.misernandfriends.cinemaclub.repository.user;

import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.repository.AbstractRepository;

public interface UserRepository extends AbstractRepository<UserDTO> {
    UserDTO findByUsername(String username);

}