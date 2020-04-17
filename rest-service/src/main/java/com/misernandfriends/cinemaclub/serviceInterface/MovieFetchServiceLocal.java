package com.misernandfriends.cinemaclub.serviceInterface;

import com.misernandfriends.cinemaclub.pojo.Genres;

public interface MovieFetchServiceLocal {
    String getMovieById(Integer page);

    String getMovieByLongId(Long id);

    String getMovieByQuery(String query);

    String getMovieCreditsById(Integer id);

    Genres getAllGenres();
}
