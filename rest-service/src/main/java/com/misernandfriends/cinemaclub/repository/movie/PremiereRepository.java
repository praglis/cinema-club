package com.misernandfriends.cinemaclub.repository.movie;

import com.misernandfriends.cinemaclub.model.cinema.CinemaDTO;
import com.misernandfriends.cinemaclub.model.movie.MovieDTO;
import com.misernandfriends.cinemaclub.model.movie.PremiereDTO;
import com.misernandfriends.cinemaclub.repository.AbstractRepository;

import java.util.List;
import java.util.Map;

public interface PremiereRepository extends AbstractRepository<PremiereDTO> {

    List<CinemaDTO> getCinemasForMovie(Long movieId);

    List<MovieDTO> getMoviesForCinema(Long cinemaId);

    boolean isPremierePresent(PremiereDTO premiere);

    Object searchFor(Long cinemaId, Map<String, String> params);
}
