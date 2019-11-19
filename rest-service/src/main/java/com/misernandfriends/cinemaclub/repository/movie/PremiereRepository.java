package com.misernandfriends.cinemaclub.repository.movie;

import com.misernandfriends.cinemaclub.model.cinema.CinemaDTO;
import com.misernandfriends.cinemaclub.model.movie.MovieDTO;
import com.misernandfriends.cinemaclub.model.movie.PremiereDTO;
import com.misernandfriends.cinemaclub.repository.AbstractRepository;

import java.util.List;

public interface PremiereRepository extends AbstractRepository<PremiereDTO> {

    public List<CinemaDTO> getCinemasForMovie(Long movieId);

    public List<MovieDTO> getMoviesForCinema(Long cinemaId);

}
