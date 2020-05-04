package com.misernandfriends.cinemaclub.repository.user;

import com.misernandfriends.cinemaclub.model.user.BadgeDTO;
import com.misernandfriends.cinemaclub.repository.AbstractRepository;

import java.util.Optional;

public interface BadgeRepository extends AbstractRepository<BadgeDTO> {
    Optional<BadgeDTO> findBadgeFromValue(int value);
}
