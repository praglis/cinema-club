package com.misernandfriends.cinemaclub.service;

import com.misernandfriends.cinemaclub.model.cache.CacheValue;
import com.misernandfriends.cinemaclub.serviceInterface.MoviesFetchServiceLocal;
import com.misernandfriends.cinemaclub.utils.UrlHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MoviesFetchService implements MoviesFetchServiceLocal {
    @Override
    public String getBestRatedMovies(Integer page) {
        page = (page == null || page <= 0) ? 1 : page;

//        final String uri = "https://api.themoviedb.org/3/movie/top_rated?api_key=08529ee2a8448b81018b7519148033ed&language=en-US&page=" + page;
        String uri = new UrlHelper(CacheValue._API_URLS.MOVIES_API_URL_TOP_RATED).addQuery("page", page.toString()).build();
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, String.class);
    }

    @Override
    public String getPopularMovies(Integer page) {
        page = (page == null || page <= 0) ? 1 : page;
//        final String uri = "https://api.themoviedb.org/3/movie/popular?api_key=08529ee2a8448b81018b7519148033ed&language=en-US&page=" + page;
        String uri = new UrlHelper(CacheValue._API_URLS.MOVIES_API_URL_MOST_POPULAR).addQuery("page", page.toString()).build();
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, String.class);
    }
}
