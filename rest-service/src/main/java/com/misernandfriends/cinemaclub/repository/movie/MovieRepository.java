package com.misernandfriends.cinemaclub.repository.movie;

import com.misernandfriends.cinemaclub.model.movie.MovieDTO;
import com.misernandfriends.cinemaclub.repository.AbstractRepository;

public interface MovieRepository extends AbstractRepository<MovieDTO> {

    public MovieDTO getByApiUrl(String apiUrl);

}
