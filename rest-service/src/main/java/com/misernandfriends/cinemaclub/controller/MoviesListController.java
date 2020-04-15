package com.misernandfriends.cinemaclub.controller;

import com.misernandfriends.cinemaclub.pojo.QuestionnaireMovieResponse;
import com.misernandfriends.cinemaclub.serviceInterface.MoviesFetchServiceLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class MoviesListController {
    @Autowired
    private MoviesFetchServiceLocal moviesService;

    @GetMapping("movies/best")
    public String getBestMovies(@RequestParam(value = "page", required = false) Integer page) {
        return moviesService.getBestRatedMovies(page);
    }

    @GetMapping("movies/popular")
    public String getPopularMovies(@RequestParam(value = "page", required = false) Integer page) {
        return moviesService.getPopularMovies(page);
    }

    @GetMapping("movies/questionnaire")
    public QuestionnaireMovieResponse getQuestionnaireMovies() {
        return moviesService.getQuestionnaireMovies();
    }
}
