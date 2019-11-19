package com.misernandfriends.cinemaclub.repository.user;

import com.misernandfriends.cinemaclub.model.movie.MovieDTO;
import com.misernandfriends.cinemaclub.model.user.CategoryDTO;
import com.misernandfriends.cinemaclub.model.user.FavoriteMovieDTO;
import com.misernandfriends.cinemaclub.repository.AbstractRepository;

import java.util.List;

public interface FavoriteMovieRepository extends AbstractRepository<FavoriteMovieDTO> {

    public List<MovieDTO> getMovieForCategory(Long categoryId, Long userId);

    public List<MovieDTO> getMovieForCategory(Long categoryId);

    public FavoriteMovieDTO getFavoriteMovie(Long categoryId, Long userId, Long movieId);

    public CategoryDTO getMovieCategory(Long userId, Long movieId);

}
