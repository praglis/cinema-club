package com.misernandfriends.cinemaclub.serviceInterface.rec;

import com.misernandfriends.cinemaclub.model.movie.MovieDTO;
import com.misernandfriends.cinemaclub.model.user.RecommendationDTO;
import com.misernandfriends.cinemaclub.model.user.UserDTO;

import java.util.List;

public interface RecommendationService {
    List<String> getValues(UserDTO userDTO, String type);
    List<MovieDTO> getMovieBaseOnTaste(UserDTO user);
    List<RecommendationDTO> getRecommendation(UserDTO user, String type, int maxResult);
    void processMovie(UserDTO user, MovieDTO movie);
    void processMovieAsync(UserDTO user, MovieDTO movie);
    void refreshSimilarMovies(UserDTO userDTO);
}
