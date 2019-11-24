package com.misernandfriends.cinemaclub.service;

import com.misernandfriends.cinemaclub.serviceInterface.MoviesAPIService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MoviesAPIServiceImpl implements MoviesAPIService {
    @Override
    public String getBestRatedMovies() {
        final String uri = "https://api.themoviedb.org/3/movie/top_rated?api_key=08529ee2a8448b81018b7519148033ed&language=pl-PL&page=1";

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, String.class);
    }

    @Override
    public String getPopularMovies() {
        final String uri = "https://api.themoviedb.org/3/movie/popular?api_key=08529ee2a8448b81018b7519148033ed&language=pl-PL&page=1";

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, String.class);
    }
}
