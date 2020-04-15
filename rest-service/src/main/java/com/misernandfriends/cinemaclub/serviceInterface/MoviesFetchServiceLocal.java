package com.misernandfriends.cinemaclub.serviceInterface;

import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.pojo.MoviesList;
import com.misernandfriends.cinemaclub.pojo.QuestionnaireMovieResponse;
import org.springframework.cache.annotation.Cacheable;

public interface MoviesFetchServiceLocal {
    @Cacheable("best")
    String getBestRatedMovies(Integer page);

    @Cacheable("popular")
    String getPopularMovies(Integer page);

    MoviesList getRecommendedMovies(UserDTO user, Integer page, String type);

    QuestionnaireMovieResponse getQuestionnaireMovies();
}
