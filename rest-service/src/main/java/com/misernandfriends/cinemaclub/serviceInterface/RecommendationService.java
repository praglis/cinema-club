package com.misernandfriends.cinemaclub.serviceInterface;

import com.misernandfriends.cinemaclub.model.movie.MovieDTO;
import com.misernandfriends.cinemaclub.model.user.RecommendationDTO;
import com.misernandfriends.cinemaclub.model.user.UserDTO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface RecommendationService {

    void processMovie(UserDTO user, MovieDTO movie);

    void getMovieBaseOnTaste(UserDTO user);

    void processMovieAsync(UserDTO user, MovieDTO movie);

    List<RecommendationDTO> getRecommendation(UserDTO user, String type, int maxResult);

    List<String> getValues(UserDTO userDTO, String type);
}
