package com.misernandfriends.cinemaclub.serviceInterface;

import org.springframework.cache.annotation.Cacheable;

public interface MoviesFetchServiceLocal {
    @Cacheable("best")
    String getBestRatedMovies(Integer page);
    @Cacheable("popular")
    String getPopularMovies(Integer page);
}
