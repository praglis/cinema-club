package com.misernandfriends.cinemaclub.serviceInterface;

public interface MovieFetchServiceLocal {
    String getMovieById(Integer page);
    String getMovieByQuery(String query);
}
