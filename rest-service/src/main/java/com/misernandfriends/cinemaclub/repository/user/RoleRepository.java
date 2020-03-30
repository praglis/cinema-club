package com.misernandfriends.cinemaclub.repository.user;

import com.misernandfriends.cinemaclub.model.user.RoleDTO;
import com.misernandfriends.cinemaclub.repository.AbstractRepository;

import java.util.Optional;

public interface RoleRepository extends AbstractRepository<RoleDTO> {
    Optional<RoleDTO> findByName(String name);
}
