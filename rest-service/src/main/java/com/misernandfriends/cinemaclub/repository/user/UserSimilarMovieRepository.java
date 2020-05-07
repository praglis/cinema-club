package com.misernandfriends.cinemaclub.repository.user;

import com.misernandfriends.cinemaclub.model.movie.MovieDTO;
import com.misernandfriends.cinemaclub.model.user.UserSimilarMovieDTO;
import com.misernandfriends.cinemaclub.repository.AbstractRepository;

import java.util.List;

public interface UserSimilarMovieRepository extends AbstractRepository<UserSimilarMovieDTO> {
    List<MovieDTO> getForUser(Long userId);
    void clearForUser(Long userId);
}
