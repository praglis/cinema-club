package com.misernandfriends.cinemaclub.controller;

import com.misernandfriends.cinemaclub.exception.ApplicationException;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.pojo.movie.Genres;
import com.misernandfriends.cinemaclub.pojo.movie.Movie;
import com.misernandfriends.cinemaclub.pojo.movie.MovieSearchCriteria;
import com.misernandfriends.cinemaclub.pojo.movie.MoviesList;
import com.misernandfriends.cinemaclub.pojo.movie.VideoResults;
import com.misernandfriends.cinemaclub.pojo.movie.crew.Credits;
import com.misernandfriends.cinemaclub.pojo.movie.review.Rate;
import com.misernandfriends.cinemaclub.pojo.movie.review.guardian.GuardianResult;
import com.misernandfriends.cinemaclub.pojo.movie.review.nyt.NYTReview;
import com.misernandfriends.cinemaclub.serviceInterface.config.SecurityService;
import com.misernandfriends.cinemaclub.serviceInterface.movie.MovieDetailService;
import com.misernandfriends.cinemaclub.serviceInterface.movie.MovieServiceLocal;
import com.misernandfriends.cinemaclub.serviceInterface.movie.ReviewService;
import com.misernandfriends.cinemaclub.serviceInterface.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class MovieController {

    private final MovieDetailService movieService;
    private final ReviewService reviewService;
    private final MovieServiceLocal movieServiceLocal;
    private final SecurityService securityService;
    private final UserService userService;

    public MovieController(MovieDetailService movieService, ReviewService reviewService, MovieServiceLocal movieServiceLocal, SecurityService securityService, UserService userService) {
        this.movieService = movieService;
        this.reviewService = reviewService;
        this.movieServiceLocal = movieServiceLocal;
        this.securityService = securityService;
        this.userService = userService;
    }

    @GetMapping("movie/get")
    public ResponseEntity<Movie> getMovieDetail(@RequestParam(value = "id") Integer id) {
        Movie movie = movieService.getMovieById(id);
        Credits credits = movieService.getMovieCreditsById(id);
        movie.setCasts(credits.getCast());
        movie.setCrews(credits.getCrew());
        return ResponseEntity.ok(movie);
    }

    @GetMapping("movie/get/reviews/nyt")
    public NYTReview getMovieNYTReviews(@RequestParam(value = "title") String title) {
        return reviewService.getNYTMovieReview(title);
    }

    @GetMapping("movie/get/reviews/guardian")
    public GuardianResult getMovieGuardianReviews(@RequestParam(value = "title") String title) {
        return reviewService.getGuardianMovieReview(title);
    }

    @GetMapping("movie/get/search")
    public MoviesList getMovieByQuery(@RequestParam(value = "query") String query) {
        return movieService.getMovieByQuery(query);
    }

    @PostMapping("movies/get")
    public MoviesList getMoviesByCriteria(@RequestBody MovieSearchCriteria criteria) {
        return movieService.getMovies(criteria);
    }

    @GetMapping("movie/get/genres")
    public Genres getAllGenres() {
        return movieService.getGenres();
    }

    @GetMapping("movie/get/trailer")
    public VideoResults getTrailerKey(@RequestParam(value = "id") Integer id) {
        return movieService.getKeyForTrailer(id);
    }

    @PostMapping("movie/{movieId}/rate")
    public ResponseEntity<Object> rateMove(@PathVariable String movieId, @RequestBody Rate rate) {
        String loggedInUsername = securityService.findLoggedInUsername();
        Optional<UserDTO> user = userService.findByUsername(loggedInUsername);
        if (!user.isPresent()) {
            log.error("User does not exist");
            throw new ApplicationException("User does not exist");
        }

        movieServiceLocal.rateMovie(movieId, rate, user.get());
        return ResponseEntity.noContent().build();
    }
}
