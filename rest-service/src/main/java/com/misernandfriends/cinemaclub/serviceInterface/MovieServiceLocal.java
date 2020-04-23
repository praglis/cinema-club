package com.misernandfriends.cinemaclub.serviceInterface;

import com.misernandfriends.cinemaclub.model.movie.MovieDTO;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.pojo.Rate;

public interface MovieServiceLocal {

    MovieDTO getMovie(String movieName);

    MovieDTO getMovie(Integer movieId);

    void rateMovie(String movieId, Rate rate, UserDTO userDTO);
}
