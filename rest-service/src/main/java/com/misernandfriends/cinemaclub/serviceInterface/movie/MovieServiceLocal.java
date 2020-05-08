package com.misernandfriends.cinemaclub.serviceInterface.movie;

import com.misernandfriends.cinemaclub.model.movie.MovieDTO;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.pojo.movie.review.Rate;

public interface MovieServiceLocal {
    MovieDTO getMovieData(String movieName);
    MovieDTO getMovieData(Integer movieId);
    void rateMovie(String movieId, Rate rate, UserDTO userDTO);
}
