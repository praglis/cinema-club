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
        String uri = new UrlHelper(CacheValue._API_URLS.MOVIES_API_URL).setQuery(id.toString()).build();
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, String.class);
    }

    @Override
    public String getMovieByLongId(Long id) {
        String uri = new UrlHelper(CacheValue._API_URLS.MOVIES_API_URL).setQuery(id.toString()).build();
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, String.class);
    }

    @Override
    public String getMovieByQuery(String query) {
        String uri = new UrlHelper(CacheValue._API_URLS.MOVIES_API_URL_QUERY).setQuery(query).build();
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, String.class);
    }
}
