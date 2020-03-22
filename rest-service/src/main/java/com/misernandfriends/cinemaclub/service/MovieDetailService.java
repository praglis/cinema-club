package com.misernandfriends.cinemaclub.service;

import com.misernandfriends.cinemaclub.model.cache.CacheValue;
import com.misernandfriends.cinemaclub.serviceInterface.MovieFetchServiceLocal;
import com.misernandfriends.cinemaclub.utils.UrlHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieDetailService implements MovieFetchServiceLocal {
    @Override
    public String getMovieById(Integer id) {
//        final String uri = "https://api.themoviedb.org/3/movie/" + id + "?api_key=08529ee2a8448b81018b7519148033ed&language=en-US";
        String uri = new UrlHelper(CacheValue._API_URLS.MOVIES_API_URL).setQuery(id.toString()).build();
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, String.class);
    }

    @Override
    public String getMovieByQuery(String query) {
//        final String uri = "https://api.themoviedb.org/3/search/movie?query=" + query + "&api_key=08529ee2a8448b81018b7519148033ed&language=en-US";
        String uri = new UrlHelper(CacheValue._API_URLS.MOVIES_API_URL_QUERY).setQuery(query).build();
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, String.class);
    }
}
