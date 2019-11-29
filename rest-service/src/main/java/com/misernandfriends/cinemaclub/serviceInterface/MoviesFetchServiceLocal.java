package com.misernandfriends.cinemaclub.serviceInterface;

public interface MoviesFetchServiceLocal {
    String getBestRatedMovies(Integer page);
    String getPopularMovies(Integer page);
}
