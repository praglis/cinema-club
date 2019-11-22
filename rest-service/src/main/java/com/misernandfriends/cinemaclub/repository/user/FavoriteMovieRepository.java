package com.misernandfriends.cinemaclub.repository.user;

import com.misernandfriends.cinemaclub.model.movie.MovieDTO;
import com.misernandfriends.cinemaclub.model.user.CategoryDTO;
import com.misernandfriends.cinemaclub.model.user.FavoriteMovieDTO;
import com.misernandfriends.cinemaclub.repository.AbstractRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteMovieRepository extends AbstractRepository<FavoriteMovieDTO> {

    List<MovieDTO> getMovieForCategory(Long categoryId, Long userId);

    List<MovieDTO> getMovieForCategory(Long categoryId);

    Optional<FavoriteMovieDTO> getFavoriteMovie(Long categoryId, Long userId, Long movieId);

    Optional<CategoryDTO> getMovieCategory(Long userId, Long movieId);

}
