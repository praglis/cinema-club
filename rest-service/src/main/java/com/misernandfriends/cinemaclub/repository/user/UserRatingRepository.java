package com.misernandfriends.cinemaclub.repository.user;

import com.misernandfriends.cinemaclub.model.user.UserRatingDTO;
import com.misernandfriends.cinemaclub.repository.AbstractRepository;

import java.util.Optional;

public interface UserRatingRepository extends AbstractRepository<UserRatingDTO> {

    double getAvgRatingForMovie(Long movieId);

    Optional<UserRatingDTO> getByUser(Long userId, String movieApi);
}
