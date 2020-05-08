package com.misernandfriends.cinemaclub.repository.movie;

import com.misernandfriends.cinemaclub.model.movie.MovieDTO;
import com.misernandfriends.cinemaclub.repository.AbstractRepository;

import java.util.Optional;

public interface MovieRepository extends AbstractRepository<MovieDTO> {
    Optional<MovieDTO> getByApiUrl(String apiUrl);
    Optional<MovieDTO> getByTitle(String title);
}
