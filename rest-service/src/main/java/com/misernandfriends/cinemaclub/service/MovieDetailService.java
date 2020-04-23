package com.misernandfriends.cinemaclub.service;

import com.misernandfriends.cinemaclub.model.cache.CacheValue;
import com.misernandfriends.cinemaclub.model.movie.MovieDTO;
import com.misernandfriends.cinemaclub.pojo.Credits;
import com.misernandfriends.cinemaclub.pojo.Genres;
import com.misernandfriends.cinemaclub.pojo.Movie;
import com.misernandfriends.cinemaclub.repository.movie.MovieRepository;
import com.misernandfriends.cinemaclub.serviceInterface.MovieFetchServiceLocal;
import com.misernandfriends.cinemaclub.utils.UrlHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class MovieDetailService implements MovieFetchServiceLocal {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public Movie getMovieById(Integer id) {
        String uri = new UrlHelper(CacheValue._API_URLS.MOVIES_API_URL).setQuery(id.toString()).build();
        RestTemplate restTemplate = new RestTemplate();
        Movie movie = restTemplate.getForObject(uri, Movie.class);
        if (movie == null) {
            return null;
        }
        Optional<MovieDTO> movieDTOOptional = movieRepository.getByApiUrl(String.valueOf(id));
        if (movieDTOOptional.isPresent()) {
            MovieDTO movieDTO = movieDTOOptional.get();
            movie.setNumberOfVotes(movieDTO.getVotesNumber());
            movie.setRating(movieDTO.getMovieRating());
        }
        return movie;
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

    public Credits getMovieCreditsById(Integer id) {
        String uri = new UrlHelper(CacheValue._API_URLS.MOVIES_CREW_API_URL).setQuery(id.toString()).build();
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, Credits.class);
    }

    @Override
    public Genres getAllGenres() {
        String uri = new UrlHelper(CacheValue._API_URLS.MOVIES_API_GENRES_URL).build();
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, Genres.class);
    }
}
