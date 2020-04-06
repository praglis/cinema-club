package com.misernandfriends.cinemaclub.repository.user;

import com.misernandfriends.cinemaclub.model.user.UserRatingDTO;
import com.misernandfriends.cinemaclub.repository.AbstractRepository;

import java.util.Optional;

import java.util.List;

public interface UserRatingRepository extends AbstractRepository<UserRatingDTO> {

    double getAvgRatingForMovie(Long movieId);

    Optional<UserRatingDTO> getByUser(Long userId, String movieApi);
    List<UserRatingDTO> getUserBestRatedMovies(Long id, int maxResult);
}
