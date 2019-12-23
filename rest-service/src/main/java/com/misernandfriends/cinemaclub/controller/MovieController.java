package com.misernandfriends.cinemaclub.controller;

import com.misernandfriends.cinemaclub.serviceInterface.MovieFetchServiceLocal;
import com.misernandfriends.cinemaclub.serviceInterface.ReviewServiceLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class MovieController {
    @Autowired
    private MovieFetchServiceLocal movieService;

    @Autowired
    private ReviewServiceLocal reviewService;

    @GetMapping("movie/get")
    public String getMovieDetail(@RequestParam(value = "id") Integer id) {
        return movieService.getMovieById(id);
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
}