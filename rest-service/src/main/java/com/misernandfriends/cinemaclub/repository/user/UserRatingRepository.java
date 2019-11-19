package com.misernandfriends.cinemaclub.repository.user;

import com.misernandfriends.cinemaclub.model.user.UserRatingDTO;
import com.misernandfriends.cinemaclub.repository.AbstractRepository;

public interface UserRatingRepository extends AbstractRepository<UserRatingDTO> {

    public double getAvgRatingForMovie(Long movieId);

}
