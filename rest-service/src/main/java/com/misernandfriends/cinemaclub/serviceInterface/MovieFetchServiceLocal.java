package com.misernandfriends.cinemaclub.serviceInterface;

public interface MovieFetchServiceLocal {
    String getMovieById(Integer page);
    String getMovieByLongId(Long id);
    String getMovieByQuery(String query);
}
