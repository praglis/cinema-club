package com.misernandfriends.cinemaclub.serviceInterface.movie;

import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.pojo.movie.review.Rate;

public interface CinemaService {

    void rateCinema(String cinemaId, Rate rate, UserDTO userDTO);

}
