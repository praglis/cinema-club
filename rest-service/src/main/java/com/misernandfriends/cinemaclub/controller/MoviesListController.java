package com.misernandfriends.cinemaclub.controller;

import com.misernandfriends.cinemaclub.serviceInterface.MoviesAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class MoviesListController {
    @Autowired
    private MoviesAPIService moviesService;

    @GetMapping("movies/best")
    public String getBestMovies() {
        return moviesService.getBestRatedMovies();
    }

    @GetMapping("movies/popular")
    public String getPopularMovies() {
        return moviesService.getPopularMovies();
    }
}