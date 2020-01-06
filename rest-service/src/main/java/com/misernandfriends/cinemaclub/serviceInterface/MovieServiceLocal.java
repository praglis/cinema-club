package com.misernandfriends.cinemaclub.serviceInterface;

import com.misernandfriends.cinemaclub.model.movie.MovieDTO;

public interface MovieServiceLocal {

    MovieDTO getMovie(String movieName);

}
