package com.misernandfriends.cinemaclub.repository.user;

import com.misernandfriends.cinemaclub.repository.AbstractRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface UserRatingRepository<T extends Serializable> extends AbstractRepository<T> {
    Optional<T> getByUser(Long userId, String referenceValue);
    List<T> getUserBestRated(Long id, int maxResult);
}
