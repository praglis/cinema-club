package com.misernandfriends.cinemaclub.controller;

import com.misernandfriends.cinemaclub.pojo.movie.MoviesList;
import com.misernandfriends.cinemaclub.pojo.rec.QuestionnaireMovieResponse;
import com.misernandfriends.cinemaclub.serviceInterface.movie.MoviesFetchService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class MoviesListController {

    private final MoviesFetchService moviesService;

    public MoviesListController(MoviesFetchService moviesService) {
        this.moviesService = moviesService;
    }

    @GetMapping("movies/best")
    public MoviesList getBestMovies(@RequestParam(value = "page", required = false) Integer page) {
        return moviesService.getBestRatedMovies(page);
    }

    @GetMapping("movies/popular")
    public MoviesList getPopularMovies(@RequestParam(value = "page", required = false) Integer page) {
        return moviesService.getPopularMovies(page);
    }

    @GetMapping("movies/questionnaire")
    public QuestionnaireMovieResponse getQuestionnaireMovies() {
        return moviesService.getQuestionnaireMovies();
    }
}
