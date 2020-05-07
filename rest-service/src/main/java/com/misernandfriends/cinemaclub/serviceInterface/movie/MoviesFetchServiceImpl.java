package com.misernandfriends.cinemaclub.serviceInterface.movie;

import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.pojo.movie.MoviesList;
import com.misernandfriends.cinemaclub.pojo.rec.QuestionnaireMovieResponse;
import org.springframework.cache.annotation.Cacheable;

public interface MoviesFetchServiceImpl {
    @Cacheable("best")
    MoviesList getBestRatedMovies(Integer page);

    @Cacheable("popular")
    MoviesList getPopularMovies(Integer page);

    MoviesList getRecommendedMovies(UserDTO user, Integer page, String type);
    QuestionnaireMovieResponse getQuestionnaireMovies();
}
