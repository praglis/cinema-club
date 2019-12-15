package com.misernandfriends.cinemaclub.service;

import com.misernandfriends.cinemaclub.serviceInterface.MovieFetchServiceLocal;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieDetailService implements MovieFetchServiceLocal {
    @Override
    public String getMovieById(Integer id) {
        final String uri = "https://api.themoviedb.org/3/movie/" + id + "?api_key=08529ee2a8448b81018b7519148033ed&language=en-US";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, String.class);
    }

    @Override
    public String getMovieByQuery(String query) {
        final String uri = "https://api.themoviedb.org/3/search/movie?query=" + query + "&api_key=08529ee2a8448b81018b7519148033ed&language=en-US";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, String.class);
    }
}
