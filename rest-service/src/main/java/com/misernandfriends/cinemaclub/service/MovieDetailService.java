package com.misernandfriends.cinemaclub.service;

import com.misernandfriends.cinemaclub.model.cache.CacheValue;
import com.misernandfriends.cinemaclub.pojo.Genres;
import com.misernandfriends.cinemaclub.pojo.MovieSearchCriteria;
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

    public String getMovieCreditsById(Integer id) {
        String uri = new UrlHelper(CacheValue._API_URLS.MOVIES_CREW_API_URL).setQuery(id.toString()).build();
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, String.class);
    }

    @Override
    public Genres getAllGenres() {
        String uri = new UrlHelper(CacheValue._API_URLS.MOVIES_API_GENRES_URL).build();
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, Genres.class);
    }

    @Override
    public String getMovies(MovieSearchCriteria criteria) {
        UrlHelper helper = new UrlHelper(CacheValue._API_URLS.MOVIES_API_URL_DISCOVER);

        if (criteria.getGenre() != null) {
            helper.addQuery("with_genres", criteria.getGenre().toString());
        }

        if (criteria.getVoteFrom() != null) {
            helper.addQuery("vote_average.gte", criteria.getVoteFrom().toString());
        }

        if (criteria.getVoteTo() != null) {
            helper.addQuery("vote_average.lte", criteria.getVoteTo().toString());
        }

        if (criteria.getYearFrom() != null) {
            helper.addQuery("primary_release_date.gte", criteria.getYearFrom().toString() + "-01-01");
        }

        if (criteria.getYearTo() != null) {
            helper.addQuery("primary_release_date.lte", criteria.getYearTo().toString() + "-12-31");
        }

        helper.addQuery("page", criteria.getPage().toString());

        String url = helper.build();
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }
}
