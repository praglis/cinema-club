package com.misernandfriends.cinemaclub.serviceInterface;

import com.misernandfriends.cinemaclub.pojo.Genres;
import com.misernandfriends.cinemaclub.pojo.MovieSearchCriteria;

public interface MovieFetchServiceLocal {
    String getMovieById(Integer page);

    String getMovieByLongId(Long id);

    String getMovieByQuery(String query);

    String getMovieCreditsById(Integer id);

    Genres getAllGenres();

    String getMovies(MovieSearchCriteria criteria);
}
