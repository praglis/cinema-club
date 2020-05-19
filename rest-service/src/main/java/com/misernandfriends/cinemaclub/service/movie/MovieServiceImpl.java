package com.misernandfriends.cinemaclub.service.movie;

import com.misernandfriends.cinemaclub.model.movie.MovieDTO;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.model.user.UserMovieRatingDTO;
import com.misernandfriends.cinemaclub.pojo.movie.Movie;
import com.misernandfriends.cinemaclub.pojo.movie.MoviesList;
import com.misernandfriends.cinemaclub.pojo.movie.review.Rate;
import com.misernandfriends.cinemaclub.repository.movie.MovieRepository;
import com.misernandfriends.cinemaclub.repository.user.UserMovieRatingRepository;
import com.misernandfriends.cinemaclub.repository.user.UserRepository;
import com.misernandfriends.cinemaclub.serviceInterface.movie.MovieDetailService;
import com.misernandfriends.cinemaclub.serviceInterface.movie.MovieServiceLocal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
public class MovieServiceImpl implements MovieServiceLocal {

    private final MovieDetailService movieDetailService;
    private final MovieRepository movieRepository;
    private final UserMovieRatingRepository userMovieRatingRepository;
    private final UserRepository userRepository;

    public MovieServiceImpl(MovieDetailService movieDetailService, MovieRepository movieRepository, UserMovieRatingRepository userMovieRatingRepository, UserRepository userRepository) {
        this.movieDetailService = movieDetailService;
        this.movieRepository = movieRepository;
        this.userMovieRatingRepository = userMovieRatingRepository;
        this.userRepository = userRepository;
    }

    @Override
    public MovieDTO getMovieData(String movieName) {
        Optional<MovieDTO> potentialMovie = movieRepository.getByTitle(movieName);
        if (potentialMovie.isPresent()) {
            return potentialMovie.get();
        }

        MoviesList movies = movieDetailService.getMovieByQuery(movieName);
        return createMovieByQuery(movies, movieName);
    }

    @Override
    public MovieDTO getMovieData(Integer movieId) {
        if (movieId == null) {
            log.debug("Can't get movie with null ID");
            return null;
        }

        Optional<MovieDTO> potentialMovie = movieRepository.getByApiUrl(movieId.toString());
        if (potentialMovie.isPresent()) {
            return potentialMovie.get();
        }

        Movie movieByQuery = movieDetailService.getMovieById(movieId);
        return createMovieByQuery(movieByQuery);
    }

    @Override
    @Transactional
    public void rateMovie(String movieId, Rate rate, UserDTO user) {
        UserMovieRatingDTO ratingDTO = new UserMovieRatingDTO();
        Integer oldRate = null;
        Optional<UserMovieRatingDTO> byUser = userMovieRatingRepository.getByUser(user.getId(), movieId);
        if (byUser.isPresent()) {
            ratingDTO = byUser.get();
            oldRate = ratingDTO.getRating();
        }
        MovieDTO movie = getMovieData(Integer.parseInt(movieId));
        ratingDTO.setMovie(movie);
        ratingDTO.setUser(user);
        ratingDTO.setRating(rate.getRate());
        user.setBadgeValue(user.getBadgeValue() + 1);
        userRepository.update(user);
        userMovieRatingRepository.create(ratingDTO);
        movie.recalculateRating(oldRate, rate.getRate());
        movieRepository.update(movie);
    }

    private MovieDTO createMovieByQuery(Movie movie) {
        if (movie != null) {
            MovieDTO movieData = movie.toMovieDTO();
            movieRepository.create(movieData);
            return movieData;
        }

        log.debug("Can't create movie data from null");
        return null;
    }

    private MovieDTO createMovieByQuery(MoviesList moviesByQuery, String title) {
        if (moviesByQuery.getTotalResults() == 0 || title == null) {
            log.debug("Can't find movie: {}", title);
            return null;
        }

        Movie movie = null;
        for (Movie m : moviesByQuery.getMovies()) {
            if (m.getTitle().equals(title)) {
                movie = m;
            }
        }

        if (movie == null) {
            log.debug("Can't find movie: {}", title);
            return null;
        }

        return movie.toMovieDTO();
    }
}
