package com.misernandfriends.cinemaclub.serviceInterface.movie;

import com.misernandfriends.cinemaclub.pojo.movie.crew.Credits;
import com.misernandfriends.cinemaclub.pojo.movie.Genres;
import com.misernandfriends.cinemaclub.pojo.movie.MovieSearchCriteria;
import com.misernandfriends.cinemaclub.pojo.movie.Movie;
import com.misernandfriends.cinemaclub.pojo.movie.MoviesList;

public interface MovieDetailService {
    Genres getGenres();
    Credits getMovieCreditsById(Integer id);
    Movie getMovieById(Integer page);
    Movie getMovieByLongId(Long id);
    MoviesList getMovieByQuery(String query);
    MoviesList getMovies(MovieSearchCriteria criteria);
}
