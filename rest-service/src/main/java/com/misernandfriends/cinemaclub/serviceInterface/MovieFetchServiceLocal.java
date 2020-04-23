package com.misernandfriends.cinemaclub.serviceInterface;

import com.misernandfriends.cinemaclub.pojo.Credits;
import com.misernandfriends.cinemaclub.pojo.Genres;
import com.misernandfriends.cinemaclub.pojo.Movie;

public interface MovieFetchServiceLocal {
    Movie getMovieById(Integer page);

    String getMovieByLongId(Long id);

    String getMovieByQuery(String query);

    Credits getMovieCreditsById(Integer id);

    Genres getAllGenres();
}
