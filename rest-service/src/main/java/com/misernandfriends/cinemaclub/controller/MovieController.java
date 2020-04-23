package com.misernandfriends.cinemaclub.controller;

import com.misernandfriends.cinemaclub.exception.ApplicationException;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.pojo.Credits;
import com.misernandfriends.cinemaclub.pojo.Genres;
import com.misernandfriends.cinemaclub.pojo.MovieSearchCriteria;
import com.misernandfriends.cinemaclub.serviceInterface.MovieFetchServiceLocal;
import com.misernandfriends.cinemaclub.serviceInterface.ReviewServiceLocal;
import com.misernandfriends.cinemaclub.pojo.Movie;
import com.misernandfriends.cinemaclub.pojo.Rate;
import com.misernandfriends.cinemaclub.serviceInterface.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class MovieController {
    @Autowired
    private MovieFetchServiceLocal movieService;

    @Autowired
    private ReviewServiceLocal reviewService;

    @Autowired
    private MovieServiceLocal movieServiceLocal;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    @GetMapping("movie/get")
    public ResponseEntity getMovieDetail(@RequestParam(value = "id") Integer id) {
        Movie movie = movieService.getMovieById(id);
        Credits credits = movieService.getMovieCreditsById(id);
        movie.setCasts(credits.getCast());
        movie.setCrews(credits.getCrew());
        return ResponseEntity.ok(movie);
    }

    @GetMapping("movie/get/reviews/nyt")
    public String getMovieNYTReviews(@RequestParam(value = "title") String title) {
        return reviewService.getNYTMovieReview(title);
    }

    @GetMapping("movie/get/reviews/guardian")
    public String getMovieGuardianReviews(@RequestParam(value = "title") String title) {
        return reviewService.getGuardianMovieReview(title);
    }

    @GetMapping("movie/get/search")
    public String getMovieByQuery(@RequestParam(value = "query") String query) {
        return movieService.getMovieByQuery(query);
    }

    @PostMapping("movies/get")
    public String getMoviesByCriteria(@RequestBody MovieSearchCriteria criteria) {
        return movieService.getMovies(criteria);
    }

    @GetMapping("movie/get/genres")
    public Genres getAllGenres() {
        return movieService.getAllGenres();
    }

    @PostMapping("movie/{movieId}/rate")
    public ResponseEntity rateMove(@PathVariable String movieId, @RequestBody Rate rate) {
        String loggedInUsername = securityService.findLoggedInUsername();
        Optional<UserDTO> user = userService.findByUsername(loggedInUsername);
        if(!user.isPresent()) {
            throw new ApplicationException("User don't exists");
        }
        movieServiceLocal.rateMovie(movieId, rate, user.get());
        return ResponseEntity.noContent().build();
    }
}
