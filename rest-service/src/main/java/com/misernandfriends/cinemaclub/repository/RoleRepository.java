package com.misernandfriends.cinemaclub.repository;

import com.misernandfriends.cinemaclub.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}