package com.misernandfriends.cinemaclub.controller;

import com.misernandfriends.cinemaclub.serviceInterface.ReviewServiceLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ReviewController {

    @Autowired
    private ReviewServiceLocal reviewService;

    @GetMapping("reviews/NYTCriticsPicks")
    public String getNYTCriticsPicksReview() {
        return reviewService.getNYTCriticsPicksReview();
    }
}
