package com.misernandfriends.cinemaclub.service.movie;

import com.misernandfriends.cinemaclub.exception.ApplicationException;
import com.misernandfriends.cinemaclub.model.cache.CacheValue;
import com.misernandfriends.cinemaclub.model.movie.MovieDTO;
import com.misernandfriends.cinemaclub.pojo.movie.crew.Credits;
import com.misernandfriends.cinemaclub.pojo.movie.Genres;
import com.misernandfriends.cinemaclub.pojo.movie.Movie;
import com.misernandfriends.cinemaclub.pojo.movie.MoviesList;
import com.misernandfriends.cinemaclub.repository.movie.MovieRepository;
import com.misernandfriends.cinemaclub.pojo.movie.MovieSearchCriteria;
import com.misernandfriends.cinemaclub.serviceInterface.movie.MovieDetailService;
import com.misernandfriends.cinemaclub.utils.UrlHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Service
public class MovieDetailServiceImpl implements MovieDetailService {

    private final MovieRepository movieRepository;

    public MovieDetailServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Movie getMovieById(Integer id) {
        String uri = new UrlHelper(CacheValue._API_URLS.MOVIES_API_URL).setQuery(id.toString()).build();
        RestTemplate restTemplate = new RestTemplate();
        Movie movieSimple = restTemplate.getForObject(uri, Movie.class);
        if (movieSimple == null) {
            log.error("Movie does not exist");
            throw new ApplicationException("Movie does not exist");
        }

        Optional<MovieDTO> potentialMovie = movieRepository.getByApiUrl(String.valueOf(id));
        if (potentialMovie.isPresent()) {
            MovieDTO movie = potentialMovie.get();
            movieSimple.setNumberOfVotes(movie.getVotesNumber());
            movieSimple.setRating(movie.getMovieRating());
        }

        return movieSimple;
    }

    @Override
    public Movie getMovieByLongId(Long id) {
        String uri = new UrlHelper(CacheValue._API_URLS.MOVIES_API_URL).setQuery(id.toString()).build();
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, Movie.class);
    }

    @Override
    public MoviesList getMovieByQuery(String query) {
        String uri = new UrlHelper(CacheValue._API_URLS.MOVIES_API_URL_QUERY).setQuery(query).build();
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, MoviesList.class);
    }

    @Override
    public Credits getMovieCreditsById(Integer id) {
        String uri = new UrlHelper(CacheValue._API_URLS.MOVIES_CREW_API_URL).setQuery(id.toString()).build();
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, Credits.class);
    }

    @Override
    public Genres getGenres() {
        String uri = new UrlHelper(CacheValue._API_URLS.MOVIES_API_GENRES_URL).build();
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, Genres.class);
    }

    @Override
    public MoviesList getMovies(MovieSearchCriteria criteria) {
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
        return restTemplate.getForObject(url, MoviesList.class);
    }
}
