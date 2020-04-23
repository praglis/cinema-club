package com.misernandfriends.cinemaclub.serviceInterface;

import com.misernandfriends.cinemaclub.pojo.Genres;
import com.misernandfriends.cinemaclub.pojo.Movie;

public interface MovieFetchServiceLocal {
    Movie getMovieById(Integer page);

    String getMovieByLongId(Long id);

    String getMovieByQuery(String query);

    String getMovieCreditsById(Integer id);

    Genres getAllGenres();
}
