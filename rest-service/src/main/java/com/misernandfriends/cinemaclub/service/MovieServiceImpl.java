package com.misernandfriends.cinemaclub.service;

import com.misernandfriends.cinemaclub.model.movie.MovieDTO;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.model.user.UserRatingDTO;
import com.misernandfriends.cinemaclub.pojo.Movie;
import com.misernandfriends.cinemaclub.pojo.Rate;
import com.misernandfriends.cinemaclub.repository.movie.MovieRepository;
import com.misernandfriends.cinemaclub.repository.user.UserRatingRepository;
import com.misernandfriends.cinemaclub.serviceInterface.MovieFetchServiceLocal;
import com.misernandfriends.cinemaclub.serviceInterface.MovieServiceLocal;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
public class MovieServiceImpl implements MovieServiceLocal {

    @Autowired
    private MovieFetchServiceLocal movieDetailService;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRatingRepository userRatingRepository;

    @Override
    public MovieDTO getMovie(String movieName) {
        Optional<MovieDTO> movieOptional = movieRepository.getByTitle(movieName);
        if (movieOptional.isPresent()) {
            return movieOptional.get();
        }
        String movieByQuery = movieDetailService.getMovieByQuery(movieName);
        return createMovieByQuery(movieByQuery);
    }

    @Override
    public MovieDTO getMovie(Integer movieId) {
        if (movieId == null) {
            return null;
        }
        Optional<MovieDTO> movieOptional = movieRepository.getByApiUrl(movieId.toString());
        if (movieOptional.isPresent()) {
            return movieOptional.get();
        }
        Movie movieByQuery = movieDetailService.getMovieById(movieId);
        return createMovieByQuery(movieByQuery);
    }

    @Override
    @Transactional
    public void rateMovie(String movieId, Rate rate, UserDTO user) {
        UserRatingDTO ratingDTO = new UserRatingDTO();
        Integer oldRate = null;
        Optional<UserRatingDTO> byUser = userRatingRepository.getByUser(user.getId(), movieId);
        if (byUser.isPresent()) {
            ratingDTO = byUser.get();
            oldRate = ratingDTO.getRating();
        }
        MovieDTO movie = getMovie(Integer.parseInt(movieId));
        ratingDTO.setMovie(movie);
        ratingDTO.setUser(user);
        ratingDTO.setRating(rate.getRate());
        userRatingRepository.create(ratingDTO);
        recalculateMovieRating(movie, oldRate, rate.getRate());
    }

    private void recalculateMovieRating(MovieDTO movie, Integer oldRate, Integer newRate) {
        Double rating = movie.getMovieRating();
        Long size = movie.getVotesNumber();
        boolean isNew = false;
        if (oldRate == null) {
            isNew = true;
            oldRate = 0;
        }
        movie.setMovieRating((rating * size + (newRate - oldRate)));
        movie.setVotesNumber(size + (isNew ? 1 : 0));
        movie.setMovieRating(movie.getMovieRating() / movie.getVotesNumber());
        movieRepository.update(movie);
    }

    private MovieDTO createMovieByQuery(Movie movie) {
        if (movie != null) {
            MovieDTO movieDTO = new MovieDTO();
            movieDTO.setTitle(movie.getTitle());
            movieDTO.setApiUrl(String.valueOf(movie.getId()));
            movieRepository.create(movieDTO);
            return movieDTO;
        }
        return null;
    }

    private MovieDTO createMovieByQuery(String movieByQuery) {
        JSONObject json;
        try {
            json = new JSONObject(movieByQuery);
            if (json.has("total_results") && json.getInt("total_results") == 0) {
                return null;
            }
            JSONObject movieObj = json;
            if (json.has("results")) {
                movieObj = json
                        .getJSONArray("results")
                        .getJSONObject(0);
            }
            MovieDTO movie = new MovieDTO();
            movie.setTitle(movieObj.getString("title"));
            movie.setApiUrl(String.valueOf(movieObj.getInt("id")));
            movieRepository.create(movie);
            return movie;
        } catch (JSONException e) {
            log.error("Can't find movie", e);
            return null;
        }
    }
}
